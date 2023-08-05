package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private MainActivity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_client_pm,container,false);
        btnXacNhan=view.findViewById(R.id.btnXacNhan);
        btnHuyDon=view.findViewById(R.id.btnHuyDon);
        rcv=view.findViewById(R.id.rcv);
        phieuMuonDAO.getCurPM(activity.getCurPM().getMa(), new PhieuMuonDAO.IGetCurPM() {
            @Override
            public void onCallBack(PhieuMuon phieuMuon) {
                if(!phieuMuon.getMa().equals(activity.getCurPM().getMa()))
                    return;
                int tt=phieuMuon.getTrangThai();
                switch (tt){
                    case 0:
                    case 2: {

                        btnXacNhan.setVisibility(View.GONE);
                        btnHuyDon.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1:
                    {
                        btnXacNhan.setVisibility(View.VISIBLE);
                        btnHuyDon.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 3:
                    {
                        btnXacNhan.setVisibility(View.GONE);
                        btnHuyDon.setVisibility(View.GONE);
                        break;
                    }
                    case 4:
                    {
                        break;
                    }
                    default:{
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
