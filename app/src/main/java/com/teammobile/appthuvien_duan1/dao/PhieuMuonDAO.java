package com.teammobile.appthuvien_duan1.dao;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;

public class PhieuMuonDAO {
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    public PhieuMuonDAO()
    {
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference().child("PhieuMuon");
    }
    public void insert(PhieuMuon phieuMuon,InsertCallBack insertCallBack)
    {
        reference.push().setValue(phieuMuon).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                insertCallBack.onCallBack(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                insertCallBack.onCallBack(false);
            }
        });

    }
    public interface InsertCallBack
    {
        public void onCallBack(Boolean check);
    }

}
