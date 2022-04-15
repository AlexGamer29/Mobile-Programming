package com.midterm.nguyenhuuminhduc.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.midterm.nguyenhuuminhduc.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent getIntent = getIntent();
        String title = getIntent.getStringExtra("title");
        String desc = getIntent.getStringExtra("desc");
        String timeStamp = getIntent.getStringExtra("timeStamp");
        String lat = getIntent.getStringExtra("lat");
        String lng = getIntent.getStringExtra("lng");
        String addr = getIntent.getStringExtra("addr");
        String e = getIntent.getStringExtra("e");
        String zip = getIntent.getStringExtra("zip");
        if (getIntent != null) {
            binding.tvDetailsTitle.setText(title);
            binding.tvDetailsDesc.setText(desc);
            binding.tvDetailsTimestamp.setText(timeStamp);
            binding.tvDetailsLat.setText(lat);
            binding.tvDetailsLng.setText(lng);
            binding.tvDetailsAddr.setText(addr);
            binding.tvDetailsE.setText(e);
            binding.tvDetailsZip.setText(zip);
        }

    }
}