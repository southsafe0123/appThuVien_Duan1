package com.teammobile.appthuvien_duan1.dao;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;
import java.util.Map;

public class SachDAO {
    FirebaseDatabase mDatabase;
    DatabaseReference reference;
    ArrayList<Sach> list = new ArrayList<>();

    public SachDAO() {
        mDatabase = FirebaseDatabase.getInstance();
        reference = mDatabase.getReference("Sach");
    }

    public void insert(Sach sach, ISachDAO iSachDAO) {
        String key = reference.push().getKey();
        reference.child(key).setValue(sach).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                LoaiDAO loaiDAO = new LoaiDAO();
                TacGiaDAO tacGiaDAO = new TacGiaDAO();


                iSachDAO.onCallBackInsert(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iSachDAO.onCallBackInsert(false);

            }
        });
    }

    public void getAll(ISachDAO iSachDAO) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Sach> kq = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Sach sach = data.getValue(Sach.class);
                    if (sach.getIsActive() > 0)
                        kq.add(new Sach(data.getKey(), sach.getLoai(), sach.getTacGia(), sach.getTenSach(), sach.getHinhAnh(), sach.getSoLuong(), sach.getGiaThue(), sach.getVitridesach(), sach.getIsActive()));
                }
                iSachDAO.onCallBackGetAll(kq);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getSLSachByLoai(String ma, IGetSLSachByLoai iGetSLSachByLoai) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Sach sach = data.getValue(Sach.class);
                    if (sach.getLoai().getMaLoai().equals(ma) && sach.getIsActive() > 0)
                        list.add(new Sach(data.getKey(), sach.getLoai(), sach.getTacGia(), sach.getTenSach(), sach.getHinhAnh(), sach.getSoLuong(), sach.getGiaThue(), sach.getVitridesach(), sach.getIsActive()));
                }
                iGetSLSachByLoai.onCallBack(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getSLSachByTG(String ma, IGetSLSachByTG iGetSLSachByTG) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Sach sach = data.getValue(Sach.class);
                    if (ma.equals(sach.getTacGia().getMaTG()) && sach.getIsActive() > 0) {
                        list.add(new Sach(data.getKey(), sach.getLoai(), sach.getTacGia(), sach.getTenSach(), sach.getHinhAnh(), sach.getSoLuong(), sach.getGiaThue(), sach.getVitridesach(), sach.getIsActive()));
                    }
                }


                iGetSLSachByTG.onCallBack(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getDsByTen(String ten, IGetSlSachByTen iGetSlSachByTen) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                if (ten.equals("")) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Sach sach = data.getValue(Sach.class);
                        if (sach.getIsActive() > 0) {
                            list.add(new Sach(data.getKey(), sach.getLoai(), sach.getTacGia(), sach.getTenSach(), sach.getHinhAnh(), sach.getSoLuong(), sach.getGiaThue(), sach.getVitridesach(), sach.getIsActive()));
                        }
                    }
                } else {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Sach sach = data.getValue(Sach.class);
                        if (sach.getTenSach().toLowerCase().contains(ten.toLowerCase()) && sach.getIsActive() > 0) {
                            list.add(new Sach(data.getKey(), sach.getLoai(), sach.getTacGia(), sach.getTenSach(), sach.getHinhAnh(), sach.getSoLuong(), sach.getGiaThue(), sach.getVitridesach(), sach.getIsActive()));
                        }
                    }
                }
                Log.d("OK", list.size() + "");
                iGetSlSachByTen.onCallBack(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void searchDsByTgia(String tenTg, ArrayList<Sach> sach, ISachDAO iSachDAO0) {
        ArrayList<Sach> list = new ArrayList<>();
        for (Sach sach1 : sach) {
            if (sach1.getTacGia().getTenTacGia().equals(tenTg)) {
                list.add(sach1);
            }
        }
        iSachDAO0.onCallBackGetAll(list);
    }

    public void searchDsByTloai(String tenTloai, ArrayList<Sach> sach, ISachDAO iSachDAO0) {
        ArrayList<Sach> list = new ArrayList<>();
        for (Sach sach1 : sach) {
            if (sach1.getLoai().getTenLoai().equals(tenTloai)) {
                list.add(sach1);
            }
        }
        iSachDAO0.onCallBackGetAll(list);
    }

    public void delete(String ma, DeleteCallBack deleteCallBack) {
        DatabaseReference mRef = reference.child(ma + "/isActive");
        mRef.setValue(0).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                    deleteCallBack.onCallBack(true);
            }
        });

    }

    public void update(String ma,int sl,IUpdate iUpdate)
    {
        //Sach sach1=new Sach(sach.getLoai(),sach.getTacGia(),sach.getTenSach(),sach.getHinhAnh(),sach.getSoLuong(),sach.getGiaThue(),sach.getVitridesach(),sach.getIsActive());
        reference.child(ma+"/soLuong").setValue(sl).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                iUpdate.onCallBack(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iUpdate.onCallBack(false);
            }
        });
    }

    public interface IGetSLSachByLoai {
        public void onCallBack(ArrayList<Sach> list);
    }

    public interface IGetSLSachByTG {
        public void onCallBack(ArrayList<Sach> list);
    }

    public interface IGetSlSachByTen {
        public void onCallBack(ArrayList<Sach> list);
    }

    public interface DeleteCallBack {
        public void onCallBack(Boolean check);
    }
    public interface IUpdate
    {
        public void onCallBack(Boolean check);
    }
}
