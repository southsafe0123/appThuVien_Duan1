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
import com.teammobile.appthuvien_duan1.adapter.PhieuMuonAdapter;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;

import java.util.ArrayList;

public class QLPhieuMuonFragment extends Fragment {
    private Context context;
    private PhieuMuonDAO phieuMuonDAO;
    private PhieuMuonAdapter adapter;
    private RecyclerView rcv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_ql_phieumuon,container,false);
        rcv=view.findViewById(R.id.rcv);
        fetchingData();
        return view;
    }
    public void fetchingData()
    {
        phieuMuonDAO.getAll(new PhieuMuonDAO.GetAllCalBack() {
            @Override
            public void onCallBack(ArrayList<PhieuMuon> list) {
                loadUI(list);
            }
        });
    }
    public void loadUI(ArrayList<PhieuMuon> list)
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        adapter=new PhieuMuonAdapter(context,list);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);

    }
    private void khoiTao()
    {
        phieuMuonDAO=new PhieuMuonDAO();
        context=getContext();

    }
}
