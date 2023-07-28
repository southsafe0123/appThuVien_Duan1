package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;
import com.teammobile.appthuvien_duan1.adapter.Cart2Adapter;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminPmFragment extends Fragment {
    private Context context;
    private QuanLyActivity activity;
    private View view;
    private RecyclerView rcv;
    private Cart2Adapter adapter;
    private String maPM="N/A";
    private Button btnSubmit,btnAccept;
    
    private SachDAO sachDAO;
    private PhieuMuonDAO phieuMuonDAO;
    private ArrayList<Sach> myList;
    private PhieuMuon pm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        view=LayoutInflater.from(context).inflate( R.layout.fragment_admin_pm,container,false);
        rcv=view.findViewById(R.id.rcv);
        btnSubmit=view.findViewById(R.id.btnSubmit);
        btnAccept=view.findViewById(R.id.btnAccept);
        
        if(pm.getTrangThai()>0){
            btnSubmit.setVisibility(View.GONE);
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pm.getTrangThai()==0){
                    Map<String,Sach> map=new HashMap<>();
                    for(Sach item: myList){
                        Sach sach=new Sach(item.getLoai(),item.getTacGia(),item.getTenSach(),item.getHinhAnh(),item.getSoLuong(),item.getGiaThue(),item.getVitridesach(),item.getIsActive());
                        map.put(item.getMaSach(),sach);
                    }
                    PhieuMuon pm1=new PhieuMuon(pm.getMa(),map,pm.getUser(),pm.getNgayTao(),pm.getNgayTra(),pm.getTongTien(), 1);
                    phieuMuonDAO.update(pm1, new PhieuMuonDAO.IUpdate() {
                        @Override
                        public void onCallBack(Boolean check) {
                            if(check)
                                Toast.makeText(context, "Cập nhật phiếu mượn thành công!", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context, "Cập nhật phiếu mượn thất bại!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tt=pm.getTrangThai();
                switch (tt){
                    case 0:
                    {
                        pm.setTrangThai(2);
                        updatePM();
                    }
                    case 1:
                    {
                        pm.setTrangThai(2);
                        updatePM();
                        break;
                    }
                    case 2:{
                        thanhToan();

                        break;
                    }
                    default:
                }
            }
        });
        fetchingData();
        loadUI();
        return view;
    }
    private void khoiTao()
    {
        context=getContext();
        activity= (QuanLyActivity) context;
        sachDAO=new SachDAO();
        Bundle bundle=getArguments();
        phieuMuonDAO=new PhieuMuonDAO();
        pm= (PhieuMuon) bundle.getSerializable("pm");

    }
    public void fetchingData()
    {
        sachDAO.getAll(new ISachDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<Sach> list) {
                Map<String,Sach> map=new HashMap<>();
                for(Sach item: list){
                    Sach sach=new Sach(item.getLoai(),item.getTacGia(),item.getTenSach(),item.getHinhAnh(),item.getSoLuong(),item.getGiaThue(),item.getVitridesach(),item.getIsActive());
                    map.put(item.getMaSach(),sach);
                }
                activity.setStock(map);

            }
        });
    }
    public void loadUI()
    {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
        myList=new ArrayList<>();
       
        
        for(Map.Entry<String,Sach> entry: pm.getSach().entrySet()){
            Sach sach=entry.getValue();
            myList.add(new Sach(entry.getKey(),sach.getLoai(),sach.getTacGia(),sach.getTenSach(),sach.getHinhAnh(), sach.getSoLuong(),sach.getGiaThue(),sach.getVitridesach(),sach.getIsActive()));

        }
        adapter=new Cart2Adapter(context,myList);
        rcv.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcv.getContext(),
                linearLayoutManager.getOrientation());
        rcv.addItemDecoration(dividerItemDecoration);
        rcv.setAdapter(adapter);
    }
    public void thanhToan()
    {
        for(Sach sach: myList){
            int mx=activity.getStock().get(sach.getMaSach()).getSoLuong();
            int buy=sach.getSoLuong();
            if(mx>=buy){
                sachDAO.update(sach.getMaSach(),mx-buy, new SachDAO.IUpdate() {
                    @Override
                    public void onCallBack(Boolean check) {
                        if(check){
                            Toast.makeText(context, "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
                            pm.setTrangThai(3);
                            updatePM();
                        }
                    }
                });
            }

        }
    }
    public void updatePM()
    {
        phieuMuonDAO.update(pm, new PhieuMuonDAO.IUpdate() {
            @Override
            public void onCallBack(Boolean check) {
                if(check)
                    Toast.makeText(context, "Update hóa đơn thành công!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
