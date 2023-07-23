package com.teammobile.appthuvien_duan1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.fragment.QuanLyLoaiFragment;
import com.teammobile.appthuvien_duan1.fragment.QuanLyMenuFragment;
import com.teammobile.appthuvien_duan1.fragment.QuanLyTGFragment;
import com.teammobile.appthuvien_duan1.fragment.QuanLyUserFragment;

public class QuanLyActivity extends AppCompatActivity {
    private SearchView searchView;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);

        actionBar=getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);
        loadFragment(new QuanLyMenuFragment());

    }

    public void loadFragment(Fragment fragment)
    {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_up,0);
        ft.replace(R.id.viewFragmentQuanLy,fragment);
        ft.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
         searchView= (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setQueryHint("Nhập thông tin...");
        SearchManager searchManager = (SearchManager) getSystemService(QuanLyActivity.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FragmentManager fm=getSupportFragmentManager();

                QuanLyLoaiFragment quanLyLoaiFragment= (QuanLyLoaiFragment) fm.findFragmentByTag("fragment_loai");
                if(quanLyLoaiFragment!=null)
                    quanLyLoaiFragment.getLoaiAdapter().getFilter().filter(newText);
                QuanLyTGFragment quanLyTGFragment= (QuanLyTGFragment) fm.findFragmentByTag("fragment_tg");
                if(quanLyTGFragment!=null)
                    quanLyTGFragment.getAdapter().getFilter().filter(newText);
                QuanLyUserFragment quanLyUserFragment= (QuanLyUserFragment) fm.findFragmentByTag("fragment_user");
                if(quanLyUserFragment!=null)
                    quanLyUserFragment.getAdapter().getFilter().filter(newText);
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
            {
                finish();
                break;
            }
        }
        return true;
    }


}