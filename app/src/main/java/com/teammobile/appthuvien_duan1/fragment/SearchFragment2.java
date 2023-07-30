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
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
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
        String tenTg = "";
        String tenTloai = "";
        int type = 0;
        SachDAO sachDAO = new SachDAO();
        if (bundle != null) {
            ten = bundle.getString("ten");
            tenTg = bundle.getString("tenTg");
            tenTloai = bundle.getString("tenTloai");
            type = bundle.getInt("search");
        }
        final String tenTgFn = tenTg;
        final String tenTloaiFn = tenTloai;
        switch (type) {
            case 1:
                sachDAO.getDsByTen(ten, new SachDAO.IGetSlSachByTen() {
                    @Override
                    public void onCallBack(ArrayList<Sach> list) {
                        rvSearched.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvSearched.setAdapter(new HomeAdapter(list, getContext()));
                    }
                });
                break;

            case 2:
                sachDAO.getDsByTen(ten, new SachDAO.IGetSlSachByTen() {
                    @Override
                    public void onCallBack(ArrayList<Sach> list) {
                        sachDAO.searchDsByTgia(tenTgFn, list, new ISachDAO() {
                            @Override
                            public void onCallBackInsert(Boolean check) {

                            }

                            @Override
                            public void onCallBackGetAll(ArrayList<Sach> list) {
                                rvSearched.setLayoutManager(new LinearLayoutManager(getContext()));
                                rvSearched.setAdapter(new HomeAdapter(list, getContext()));
                            }
                        });
                    }
                });
                break;

            case 3:
                sachDAO.getDsByTen(ten, new SachDAO.IGetSlSachByTen() {
                    @Override
                    public void onCallBack(ArrayList<Sach> list) {
                        sachDAO.searchDsByTgia(tenTgFn, list, new ISachDAO() {
                            @Override
                            public void onCallBackInsert(Boolean check) {

                            }

                            @Override
                            public void onCallBackGetAll(ArrayList<Sach> list) {
                                sachDAO.searchDsByTloai(tenTloaiFn, list, new ISachDAO() {
                                    @Override
                                    public void onCallBackInsert(Boolean check) {

                                    }

                                    @Override
                                    public void onCallBackGetAll(ArrayList<Sach> list) {
                                        rvSearched.setLayoutManager(new LinearLayoutManager(getContext()));
                                        rvSearched.setAdapter(new HomeAdapter(list, getContext()));
                                    }
                                });
                            }
                        });
                    }
                });
                break;

            case 4:
                sachDAO.getDsByTen(ten, new SachDAO.IGetSlSachByTen() {
                    @Override
                    public void onCallBack(ArrayList<Sach> list) {
                        sachDAO.searchDsByTloai(tenTloaiFn, list, new ISachDAO() {
                            @Override
                            public void onCallBackInsert(Boolean check) {

                            }

                            @Override
                            public void onCallBackGetAll(ArrayList<Sach> list) {
                                rvSearched.setLayoutManager(new LinearLayoutManager(getContext()));
                                rvSearched.setAdapter(new HomeAdapter(list, getContext()));
                            }
                        });
                    }
                });
                break;
        }
        ;
        return view;
    }
}
