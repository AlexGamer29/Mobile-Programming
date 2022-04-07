package com.midterm.minhduc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataApi {

    @GET("static/data/data.json")
    Call<List<Data>> getData();
}
