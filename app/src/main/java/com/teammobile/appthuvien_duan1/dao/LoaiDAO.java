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
import com.teammobile.appthuvien_duan1.interfaces.ILoaiDAO;
import com.teammobile.appthuvien_duan1.model.Loai;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class LoaiDAO {
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    private ArrayList<Loai> list=new ArrayList<>();
    public LoaiDAO(){
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference().child("Loai");
    }
    public void insert(Loai loai, ILoaiDAO iLoaiDAO)
    {
        reference.push().setValue(loai).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                iLoaiDAO.onCallBackInsert(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iLoaiDAO.onCallBackInsert(false);

            }
        });
    }
    public void getAll(ILoaiDAO iLoaiDAO)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    Loai loai =data.getValue(Loai.class);
                    if(loai.getIsActive()>0)
                        list.add(new Loai(data.getKey(),loai.getTenLoai(),loai.getIsActive()));
                }

                iLoaiDAO.onCallBackGetAll(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void delete(String ma,DeleteLoaiCallBack deleteLoaiCallBack)
    {
        reference.child(ma).child("isActive").setValue(0).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                deleteLoaiCallBack.onCallBack(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                deleteLoaiCallBack.onCallBack(false);
            }
        });
    }
    public interface DeleteLoaiCallBack
    {
        public void onCallBack(Boolean check);
    }

}
