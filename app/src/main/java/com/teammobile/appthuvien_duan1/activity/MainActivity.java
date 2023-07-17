package com.teammobile.appthuvien_duan1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.teammobile.appthuvien_duan1.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navMain = findViewById(R.id.navMain);

        navMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    default:
                        Toast.makeText(MainActivity.this, "default", Toast.LENGTH_SHORT).show();
                        // fragment = new ...();
                        break;
                    case R.id.item_home:
                        Toast.makeText(MainActivity.this, "item_home", Toast.LENGTH_SHORT).show();
                        // fragment = new ...();
                        break;
                    case R.id.item_giohang:
                        Toast.makeText(MainActivity.this, "item_giohang", Toast.LENGTH_SHORT).show();
                        // fragment = new ...();
                        break;
                    case R.id.item_search:
                        Toast.makeText(MainActivity.this, "item_search", Toast.LENGTH_SHORT).show();
                        // fragment = new ...();
                        break;
                    case R.id.item_user:
                        Toast.makeText(MainActivity.this, "item_user", Toast.LENGTH_SHORT).show();
                        // fragment = new ...();
                        break;
                }
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.frag_main,fragment).commit();

                return true;
            }
        });
    }
}