package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TextView tvCount;
    private FloatingActionButton btnCountUp;
    private FloatingActionButton btnCountDown;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = findViewById(R.id.tv_count);
        btnCountUp = findViewById(R.id.btn_count_up);
        btnCountDown = findViewById(R.id.btn_count_down);
        count = Integer.parseInt(tvCount.getText().toString());

        btnCountUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCount.setText("" + ++count);
            }
        });

        btnCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCount.setText("" + --count);
            }
        });
    }
}