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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.fragment.AdminPmFragment;
import com.teammobile.appthuvien_duan1.fragment.QLPhieuMuonFragment;
import com.teammobile.appthuvien_duan1.fragment.QuanLyLoaiFragment;
import com.teammobile.appthuvien_duan1.fragment.QuanLyMenuFragment;
import com.teammobile.appthuvien_duan1.fragment.QuanLySachFragment;
import com.teammobile.appthuvien_duan1.fragment.QuanLyTGFragment;
import com.teammobile.appthuvien_duan1.fragment.QuanLyUserFragment;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuanLyActivity extends AppCompatActivity {
    private SearchView searchView;
    private ActionBar actionBar;
    private SachDAO sachDAO;
    private int trangThai=-1;
    private int tongGia=0;
    private Map<String,Sach> stock;
    private PhieuMuon curPM;
    private AdminPmFragment adminPmFragment;

    public AdminPmFragment getAdminPmFragment() {
        return adminPmFragment;
    }

    public void setAdminPmFragment(AdminPmFragment adminPmFragment) {
        this.adminPmFragment = adminPmFragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        khoiTao();
        actionBar=getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.baseline_home_24);
        loadFragment(new QuanLyMenuFragment());

    }

    public PhieuMuon getCurPM() {
        return curPM;
    }

    public void setCurPM(PhieuMuon curPM) {
        this.curPM = curPM;
    }

    public void khoiTao()
    {
        sachDAO=new SachDAO();
    }
    public void loadFragment(Fragment fragment)
    {
        FragmentManager fm=getSupportFragmentManager();
        if(!fm.isDestroyed()){
//            FragmentTransaction ft=fm.beginTransaction();
//            ft.setCustomAnimations(R.anim.slide_up,0);
//            ft.addToBackStack(null).replace(R.id.viewFragmentQuanLy,fragment,"fragment_menu");
//            ft.commit();
            fm.beginTransaction().addToBackStack(null).replace(R.id.viewFragmentQuanLy,fragment).commit();
        }

    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public Map<String, Sach> getStock() {
        return stock;
    }

    public void setStock(Map<String, Sach> stock) {
        this.stock = stock;
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
                QuanLySachFragment quanLySachFragment= (QuanLySachFragment) fm.findFragmentByTag("fragment_sach");
                if(quanLySachFragment!=null)
                    quanLySachFragment.getAdapter().getFilter().filter(newText);
                QLPhieuMuonFragment qlPhieuMuonFragment= (QLPhieuMuonFragment) fm.findFragmentByTag("fragment_pm");
                if(qlPhieuMuonFragment!=null)
                    qlPhieuMuonFragment.getAdapter().getFilter().filter(newText);
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
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getCurPM()!=null){
            Log.d("Ok: QL","PM is null");
            setCurPM(null);
        }
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentByTag("fragment_menu");
        if(fragment!=null){
            startActivity(new Intent(QuanLyActivity.this,MainActivity.class));
            finish();

        }
    }
//
}