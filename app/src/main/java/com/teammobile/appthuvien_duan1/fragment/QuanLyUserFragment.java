package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.adapter.UserAdapter;
import com.teammobile.appthuvien_duan1.dao.UserDAO;
import com.teammobile.appthuvien_duan1.model.User;

import java.util.ArrayList;

public class QuanLyUserFragment extends Fragment {
    private Context context;
    private RecyclerView rcv;
    private UserDAO userDAO;
    private UserAdapter adapter;

    public UserAdapter getAdapter() {
        return adapter;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_quanly_user,container,false);
        khoiTao(view);
        fetchingData();
        return view;
    }
    public void khoiTao(View view)
    {
        rcv=view.findViewById(R.id.rcv);
        userDAO=new UserDAO(context);
    }
    public void fetchingData()
    {
        userDAO.getAll(new UserDAO.GetAllCallBack() {
            @Override
            public void onCallBack(ArrayList<User> list) {
                loadUI(list);
            }
        });
    }
    public void loadUI(ArrayList<User> data)
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        rcv.setLayoutManager(linearLayoutManager);
        adapter=new UserAdapter(context,data);
        rcv.setAdapter(adapter);
    }
//
}
