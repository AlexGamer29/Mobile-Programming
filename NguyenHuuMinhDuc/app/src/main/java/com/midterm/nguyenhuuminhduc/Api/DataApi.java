package com.midterm.nguyenhuuminhduc.Api;

import com.midterm.nguyenhuuminhduc.Model.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataApi {

    @GET("static/data/data.json")
    Call<List<Data>> getDatas();
}
