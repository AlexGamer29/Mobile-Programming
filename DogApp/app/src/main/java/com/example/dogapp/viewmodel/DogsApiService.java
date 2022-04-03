package com.example.dogapp.viewmodel;

import com.example.dogapp.model.DogBreed;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DogsApiService {
    private static final String BASE_URL = "https://raw.githubusercontent.com";

    private DogsApi dogsApi;
    private static DogsApiService instance;

    public static DogsApiService getInstance() {
        if (instance == null) {
            instance = new DogsApiService();
        }
        return instance;
    }

    private DogsApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        dogsApi = retrofit.create(DogsApi.class);
    }

//    private DogsApi instance;
//
//    public DogsApiService() {
//        instance = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                    .build()
//                    .create(DogsApi.class);
//    }


    public Call<List<DogBreed>> getDogs() {
        return dogsApi.getDogs();
    }


}
