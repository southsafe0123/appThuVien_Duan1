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
import com.teammobile.appthuvien_duan1.fragment.UserFragment;
import com.teammobile.appthuvien_duan1.fragment.FragmentHome;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentHome fragmentHome = new FragmentHome();
        BottomNavigationView navMain = findViewById(R.id.navMain);

        navMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    default:
                        Toast.makeText(MainActivity.this, "default", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_home:
                        Toast.makeText(MainActivity.this, "item_home", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_giohang:
                        Toast.makeText(MainActivity.this, "item_giohang", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_search:
                        Toast.makeText(MainActivity.this, "item_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_user:
                        Toast.makeText(MainActivity.this, "item_user", Toast.LENGTH_SHORT).show();
                        loadFragment(new UserFragment());
                        break;
                }

                return true;
            }
        });
    }
    public void loadFragment(Fragment fragment)
    {
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frag_main,fragment).commit();
    }
}