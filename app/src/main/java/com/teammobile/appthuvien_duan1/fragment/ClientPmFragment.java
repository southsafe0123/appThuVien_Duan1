package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.MainActivity;
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;
import com.teammobile.appthuvien_duan1.adapter.ClientCartAdapter;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;

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
    private TextView tvTinhTrang;
    private MainActivity activity;
    private String tinhTrang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_client_pm,container,false);
        btnXacNhan=view.findViewById(R.id.btnXacNhan);
        btnHuyDon=view.findViewById(R.id.btnHuyDon);
        tvTinhTrang=view.findViewById(R.id.tvTinhTrang);
        rcv=view.findViewById(R.id.rcv);
        phieuMuonDAO.getCurPM(pm.getMa(), new PhieuMuonDAO.IGetCurPM() {
            @Override
            public void onCallBack(PhieuMuon phieuMuon) {
                if(phieuMuon.getMa().equals(pm.getMa())==false){
                    Toast.makeText(context, "OKOK", Toast.LENGTH_SHORT).show();
                    return;

                }
                int tt=phieuMuon.getTrangThai();
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
                        tinhTrang="Bị hủy";
                        btnXacNhan.setVisibility(View.GONE);
                        btnHuyDon.setVisibility(View.GONE);
                    }

                }
                pm=phieuMuon;
                fetchingData();
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
        Bundle bundle=getArguments();
        pm = (PhieuMuon) bundle.getSerializable("pm");
        phieuMuonDAO=new PhieuMuonDAO();

    }
    public void fetchingData()
    {
        tvTinhTrang.setText("Tình trạng: "+ tinhTrang);
        Map<String, Sach> map= (HashMap<String, Sach>) pm.getSach();
        ArrayList<Sach> list=new ArrayList<>();
        for(Map.Entry<String,Sach> entry: map.entrySet()){
            Sach sach=entry.getValue();
            list.add(new Sach(entry.getKey(),sach.getLoai(),sach.getTacGia(),sach.getTenSach(),sach.getHinhAnh(),sach.getSoLuong(),sach.getGiaThue(),sach.getVitridesach(),sach.getIsActive()));
        }
        loadUI(list);
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
                    Toast.makeText(context, "Xác nhận với người dùng rồi nè!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        
    }
}
