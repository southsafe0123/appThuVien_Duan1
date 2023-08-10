package com.teammobile.appthuvien_duan1.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdminPmFragment extends Fragment {
    private Context context;
    private QuanLyActivity activity;
    private View view;
    private RecyclerView rcv;
    private AdminCartAdapter adapter;
    private AppCompatButton btnCapNhat, btnThanhToan,btnTuChoi,btnTraHang,btnXacNhan;
    private TextView tvTrangThai,tvTongTien;
    private SachDAO sachDAO;
    private PhieuMuonDAO phieuMuonDAO;
    private ArrayList<Sach> myList;
    private PhieuMuon pm;
    private View viewFM;
    private String trangThai;
    private int tongTien;

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public TextView getTvTongTien() {
        return tvTongTien;
    }

    public void setTvTongTien(TextView tvTongTien) {
        this.tvTongTien = tvTongTien;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        NumberFormat format = NumberFormat.getInstance(Locale.US);

        viewFM=LayoutInflater.from(context).inflate(R.layout.fragment_admin_pm,container,false);
        btnCapNhat=viewFM.findViewById(R.id.btnCapNhat);
        btnXacNhan=viewFM.findViewById(R.id.btnXacNhan);
        btnTuChoi=viewFM.findViewById(R.id.btnHuyDon);
        btnThanhToan=viewFM.findViewById(R.id.btnThanhToan);
        btnTraHang=viewFM.findViewById(R.id.btnTraHang);
        tvTrangThai=viewFM.findViewById(R.id.tvTrangThai);
        rcv=viewFM.findViewById(R.id.rcv);
        tvTongTien=viewFM.findViewById(R.id.tvTongTien);
        phieuMuonDAO.getAll(new PhieuMuonDAO.GetAllCalBack() {
            @Override
            public void onCallBack(ArrayList<PhieuMuon> list) {
                int ok=0;
                PhieuMuon curPM=activity.getCurPM();
                for(PhieuMuon phieuMuon: list){

                    if(curPM!=null&&curPM.getMa().equals(phieuMuon.getMa())){
                        ok=1;
                        pm=phieuMuon;
                        break;
                    }
                }

                if(ok==1){
                    int tt=pm.getTrangThai();
                    trangThai="";
                    switch (tt){
                        case 0:
                        {
                            trangThai="Chờ xác nhận";
                            btnThanhToan.setVisibility(View.GONE);
                            btnTraHang.setVisibility(View.GONE);
                            btnXacNhan.setVisibility(View.VISIBLE);
                            btnCapNhat.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 1:
                        {
                            trangThai="Chờ người dùng xác nhận";
                            btnCapNhat.setVisibility(View.GONE);
                            btnThanhToan.setVisibility(View.GONE);
                            btnTraHang.setVisibility(View.GONE);
                            btnXacNhan.setVisibility(View.GONE);
                            break;
                        }
                        case 2:
                        {
                            trangThai="Đã xác nhận";
                            btnCapNhat.setVisibility(View.GONE);
                            btnThanhToan.setVisibility(View.VISIBLE);
                            btnTraHang.setVisibility(View.GONE);
                            btnXacNhan.setVisibility(View.GONE);
                            break;
                        }
                        case 3:
                        {
                            trangThai="Thanh toán thành công";
                            btnCapNhat.setVisibility(View.GONE);
                            btnThanhToan.setVisibility(View.GONE);
                            btnTraHang.setVisibility(View.VISIBLE);
                            btnXacNhan.setVisibility(View.GONE);
                            btnTuChoi.setVisibility(View.GONE);
                            break;
                        }
                        case 4:{
                            trangThai="Đã trả sách về thư viện";
                            btnCapNhat.setVisibility(View.GONE);
                            btnThanhToan.setVisibility(View.GONE);
                            btnTraHang.setVisibility(View.GONE);
                            btnXacNhan.setVisibility(View.GONE);
                            btnTuChoi.setVisibility(View.GONE);
                            break;
                        }
                        default:{
                            trangThai="Hóa đơn bị hủy";
                            btnCapNhat.setVisibility(View.GONE);
                            btnThanhToan.setVisibility(View.GONE);
                            btnTraHang.setVisibility(View.GONE);
                            btnXacNhan.setVisibility(View.GONE);
                            btnTuChoi.setVisibility(View.GONE);
                        }
                    }
                    activity.setTrangThai(pm.getTrangThai());
                    fetchingData();
                }
            }
        });
        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setTrangThai(1);
                Map<String,Sach> map=new HashMap<>();
                int tongTien=0;
                for(Sach sach: myList){
                    map.put(sach.getMaSach(),new Sach(sach.getLoai(),sach.getTacGia(),sach.getTenSach(),sach.getHinhAnh(),sach.getSoLuong(),sach.getGiaThue(),sach.getVitridesach(),sach.getIsActive()));
                    tongTien+=sach.getGiaThue()*sach.getSoLuong();
                }
                pm.setSach(map);
                pm.setTongTien(tongTien);
                updatePM();
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ok=1;
                ArrayList<Sach> kq=new ArrayList<>();
                for(Map.Entry<String, Sach> item: pm.getSach().entrySet()){
                    String key=item.getKey();
                    int maxSL=activity.getStock().get(key).getSoLuong();
                    if(item.getValue().getSoLuong()>maxSL){
                        ok=0;
                        break;
                    }
                }
                if(ok>0){
                    pm.setTrangThai(2);
                    updatePM();
                    myList=toList(pm.getSach());
                    updateStock(myList,-1);

                }
                else{
                    showDialog();
                }
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setTrangThai(3);
                updatePM();
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
                if(pm.getTrangThai()>1){
                    updateStock(myList,1);
                }
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
        phieuMuonDAO = new PhieuMuonDAO();
        myList = new ArrayList<>();
    }
    public void showDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Số lượng hàng đã được thay đổi, bạn có muốn cập nhật lại ?");
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pm.setTrangThai(1);
                tongTien=0;
                for(Map.Entry<String,Sach> entry: pm.getSach().entrySet()){
                    String key=entry.getKey();
                    int maxSL=activity.getStock().get(key).getSoLuong();
                    if(maxSL<entry.getValue().getSoLuong()){
                        pm.getSach().get(key).setSoLuong(maxSL);
                    }
                    Sach sach=pm.getSach().get(key);
                    tongTien+=sach.getGiaThue()*sach.getSoLuong();
                }
                pm.setTongTien(tongTien);
                updatePM();
            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void fetchingData() {
        NumberFormat formatter = new DecimalFormat("#,###");
        tvTrangThai.setText("Trạng thái: "+ trangThai);
        tvTongTien.setText("Tổng đơn hàng: "+formatter.format(pm.getTongTien())+" vnđ");
        tongTien=pm.getTongTien();
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
        myList=toList(pm.getSach());
        adapter = new AdminCartAdapter(context, myList);
        rcv.setLayoutManager(linearLayoutManager);
        rcv.setAdapter(adapter);
    }
    public ArrayList<Sach> toList(Map<String,Sach> map){
        ArrayList<Sach> kq=new ArrayList<>();
        for (Map.Entry<String, Sach> entry : map.entrySet()) {
            Sach sach = entry.getValue();
            kq.add(new Sach(entry.getKey(), sach.getLoai(), sach.getTacGia(), sach.getTenSach(), sach.getHinhAnh(), sach.getSoLuong(), sach.getGiaThue(), sach.getVitridesach(), sach.getIsActive()));
        }
        return kq;
    }
    public void thanhToan() {
        updateStock(myList,-1);
    }

    public void updatePM() {

        phieuMuonDAO.update(pm, new PhieuMuonDAO.IUpdate() {
            @Override
            public void onCallBack(Boolean check) {
                if (check) {
                   // Toast.makeText(context, "Cập nhật thành công rồi nè", Toast.LENGTH_SHORT).show();
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
                    if(check)
                        Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
//
}
