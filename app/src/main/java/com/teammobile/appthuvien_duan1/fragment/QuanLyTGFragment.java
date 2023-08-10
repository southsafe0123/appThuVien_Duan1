package com.teammobile.appthuvien_duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.adapter.LoaiAdapter;
import com.teammobile.appthuvien_duan1.adapter.TacGiaAdapter;
import com.teammobile.appthuvien_duan1.dao.LoaiDAO;
import com.teammobile.appthuvien_duan1.dao.TacGiaDAO;
import com.teammobile.appthuvien_duan1.interfaces.ILoaiDAO;
import com.teammobile.appthuvien_duan1.interfaces.ITacGiaDAO;
import com.teammobile.appthuvien_duan1.model.Loai;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.util.ArrayList;

public class QuanLyTGFragment extends Fragment {
    private Context context;
    private TacGiaDAO tacGiaDAO;
    private TacGiaAdapter adapter;
    private RecyclerView rcv;
    private ImageView ivEmpty;
    private FloatingActionButton floatingActionButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_quanly_tacgia,container,false);
        tacGiaDAO=new TacGiaDAO();
        context=getContext();
        rcv=view.findViewById(R.id.rcvLoai);
        floatingActionButton=view.findViewById(R.id.btnFloatingAdd);
        ivEmpty=view.findViewById(R.id.ivEmpty);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogThemTG();
            }
        });
        fetchingData();
        return view;
    }
    public void showDialogThemTG()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_them_tacgia,null);
        builder.setView(view);
        EditText edtName=view.findViewById(R.id.edtName);
        Button btnAdd=view.findViewById(R.id.btnAdd);
        Dialog dialog=builder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edtName.getText().toString();
                tacGiaDAO=new TacGiaDAO();
                tacGiaDAO.insert(new TacGia(name,1), new ITacGiaDAO() {

                    @Override
                    public void onCallBackInsert(Boolean check) {
                        Toast.makeText(context, "Insert tác giả thành công", Toast.LENGTH_SHORT).show();
                        fetchingData();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCallBackGetAll(ArrayList<TacGia> list) {
                        dialog.dismiss();
                    }
                });
            }
        });
        dialog.show();
    }
    public void fetchingData()
    {
        tacGiaDAO.getAll(new ITacGiaDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<TacGia> list) {
                loadData(list);
            }
        });
    }
    public void loadData(ArrayList<TacGia> list)
    {
        if(list.size()>0){
            ivEmpty.setVisibility(View.GONE);
        }
        else
            ivEmpty.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        adapter=new TacGiaAdapter(context,list);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

    }

    public TacGiaAdapter getAdapter() {
        return adapter;
    }
}
