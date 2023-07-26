package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;
import com.teammobile.appthuvien_duan1.adapter.CartAdapter;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;
import java.util.Map;

public class ChitietPmFragment extends Fragment {
    private Context context;
    private QuanLyActivity activity;
    private View view;
    private RecyclerView rcv;
    private CartAdapter adapter;
    private String maPM="N/A";
    private PhieuMuonDAO phieuMuonDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        view=LayoutInflater.from(context).inflate( R.layout.fragment_chitiet_pm,container,false);
        rcv=view.findViewById(R.id.rcv);
        Bundle bundle=getArguments();

        maPM=bundle.getString("maPM");

        Toast.makeText(context, maPM+"", Toast.LENGTH_SHORT).show();
        fetchingData();
        return view;
    }
    private void khoiTao()
    {
        context=getContext();
        activity= (QuanLyActivity) context;
        phieuMuonDAO=new PhieuMuonDAO();
    }
    public void fetchingData()
    {
        phieuMuonDAO.getPM(maPM, new PhieuMuonDAO.IGetPM() {
            @Override
            public void onCallBack(Map<String, Sach> list) {
                loadUI(list);
            }
        });
    }
    public void loadUI(Map<String,Sach> map)
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        ArrayList<Sach> list=new ArrayList<>();
        for(Map.Entry<String,Sach> entry: map.entrySet()){
            Sach sach=entry.getValue();
            list.add(new Sach(entry.getKey(),sach.getLoai(),sach.getTacGia(),sach.getTenSach(),sach.getHinhAnh(), sach.getSoLuong(),sach.getGiaThue(),sach.getVitridesach(),sach.getIsActive()));

        }
        adapter=new CartAdapter(list,context);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
    }
}
