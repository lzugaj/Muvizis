package com.luv2code.android.muvizis.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.luv2code.android.muvizis.R;
import com.luv2code.android.muvizis.db.models.Movie;
import com.luv2code.android.muvizis.db.restapi.IMovie;
import com.luv2code.android.muvizis.ui.screens.MovieDetailsActivity;

import java.util.Objects;

public class SearchMovieFragment extends Fragment implements Callback<Movie> {

    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.btnDetails)
    Button btnDetails;

    private IMovie iMovie;
    private Movie movie;

    public static SearchMovieFragment newInstance() {
        return new SearchMovieFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_search, container, false);
        ButterKnife.bind(this, rootView);
        setupRestAdapter();

        return rootView;
    }

    private void setupRestAdapter() {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(IMovie.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        iMovie = restAdapter.create(IMovie.class);
    }

    @OnClick(R.id.btnGetData)
    public void btnGetData() {
        if (etTitle.getText().toString().length() > 0) {
            hideKeyboard();
            getMovie(etTitle.getText().toString());
        } else {
            Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnDetails)
    public void btnDetails() {
        hideKeyboard();
        Intent i = new Intent(getActivity(), MovieDetailsActivity.class);
        i.putExtra("MOVIE_OBJECT", movie);
        startActivity(i);
    }

    private void getMovie(String title) {
        iMovie.getMovie(title).enqueue(this);
    }


    @Override
    public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
        movie = response.body();
        if (movie != null) {
            tvResult.setText(movie.toStringSimple());
            btnDetails.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
        Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_LONG).show();
        tvResult.setText(null);
        btnDetails.setVisibility(View.GONE);
    }

    private void hideKeyboard(){
        View view = Objects.requireNonNull(getActivity()).getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}