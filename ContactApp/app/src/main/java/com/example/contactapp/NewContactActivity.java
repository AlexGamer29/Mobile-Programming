package com.example.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.contactapp.databinding.ActivityMainBinding;
import com.example.contactapp.databinding.ActivityNewContactBinding;

public class NewContactActivity extends AppCompatActivity {

    private ActivityNewContactBinding binding;
    private String name;
    private String mobile;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewContactBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnSavecontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                name = binding.etName.getText().toString();
                email = binding.etEmail.getText().toString();
                mobile = binding.etPhonenumber.getText().toString();
//                Bundle sendData = new Bundle();
//                sendData.putString("name", name);
//                sendData.putString("email", email);
//                sendData.putString("mobile", mobile);
//                i.putExtras(sendData);
                i.putExtra("name", name);
                i.putExtra("email", email);
                i.putExtra("mobile", mobile);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });
    }
}