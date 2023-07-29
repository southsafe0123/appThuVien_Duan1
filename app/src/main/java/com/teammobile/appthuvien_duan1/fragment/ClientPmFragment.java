package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.adapter.Cart3Adapter;
import com.teammobile.appthuvien_duan1.adapter.CartAdapter;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClientPmFragment extends Fragment {
    private Context context;
    private PhieuMuon phieuMuon;
    private AppCompatButton btnConfirm;
    private PhieuMuonDAO phieuMuonDAO;
    private Cart3Adapter adapter;
    private RecyclerView rcv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_client_pm,container,false);
        btnConfirm=view.findViewById(R.id.btnConfirm);
        rcv=view.findViewById(R.id.rcv);
        if(phieuMuon.getTrangThai()==1){
            btnConfirm.setVisibility(View.VISIBLE);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    phieuMuon.setTrangThai(0);
                    phieuMuonDAO.update(phieuMuon, new PhieuMuonDAO.IUpdate() {
                        @Override
                        public void onCallBack(Boolean check) {
                            if(check){
                                Toast.makeText(context, "Xác nhận hóa đơn với thủ thư", Toast.LENGTH_SHORT).show();
                                reload();
                            }
                        }
                    });
                }
            });
        }
        fetchingData();
        return view;
    }
    public void khoiTao()
    {
        context=getContext();
        Bundle bundle=getArguments();
        phieuMuon= (PhieuMuon) bundle.getSerializable("pm");
        phieuMuonDAO=new PhieuMuonDAO();
    }
    public void fetchingData()
    {
        Map<String, Sach> map= (HashMap<String, Sach>) phieuMuon.getSach();
        ArrayList<Sach> list=new ArrayList<>();
        for(Map.Entry<String,Sach> entry: map.entrySet()){
            Sach sach=entry.getValue();
            list.add(new Sach(entry.getKey(),sach.getLoai(),sach.getTacGia(),sach.getTenSach(),sach.getHinhAnh(),sach.getSoLuong(),sach.getGiaThue(),sach.getVitridesach(),sach.getIsActive()));
        }
        loadUI(list);
    }
    public void loadUI(ArrayList<Sach> list)
    {
        adapter=new Cart3Adapter(context,list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);
    }
    public void reload()
    {
        Fragment fragment=new ClientPmFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("pm",phieuMuon);
        fragment.setArguments(bundle);
        FragmentManager fm=getActivity().getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.frag_main,fragment).commit();
    }
}
