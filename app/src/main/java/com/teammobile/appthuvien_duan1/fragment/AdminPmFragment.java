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
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;
import com.teammobile.appthuvien_duan1.adapter.AdminCartAdapter;
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
    private AdminCartAdapter adapter;
    private AppCompatButton btnCapNhat, btnThanhToan,btnTuChoi,btnTraHang,btnXacNhan;
    private SachDAO sachDAO;
    private PhieuMuonDAO phieuMuonDAO;
    private ArrayList<Sach> myList;
    private PhieuMuon pm;
    private View viewFM;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        viewFM=LayoutInflater.from(context).inflate(R.layout.fragment_admin_pm,container,false);
        btnCapNhat=viewFM.findViewById(R.id.btnCapNhat);
        btnXacNhan=viewFM.findViewById(R.id.btnXacNhan);
        btnTuChoi=viewFM.findViewById(R.id.btnHuyDon);
        btnThanhToan=viewFM.findViewById(R.id.btnThanhToan);
        btnTraHang=viewFM.findViewById(R.id.btnTraHang);
        rcv=viewFM.findViewById(R.id.rcv);
        phieuMuonDAO.getCurPM(activity.getCurPM().getMa(), new PhieuMuonDAO.IGetCurPM() {
            @Override
            public void onCallBack(PhieuMuon phieuMuon) {
                if(!phieuMuon.getMa().equals(activity.getCurPM().getMa()))
                    return;
                int tt=phieuMuon.getTrangThai();
                switch (tt){
                    case 0:
                    {
                        Toast.makeText(context, "HELLO", Toast.LENGTH_SHORT).show();
                        btnThanhToan.setVisibility(View.GONE);
                        btnTraHang.setVisibility(View.GONE);
                        btnXacNhan.setVisibility(View.VISIBLE);
                        btnCapNhat.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1:
                    {
                        btnCapNhat.setVisibility(View.GONE);
                        btnThanhToan.setVisibility(View.GONE);
                        btnTraHang.setVisibility(View.GONE);
                        btnXacNhan.setVisibility(View.GONE);
                        break;
                    }
                    case 2:
                    {
                        btnCapNhat.setVisibility(View.GONE);
                        btnThanhToan.setVisibility(View.VISIBLE);
                        btnTraHang.setVisibility(View.GONE);
                        btnXacNhan.setVisibility(View.GONE);
                        break;
                    }
                    case 3:
                    {
                        btnCapNhat.setVisibility(View.GONE);
                        btnThanhToan.setVisibility(View.GONE);
                        btnTraHang.setVisibility(View.VISIBLE);
                        btnXacNhan.setVisibility(View.GONE);
                        btnTuChoi.setVisibility(View.GONE);
                        break;
                    }
                    default:{
                        btnCapNhat.setVisibility(View.GONE);
                        btnThanhToan.setVisibility(View.GONE);
                        btnTraHang.setVisibility(View.GONE);
                        btnXacNhan.setVisibility(View.GONE);
                        btnTuChoi.setVisibility(View.GONE);
                    }
                }
                activity.setTrangThai(phieuMuon.getTrangThai());
                fetchingData();
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setTrangThai(1);
                Map<String,Sach> map=new HashMap<>();
                for(Sach sach: myList){
                    map.put(sach.getMaSach(),new Sach(sach.getLoai(),sach.getTacGia(),sach.getTenSach(),sach.getHinhAnh(),sach.getSoLuong(),sach.getGiaThue(),sach.getVitridesach(),sach.getIsActive()));

                }
                pm.setSach(map);
                updatePM();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setTrangThai(2);
                updatePM();
            }
        });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setTrangThai(3);
                updatePM();
                updateStock(myList,-1);
            }
        });
        btnTraHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setTrangThai(4);
                updatePM();
                updateStock(myList,1);
            }
        });
        btnTuChoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setTrangThai(-1);
                updatePM();
            }
        });
        return viewFM;
    }

    private void khoiTao() {
        context = getContext();
        activity = (QuanLyActivity) context;
        sachDAO = new SachDAO();
        Bundle bundle = getArguments();
        phieuMuonDAO = new PhieuMuonDAO();
        pm = (PhieuMuon) bundle.getSerializable("pm");
        activity.setTrangThai(pm.getTrangThai());

    }

    public void fetchingData() {
        sachDAO.getAll(new ISachDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<Sach> list) {
                Map<String, Sach> map = new HashMap<>();
                for (Sach item : list) {
                    Sach sach = new Sach(item.getLoai(), item.getTacGia(), item.getTenSach(), item.getHinhAnh(), item.getSoLuong(), item.getGiaThue(), item.getVitridesach(), item.getIsActive());
                    map.put(item.getMaSach(), sach);
                }
                activity.setStock(map);
                loadUI();

            }
        });
    }

    public void loadUI() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        myList = new ArrayList<>();
        for (Map.Entry<String, Sach> entry : pm.getSach().entrySet()) {
            Sach sach = entry.getValue();
            myList.add(new Sach(entry.getKey(), sach.getLoai(), sach.getTacGia(), sach.getTenSach(), sach.getHinhAnh(), sach.getSoLuong(), sach.getGiaThue(), sach.getVitridesach(), sach.getIsActive()));

        }
        Toast.makeText(context, ""+myList.get(0).getTenSach(), Toast.LENGTH_SHORT).show();
        adapter = new AdminCartAdapter(context, myList);
        rcv.setLayoutManager(linearLayoutManager);
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcv.getContext(),
//                linearLayoutManager.getOrientation());
//        rcv.addItemDecoration(dividerItemDecoration);
        rcv.setAdapter(adapter);
    }

    public void thanhToan() {
        updateStock(myList,-1);
    }

    public void updatePM() {

        phieuMuonDAO.update(pm, new PhieuMuonDAO.IUpdate() {
            @Override
            public void onCallBack(Boolean check) {
                if (check) {
                    Toast.makeText(context, "Cập nhật thành công rồi nè", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateStock(ArrayList<Sach> list,int choice)
    {
        for(Sach sach: list){
            int mx=activity.getStock().get(sach.getMaSach()).getSoLuong();
            sachDAO.update(sach.getMaSach(), mx + sach.getSoLuong()*choice, new SachDAO.IUpdate() {
                @Override
                public void onCallBack(Boolean check) {
                        if(check&&choice==-1){
                            pm.setTrangThai(3);
                            updatePM();
                        }
                }
            });
        }
    }
}
