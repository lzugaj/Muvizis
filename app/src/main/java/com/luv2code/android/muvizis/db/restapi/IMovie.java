package com.luv2code.android.muvizis.db.restapi;

import com.luv2code.android.muvizis.db.models.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IMovie {

    String API_KEY = "ca92682c";
    String URL = "http://www.omdbapi.com";

    @GET("/?apikey=" + API_KEY)
    Call<Movie> getMovie(@Query("t") String movieName);
}
