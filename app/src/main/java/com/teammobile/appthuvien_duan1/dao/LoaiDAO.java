package com.teammobile.appthuvien_duan1.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoaiDAO {
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    public LoaiDAO(){
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference().child("Loai");
    }
}
