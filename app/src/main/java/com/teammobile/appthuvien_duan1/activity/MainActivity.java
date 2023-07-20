package com.teammobile.appthuvien_duan1.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.fragment.FragmentHome;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentHome fragmentHome = new FragmentHome();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().add(R.id.viewFragment, fragmentHome).commit();
    }
}