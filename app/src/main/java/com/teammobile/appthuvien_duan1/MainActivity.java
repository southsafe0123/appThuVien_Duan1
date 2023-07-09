package com.teammobile.appthuvien_duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teammobile.appthuvien_duan1.dao.Dao_User;
import com.teammobile.appthuvien_duan1.interfaces.Callback_Interface;
import com.teammobile.appthuvien_duan1.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}