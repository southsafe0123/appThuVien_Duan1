package com.teammobile.appthuvien_duan1.dao;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teammobile.appthuvien_duan1.interfaces.Callback_Interface;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class Dao_Sach {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference sachRef;
    public Dao_Sach(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        sachRef = firebaseDatabase.getReference("Sach");
    }

    public void addSach(){
        String maSach = sachRef.push().getKey();
        sachRef.child(maSach).setValue(new Sach(maSach,"maloai","maTG","tensach","hinhanh","soluong","giathue","vitridesach"));
    }

    public void getSach(Callback_Interface.loadListSach callback){


        sachRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Sach> list = new ArrayList<>();
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Sach sach = dataSnapshot.getValue(Sach.class);
                    list.add(sach);
                }
                callback.listSach(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.listSach(null);
            }
        });

    }
}
