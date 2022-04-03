package com.example.dogapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.dogapp.R;
import com.example.dogapp.databinding.ActivityMainBinding;
import com.example.dogapp.model.DogBreed;
import com.example.dogapp.viewmodel.DogAdapter;
import com.example.dogapp.viewmodel.DogsApi;
import com.example.dogapp.viewmodel.DogsApiService;

import java.util.ArrayList;
import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    public List<DogBreed> listDogs;
    public DogAdapter dogAdapter;

    private SearchView searchView;
    private DogsApiService dogsApiService;
    private DogsApi dogsApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.rvImages.setLayoutManager(new GridLayoutManager(this, 2));
        listDogs = new ArrayList<>();

//        dogAdapter = new DogAdapter(MainActivity.this, listDogs);
//        binding.rvImages.setAdapter(dogAdapter);

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://raw.githubusercontent.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .build();
//
//        dogsApi = retrofit.create(DogsApi.class);

        dogsApiService = DogsApiService.getInstance();

        fetchApi();


//        apiService.getDogs()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableSingleObserver<List<DogBreed>>() {
//                    @Override
//                    public void onSuccess(@NonNull List<DogBreed> dogBreeds) {
//                        for(DogBreed dog : dogBreeds) {
//                            Log.d("DEBUG", "" + dog.getName());
//                            listDogs.addAll(dogBreeds);
//                            dogAdapter = new DogAdapter(listDogs);
//                            binding.rvImages.setAdapter(dogAdapter);
//                        }
//                    }
//
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d("ERROR", "Error" + e.getMessage());
//                    }
//                });
    }

    public void fetchApi() {
//        Call<List<DogBreed>> call = dogsApi.getDogs();
        Call<List<DogBreed>> call = dogsApiService.getDogs();
        call.enqueue(new Callback<List<DogBreed>>() {
            @Override
            public void onResponse(Call<List<DogBreed>> call, Response<List<DogBreed>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<DogBreed> listDogs = response.body();
                dogAdapter = new DogAdapter(MainActivity.this, listDogs);
                binding.rvImages.setAdapter(dogAdapter);
            }

            @Override
            public void onFailure(Call<List<DogBreed>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // in this on create options menu we are calling
        // a menu inflater and inflating our menu file.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        // on below line we are getting our menu item as search view item
//        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        // on below line we are creating a variable for our search view.
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        // on below line we are setting on query text listener for our search view.


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on query submit we are clearing the focus for our search view.
                dogAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // on changing the text in our search view we are calling
                // a filter method to filter our array list.
                dogAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}