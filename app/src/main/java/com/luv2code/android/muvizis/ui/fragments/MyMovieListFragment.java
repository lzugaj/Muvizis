package com.luv2code.android.muvizis.ui.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.luv2code.android.muvizis.R;
import com.luv2code.android.muvizis.db.helpers.MovieDatabaseHelper;
import com.luv2code.android.muvizis.db.providers.MovieContentProvider;

import java.util.Objects;

public class MyMovieListFragment extends Fragment {

    private SimpleCursorAdapter adapter;
    private long selectedID = -1;

    @BindView(R.id.etMovieTitle)
    EditText etMovieTitle;

    @BindView(R.id.etMovieActor)
    EditText etMovieActor;

    @BindView(R.id.listView)
    ListView listView;

    public static MyMovieListFragment newInstance() {
        return new MyMovieListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_movie_my, container, false);
        ButterKnife.bind(this, rootView);
        initList();

        return rootView;
    }

    private void initList() {
        String[] columns = {MovieDatabaseHelper.COLUMN_TITLE, MovieDatabaseHelper.COLUMN_ACTOR};
        int[] viewIds = {R.id.tvMovieTitle, R.id.tvMovieActor};
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.liste_ele, null, columns, viewIds, 0);
        listView.setAdapter(adapter);
        refreshList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) { selectedID = l; fillForm();
            }
        });
    }

    private void refreshList() {
        CursorLoader cursorLoader = new CursorLoader(Objects.requireNonNull(getActivity()), MovieContentProvider.CONTENT_URI, null, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        adapter.swapCursor(cursor);
    }

    @OnClick(R.id.btnAdd)
    public void btnAdd() {
        if (inputOk()) { insertBook(etMovieTitle.getText().toString(), etMovieActor.getText().toString()); }
    }

    @OnClick(R.id.btnDelete)
    public void btnDelete() {
        if (isItemSelected()) {
            deleteBook();
        }
    }

    @OnClick(R.id.btnEdit)
    public void btnEdit() {
        if (isItemSelected() && inputOk()) {
            updateBook(etMovieTitle.getText().toString(),
                    etMovieActor.getText().toString());
        }
    }

    private boolean inputOk() {
        if (etMovieTitle.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Movie title not set", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (etMovieActor.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Movie actor not set", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isItemSelected() {
        if (selectedID == -1) {
            Toast.makeText(getActivity(), "Please select movie", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void insertBook(String movieTitle, String actor) {
        ContentValues values = new ContentValues();
        values.put(MovieDatabaseHelper.COLUMN_TITLE, movieTitle);
        values.put(MovieDatabaseHelper.COLUMN_ACTOR, actor);
        Objects.requireNonNull(getActivity()).getContentResolver().insert(MovieContentProvider.CONTENT_URI, values);
        refreshList();
        clearForm();
    }

    private void updateBook(String movieTitle, String actor) {
        ContentValues values = new ContentValues();
        values.put(MovieDatabaseHelper.COLUMN_TITLE, movieTitle);
        values.put(MovieDatabaseHelper.COLUMN_ACTOR, actor);
        Uri uri = Uri.parse(MovieContentProvider.CONTENT_URI + "/" + selectedID);
        Objects.requireNonNull(getActivity()).getContentResolver().update(uri, values, null, null);

        refreshList();
        clearForm();
    }

    private void deleteBook() {
        Uri uri = Uri.parse(MovieContentProvider.CONTENT_URI + "/" + selectedID);
        Objects.requireNonNull(getActivity()).getContentResolver().delete(uri, null, null);

        refreshList();
        clearForm();
    }

    private void fillForm() {
        Uri uri = Uri.parse(MovieContentProvider.CONTENT_URI + "/" + selectedID);
        CursorLoader cursorLoader = new CursorLoader(Objects.requireNonNull(getActivity()), uri, null, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();
        assert cursor != null;
        if (cursor.moveToFirst()) {
            String bookTitle = cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_TITLE));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(MovieDatabaseHelper.COLUMN_ACTOR));
            etMovieTitle.setText(bookTitle);
            etMovieActor.setText(author);
        }
    }

    private void clearForm() {
        etMovieActor.setText("");
        etMovieTitle.setText("");
        selectedID = -1;
    }
}