package com.teammobile.appthuvien_duan1.dao;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class SachDAO {
    FirebaseDatabase mDatabase;
    DatabaseReference reference;
    ArrayList<Sach> list=new ArrayList<>();
    public SachDAO()
    {
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference("Sach");

    }
    public void insert(Sach sach, ISachDAO iSachDAO)
    {
        String key=reference.push().getKey();
        reference.child(key).setValue(sach).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                LoaiDAO loaiDAO=new LoaiDAO();
                TacGiaDAO tacGiaDAO=new TacGiaDAO();


                iSachDAO.onCallBackInsert(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iSachDAO.onCallBackInsert(false);

            }
        });
    }
    public void getAll(ISachDAO iSachDAO)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Sach> kq=new ArrayList<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    Sach sach=data.getValue(Sach.class);
                    kq.add(sach);
                }
                iSachDAO.onCallBackGetAll(kq);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getSLSachByLoai(String ma,IGetSLSachByLoai iGetSLSachByLoai)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data: snapshot.getChildren()){
                    Sach sach=data.getValue(Sach.class);
                    if(sach.getLoai().getMaLoai().equals(ma)&&sach.getIsActive()>0)
                        list.add(sach);
                }
                iGetSLSachByLoai.onCallBack(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getSLSachByTG(String ma,IGetSLSachByTG iGetSLSachByTG)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data: snapshot.getChildren()){
                    Sach sach=data.getValue(Sach.class);
                    if(ma.equals(sach.getTacGia().getMaTG())&&sach.getIsActive()>0){
                        list.add(sach);
                    }
                }
                Log.d("OK",list.size()+"");
                iGetSLSachByTG.onCallBack(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })        ;
    }
    public interface IGetSLSachByLoai
    {
        public void onCallBack(ArrayList<Sach> list);
    }
    public interface IGetSLSachByTG
    {
        public void onCallBack(ArrayList<Sach> list);
    }
}
