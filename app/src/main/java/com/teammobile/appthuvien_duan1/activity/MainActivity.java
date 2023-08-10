package com.teammobile.appthuvien_duan1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.fragment.BadgeCartFragment;
import com.teammobile.appthuvien_duan1.fragment.CartFragment;
import com.teammobile.appthuvien_duan1.fragment.ClientPmFragment;
import com.teammobile.appthuvien_duan1.fragment.SearchFragment;
import com.teammobile.appthuvien_duan1.fragment.UserFragment;
import com.teammobile.appthuvien_duan1.fragment.HomeFragment;
import com.teammobile.appthuvien_duan1.interfaces.IGioHang;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private PhieuMuon curPM;
    private ClientPmFragment clientPmFragment;
    private BottomNavigationView navMain;

    public ClientPmFragment getClientPmFragment() {
        return clientPmFragment;
    }

    public void setClientPmFragment(ClientPmFragment clientPmFragment) {
        this.clientPmFragment = clientPmFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HomeFragment fragmentHome = new HomeFragment();
        navMain = findViewById(R.id.navMain);

        loadFragment(new HomeFragment());
        getSupportActionBar().hide();
        navMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    default:
//                        Toast.makeText(MainActivity.this, "default", Toast.LENGTH_SHORT).show();
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.item_home:
//                        Toast.makeText(MainActivity.this, "item_home", Toast.LENGTH_SHORT).show();
                        loadFragment(new HomeFragment());
                        break;
                    case R.id.item_giohang:
                        loadFragment(new CartFragment());

                        getSupportActionBar().hide();
                        break;
                    case R.id.item_search:
                        getSupportActionBar().hide();
                        loadFragment(new SearchFragment());
//                        Toast.makeText(MainActivity.this, "item_search", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_user:
//                        Toast.makeText(MainActivity.this, "item_user", Toast.LENGTH_SHORT).show();
                        loadFragment(new UserFragment());
                        break;
                }

                return true;
            }
        });
    }
    public void updateCartCount(int cartCount) {
        BadgeDrawable badge = navMain.getOrCreateBadge(R.id.item_giohang);
        if(cartCount<1){
            badge.setVisible(false);
        }
        else{
            badge.setVisible(true);
        }
        badge.setNumber(cartCount);


    }
    public void loadFragment(Fragment fragment)
    {
        FragmentManager fm=getSupportFragmentManager();
        if(!fm.isDestroyed()){
//            fm.popBackStack();
            fm.beginTransaction().addToBackStack(null).replace(R.id.frag_main,fragment).commit();
        }

    }

    public PhieuMuon getCurPM() {
        return curPM;
    }

    public void setCurPM(PhieuMuon curPM) {
        this.curPM = curPM;
    }




//    public void updateCartCount(int cartCount) {
//        BadgeDrawable badge = navMain.getOrCreateBadge(R.id.item_giohang);
//        badge.setNumber(cartCount);
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getCurPM()!=null){
            Log.d("ok","PM is null");
            setCurPM(null);
        }
    }
}