package com.midterm.nguyenhuuminhduc.Api;

import com.midterm.nguyenhuuminhduc.Model.Data;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataApiService {

    private static final String BASE_URL = "http://staff.vnuk.edu.vn:5000";

    private DataApi dataApi;
    private static DataApiService instance;

    public static DataApiService getInstance() {
        if (instance == null) {
            instance = new DataApiService();
        }
        return instance;
    }

    private DataApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        dataApi = retrofit.create(DataApi.class);
    }

    public Call<List<Data>> getDatas() {
        return dataApi.getDatas();
    }

}
