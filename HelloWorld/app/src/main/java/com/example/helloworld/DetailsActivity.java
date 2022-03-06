package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;

import com.example.helloworld.databinding.ActivityDetailsBinding;
import com.example.helloworld.databinding.ActivityMainBinding;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    private MyViewModel model;
    private String element_index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        model = new ViewModelProvider(this).get(MyViewModel.class);

        Intent intent = getIntent();
        if (intent != null) {
            String stringGet = ((Intent) intent).getStringExtra("Element");
            element_index = ((Intent) intent).getStringExtra("Element index");
            binding.etDetails.setText(stringGet);
        }

        binding.etDetails.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                binding.etDetails.setText(binding.etDetails.getText().toString());
            }
        });
    }

    public void onButtonOKClick(View view) {
        String stringToPassMainActivity = binding.etDetails.getText().toString().trim();

        Intent intent_push = new Intent();
        intent_push.putExtra("Change element", stringToPassMainActivity);
        intent_push.putExtra("Index", element_index);
        setResult(RESULT_OK, intent_push);
        finish();
    }

}