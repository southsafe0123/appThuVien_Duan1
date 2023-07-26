package com.teammobile.appthuvien_duan1.dao;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhieuMuonDAO {
    private FirebaseDatabase mDatabase;
    private DatabaseReference reference;
    private ArrayList<PhieuMuon> list=new ArrayList<>();
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
    public void getAll(GetAllCalBack getAllCalBack)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data: snapshot.getChildren()){

                    PhieuMuon pm=data.getValue(PhieuMuon.class);
                    if(pm.getTrangThai()>=0)
                        list.add(new PhieuMuon(data.getKey(),pm.getSach(),pm.getUser(),pm.getNgayTao(), pm.getNgayTra(), pm.getTongTien(), pm.getTrangThai()));

                }
                getAllCalBack.onCallBack(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void update(Map<String,Sach> map,UpdateCallBack updateCallBack)
    {
        reference.child("sach").setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                updateCallBack.onCallBack(true);
            }
        });
    }
    public void getPM(String maPM,IGetPM iGetPM)
    {
        reference.child(maPM+"/sach").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String,Sach> map=new HashMap<>();
                for(DataSnapshot data: snapshot.getChildren()){
                    map.put(data.getKey(),data.getValue(Sach.class));
                }
                iGetPM.onCallBack(map);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void update(PhieuMuon pm,IUpdate iUpdate)
    {
        PhieuMuon pm1=new PhieuMuon(pm.getSach(),pm.getUser(),pm.getNgayTao(), pm.getNgayTra(), pm.getTongTien(),pm.getTrangThai());
        reference.child(pm.getMa()).setValue(pm1).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public interface InsertCallBack
    {
        public void onCallBack(Boolean check);
    }
    public interface GetAllCalBack
    {
        public void onCallBack(ArrayList<PhieuMuon> list);
    }
    public interface UpdateCallBack
    {
        public void onCallBack(Boolean check);
    }
    public interface IGetPM
    {
        public void onCallBack(Map<String,Sach> list);
    }
    public interface IUpdate
    {
        public void onCallBack(Boolean check);
    }
}
