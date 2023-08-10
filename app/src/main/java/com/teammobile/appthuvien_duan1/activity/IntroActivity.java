package com.teammobile.appthuvien_duan1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teammobile.appthuvien_duan1.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Button btnLogin = findViewById(R.id.btnInLogin);
        Button btnSignup = findViewById(R.id.btnInSignup);
        ImageView ivLogo = findViewById(R.id.ivLogo);

        Glide.with(this).load(R.mipmap.logo_new).into(ivLogo);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this, Login_Activity.class);
                startActivity(intent);
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroActivity.this, Register_Activity.class);
                startActivity(intent);
            }
        });
    }
}