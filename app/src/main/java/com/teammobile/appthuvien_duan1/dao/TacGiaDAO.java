package com.teammobile.appthuvien_duan1.dao;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teammobile.appthuvien_duan1.interfaces.ITacGia;
import com.teammobile.appthuvien_duan1.model.TacGia;

public class TacGiaDAO {
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    public TacGiaDAO(){
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference().child("TacGia");
    }
    public void insert(TacGia tacGia,ITacGia iTacGia)
    {
        reference.push().setValue(tacGia).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                iTacGia.onCallBack(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iTacGia.onCallBack(false);

            }
        });
    }
}
