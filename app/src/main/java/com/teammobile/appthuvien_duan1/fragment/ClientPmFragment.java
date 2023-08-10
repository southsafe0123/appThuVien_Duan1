package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.MainActivity;
import com.teammobile.appthuvien_duan1.adapter.ClientCartAdapter;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientPmFragment extends Fragment {
    private Context context;
    private PhieuMuon pm;
    private Button btnXacNhan,btnHuyDon;
    private PhieuMuonDAO phieuMuonDAO;
    private ClientCartAdapter adapter;
    private RecyclerView rcv;
    private TextView tvTinhTrang,tvTongTien;
    private MainActivity activity;
    private String tinhTrang;
    private SachDAO sachDAO;
    private ArrayList<Sach> myList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_client_pm,container,false);
        btnXacNhan=view.findViewById(R.id.btnXacNhan);
        btnHuyDon=view.findViewById(R.id.btnHuyDon);
        tvTinhTrang=view.findViewById(R.id.tvTinhTrang);
        tvTongTien=view.findViewById(R.id.tvTongTien);
        rcv=view.findViewById(R.id.rcv);
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
                    tinhTrang="";
                    switch (tt){
                        case 0:
                        {
                            tinhTrang="Chờ xác nhận";
                            btnXacNhan.setVisibility(View.GONE);
                            btnHuyDon.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 2: {
                            tinhTrang="Đã xác nhận";
                            btnXacNhan.setVisibility(View.GONE);
                            btnHuyDon.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 1:
                        {
                            tinhTrang="Hóa đơn được thay đổi";
                            btnXacNhan.setVisibility(View.VISIBLE);
                            btnHuyDon.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 3:
                        {
                            tinhTrang="Đã nhận sách";
                            btnXacNhan.setVisibility(View.GONE);
                            btnHuyDon.setVisibility(View.GONE);
                            break;
                        }
                        case 4:
                        {
                            tinhTrang="Đã trả sách";
                            btnXacNhan.setVisibility(View.GONE);
                            btnHuyDon.setVisibility(View.GONE);
                            break;
                        }
                        default:{
                            tinhTrang="Hóa đơn bị hủy";
                            btnXacNhan.setVisibility(View.GONE);
                            btnHuyDon.setVisibility(View.GONE);
                        }
                    }
                    fetchingData();
                }
            }
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setTrangThai(0);
                updatePM();
            }
        });
        btnHuyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pm.getTrangThai()>1){
                    updateStock(myList,1);
                }
                pm.setTrangThai(-1);
                updatePM();
            }
        });
        return view;
    }
    public void khoiTao()
    {
        context=getContext();
        activity= (MainActivity) context;
        phieuMuonDAO=new PhieuMuonDAO();
        sachDAO=new SachDAO();
    }
    public void fetchingData()
    {
        NumberFormat formatter = new DecimalFormat("#,###");

        tvTinhTrang.setText("Tình trạng: "+ tinhTrang);
        Map<String, Sach> map= (HashMap<String, Sach>) pm.getSach();
        myList =new ArrayList<>();
        for(Map.Entry<String,Sach> entry: map.entrySet()){
            Sach sach=entry.getValue();
            myList.add(new Sach(entry.getKey(),sach.getLoai(),sach.getTacGia(),sach.getTenSach(),sach.getHinhAnh(),sach.getSoLuong(),sach.getGiaThue(),sach.getVitridesach(),sach.getIsActive()));
        }
        tvTongTien.setText("Tổng đơn hàng: "+formatter.format(pm.getTongTien())+" vnđ");
    
        tvTongTien.setText("Tổng tiền: "+formatter.format(pm.getTongTien())+" vnđ");
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
                loadUI(myList);

            }
        });

    }
    public void loadUI(ArrayList<Sach> list)
    {
        adapter=new ClientCartAdapter(context,list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);

    }

    public void updatePM()
    {
        phieuMuonDAO.update(pm, new PhieuMuonDAO.IUpdate() {
            @Override
            public void onCallBack(Boolean check) {
                if(check){
                   // Toast.makeText(context, "Xác nhận với người dùng rồi nè!", Toast.LENGTH_SHORT).show();
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
}
