package com.teammobile.appthuvien_duan1.dao;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teammobile.appthuvien_duan1.interfaces.ILoai;
import com.teammobile.appthuvien_duan1.model.LoaiSach;

public class LoaiDAO {
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    public LoaiDAO(){
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference().child("Loai");
    }
    public void insert(LoaiSach loaiSach, ILoai iLoai)
    {
        reference.push().setValue(loaiSach).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                iLoai.onCallBack(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iLoai.onCallBack(false);

            }
        });
    }
}
