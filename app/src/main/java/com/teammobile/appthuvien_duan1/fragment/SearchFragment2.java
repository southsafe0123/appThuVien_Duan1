package com.teammobile.appthuvien_duan1.fragment;

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
import com.teammobile.appthuvien_duan1.adapter.HomeAdapter;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class SearchFragment2 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searched, container, false);
        RecyclerView rvSearched = view.findViewById(R.id.rvSearched);
        Bundle bundle = getArguments();
        String ten = "";
        if(bundle != null){
            ten = bundle.getString("ten");
        }

        SachDAO sachDAO = new SachDAO();
        sachDAO.getDsByTen(ten, new SachDAO.IGetSlSachByTen() {
            @Override
            public void onCallBack(ArrayList<Sach> list) {
                rvSearched.setLayoutManager(new LinearLayoutManager(getContext()));
                rvSearched.setAdapter(new HomeAdapter(list, getContext()));
            }
        });
        return view;
    }
}
