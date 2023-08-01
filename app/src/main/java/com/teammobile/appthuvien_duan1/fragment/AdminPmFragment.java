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
import androidx.fragment.app.FragmentManager;
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
    private String maPM = "N/A";
    private Button btnSubmit, btnAccept,btnDecline;

    private SachDAO sachDAO;
    private PhieuMuonDAO phieuMuonDAO;
    private ArrayList<Sach> myList;
    private PhieuMuon pm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        khoiTao();

        view = LayoutInflater.from(context).inflate(R.layout.fragment_admin_pm, container, false);
        rcv = view.findViewById(R.id.rcv);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnAccept = view.findViewById(R.id.btnAccept);
        btnDecline=view.findViewById(R.id.btnDecline);
        if(pm.getTrangThai()==-1){
            btnDecline.setText("Hóa đơn đã hủy");
            btnDecline.setEnabled(false);
        }
        if (pm.getTrangThai() ==0) {
            btnSubmit.setVisibility(View.VISIBLE);
            btnAccept.setText("Xác nhận phiếu mượn");
            btnAccept.setVisibility(View.VISIBLE);
        }
        if(pm.getTrangThai()==3){
            btnAccept.setVisibility(View.VISIBLE);
            btnAccept.setText("Trả hàng");
            btnDecline.setEnabled(false);
        }
        if(pm.getTrangThai()==2){
            btnAccept.setVisibility(View.VISIBLE);
            btnAccept.setText("Thanh toán");
        }
        if(pm.getTrangThai()>3){
            btnAccept.setEnabled(false);
            btnDecline.setEnabled(false);
            btnDecline.setText("Hoàn thành");
        }
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pm.getTrangThai() == 0) {
                    Map<String, Sach> map = new HashMap<>();
                    int tongTien=0;

                    for (Sach item : myList) {
                        Sach sach = new Sach(item.getLoai(), item.getTacGia(), item.getTenSach(), item.getHinhAnh(), item.getSoLuong(), item.getGiaThue(), item.getVitridesach(), item.getIsActive());
                        tongTien+=sach.getGiaThue()*sach.getSoLuong();
                        map.put(item.getMaSach(), sach);
                    }
                    pm.setSach(map);
                    pm.setTrangThai(1);
                    pm.setTongTien(tongTien);
                    updatePM();
                }
            }
        });
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tt = pm.getTrangThai();
                switch (tt) {
                    case 0: {
                        pm.setTrangThai(2);
                        updatePM();
                        break;
                    }
                    case 1: {
                        Toast.makeText(context, "Bạn phải đơi người dùng duyệt!", Toast.LENGTH_SHORT).show();
                        reload();
                        break;
                    }
                    case 2: {
                        thanhToan();
                        break;
                    }
                    case 3:{
                        updateStock(myList,1);
                        pm.setTrangThai(4);
                        updatePM();
                        break;
                    }
                    default:
                }
            }
        });
        btnDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pm.getTrangThai()<3){
                    pm.setTrangThai(-1);
                    updatePM();
                }

            }
        });
        fetchingData();
        return view;
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
        adapter = new AdminCartAdapter(context, myList);
        rcv.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcv.getContext(),
                linearLayoutManager.getOrientation());
        rcv.addItemDecoration(dividerItemDecoration);
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
                    //reload();
                    activity.setTrangThai(pm.getTrangThai());
                }
            }
        });
        phieuMuonDAO.getCurPM(pm.getMa(), new PhieuMuonDAO.IGetCurPM() {
            @Override
            public void onCallBack(PhieuMuon phieuMuon) {
                Toast.makeText(context, "Thay đổi rồi nè!", Toast.LENGTH_SHORT).show();
                pm=phieuMuon;
                reload();
            }
        });
    }
    public void reload()
    {
        FragmentManager fm=activity.getSupportFragmentManager();
        if(fm!=null){
            Fragment fragment=new AdminPmFragment();
            Bundle bundle=new Bundle();
            bundle.putSerializable("pm",pm);
            fragment.setArguments(bundle);
            if(fm.findFragmentById(R.id.viewFragmentQuanLy)!=null){
                fm.popBackStack();
                fm.beginTransaction().addToBackStack(null).replace(R.id.viewFragmentQuanLy,fragment).commit();
            }

        }


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
