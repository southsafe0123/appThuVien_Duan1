package com.teammobile.appthuvien_duan1.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.adapter.QLSachAdapter;
import com.teammobile.appthuvien_duan1.dao.LoaiDAO;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.dao.TacGiaDAO;
import com.teammobile.appthuvien_duan1.interfaces.IFirebaseStorage;
import com.teammobile.appthuvien_duan1.interfaces.ILoaiDAO;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.interfaces.ITacGiaDAO;
import com.teammobile.appthuvien_duan1.model.Loai;
import com.teammobile.appthuvien_duan1.model.Sach;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QuanLySachFragment extends Fragment {
    private Context context;
    private FloatingActionButton floatingActionButton;
    private RecyclerView rcv;
    private SachDAO sachDAO;
    private QLSachAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        khoiTao();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_quanly_sach,container,false);
        floatingActionButton=view.findViewById(R.id.btnFloatingAdd);
        rcv=view.findViewById(R.id.rcvLoai);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment();
            }
        });
        fetchingData();
        return view;
    }
    public void khoiTao()
    {
        sachDAO=new SachDAO();
        context=getContext();
    }
    public void loadFragment()
    {
        FragmentManager fm=getActivity().getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.viewFragmentQuanLy,new ThemSachFragment(),"fragment_them_sach").addToBackStack("fragment_them_sach").commit();


    }
    public void fetchingData()
    {
        sachDAO.getAll(new ISachDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<Sach> list) {
                loadUI(list);
            }
        });
    }
    public void loadUI(ArrayList<Sach> list)
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        adapter=new QLSachAdapter(context,list);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        rcv.setAdapter(adapter);
    }

    public QLSachAdapter getAdapter() {
        return adapter;
    }
}
