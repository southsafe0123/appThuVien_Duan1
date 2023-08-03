package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.adapter.PhieuMuonClientAdapter;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;

import java.util.ArrayList;

public class User_DSPM_Fragment extends Fragment {
    private Context context;
    private SharedPreferences sharedPreferences;
    private String uid;
    private PhieuMuonDAO phieuMuonDAO;
    private PhieuMuonClientAdapter adapter;
    private RecyclerView rcv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_user_dspm,container,false);
        rcv=view.findViewById(R.id.rcv);
        fetchingData();
        return view;
    }
    public void fetchingData()
    {
        phieuMuonDAO.getPM(uid, new PhieuMuonDAO.IGetPM() {
            @Override
            public void onCallBack(ArrayList<PhieuMuon> list) {
                loadUI(list);
            }
        });
    }
    private void khoiTao() {
        context = getContext();
        sharedPreferences=getActivity().getSharedPreferences("Info",Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("uid", "");
        phieuMuonDAO = new PhieuMuonDAO();
    }
    public void loadUI(ArrayList<PhieuMuon> list)
    {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        adapter= new PhieuMuonClientAdapter(context,list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcv.getContext(),
                layoutManager.getOrientation());
        rcv.addItemDecoration(dividerItemDecoration);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);
    }
}
