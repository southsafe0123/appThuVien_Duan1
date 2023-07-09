package com.teammobile.appthuvien_duan1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teammobile.appthuvien_duan1.R;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnvMain=findViewById(R.id.bnvMain);

    }

}