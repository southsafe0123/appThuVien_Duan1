package com.teammobile.appthuvien_duan1.dao;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teammobile.appthuvien_duan1.interfaces.ITacGiaDAO;
import com.teammobile.appthuvien_duan1.model.Sach;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.util.ArrayList;

public class TacGiaDAO {
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    ArrayList<TacGia> list=new ArrayList<>();
    public TacGiaDAO(){
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference().child("TacGia");
    }
    public void insert(TacGia tacGia, ITacGiaDAO iTacGiaDAO)
    {
        reference.push().setValue(tacGia).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                iTacGiaDAO.onCallBackInsert(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iTacGiaDAO.onCallBackInsert(false);

            }
        });
    }
    public void getAll(ITacGiaDAO iTacGiaDAO)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    TacGia tacGia=data.getValue(TacGia.class);
                    list.add(new TacGia(data.getKey(),tacGia.getTenTacGia(),tacGia.getIsActive()));
                }
                iTacGiaDAO.onCallBackGetAll(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}
