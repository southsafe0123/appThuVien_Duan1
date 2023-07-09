package com.teammobile.appthuvien_duan1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.teammobile.appthuvien_duan1.R;

public class MainActivity extends AppCompatActivity {
    NavigationBarView bnvMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnvMain=findViewById(R.id.bnvMain);
        bnvMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                switch (item.getItemId()){
                    case R.id.mnHome:
                    {
                        Toast.makeText(MainActivity.this,"Bạn chọn Home",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.mnSearch:
                    {
                        Toast.makeText(MainActivity.this,"Bạn chọn Search",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,SearchActivity.class));
                        break;
                    }
                    case R.id.mnCart:
                    {
                        Toast.makeText(MainActivity.this,"Bạn chọn Cart",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.mnUser:
                    {
                        Toast.makeText(MainActivity.this,"Bạn chọn User",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        });
    }

}