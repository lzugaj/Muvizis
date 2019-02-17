package com.luv2code.android.muvizis.ui.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.luv2code.android.muvizis.R;
import com.luv2code.android.muvizis.db.models.Movie;
import com.luv2code.android.muvizis.db.models.SimpleDataObject;
import com.luv2code.android.muvizis.ui.adapters.DataAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.ivImage)
    ImageView ivImage;

    @BindView(R.id.rvData)
    RecyclerView rvData;

    private List<SimpleDataObject> dataObjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        Intent i = getIntent();
        if (i != null) {
            Movie movie = i.getParcelableExtra("MOVIE_OBJECT");
            Picasso.get().load(movie.getPoster()).into(ivImage);
            ivImage.setAlpha(0.2f);
            setDataObjects(movie);
        }

        rvData = findViewById(R.id.rvData);

        DataAdapter mAdapter = new DataAdapter(dataObjects);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rvData.setLayoutManager(mLayoutManager);
        rvData.setItemAnimator(new DefaultItemAnimator());
        rvData.setAdapter(mAdapter);
    }

    private void setDataObjects(final Movie movie){
        String listStuff[] = movie.toString().replace("\'", "").split("\\r?\\n");
        dataObjects.clear();
        String[] data;
        for (String s : listStuff) {
            data = s.split("=");
            String titleCapitalized = data[0].substring(0, 1).toUpperCase() + data[0].substring(1);
            dataObjects.add(new SimpleDataObject(titleCapitalized, data[1]));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.imgShowHide:
                showHideImage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showHideImage(){
        if(rvData.getVisibility() == View.VISIBLE){
            rvData.setVisibility(View.GONE);
            showWithAnim();
        } else {
            rvData.setVisibility(View.VISIBLE);
            ivImage.setAlpha(0.2f);

        }
    }

    private void showWithAnim(){
        ivImage.setAlpha(1f);
        AlphaAnimation animation1 = new AlphaAnimation(0.1f, 1.0f);
        animation1.setDuration(650);
        animation1.setFillAfter(true);
        ivImage.startAnimation(animation1);
    }
}
