package com.example.helloworld;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer> number;
    private MutableLiveData<ArrayList<String>> listString;
    private MutableLiveData<String> number_details;


    public LiveData<Integer> getNumber() {
        if (number == null) {
            number = new MutableLiveData<Integer>();
            number.setValue(0);
        }
        return number;
    }

    public void increaseNumber() {
        number.setValue(number.getValue() + 1);
    }

    public void decreaseNumber() {
        number.setValue(number.getValue() - 1);
    }

//    public LiveData<ArrayList<String>> getStringList() {
//        return listString;
//    }
//    public void fetchStringList(){
//        listString.setValue(listString.getValue());
//    }
}
