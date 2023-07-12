package com.teammobile.appthuvien_duan1.dao;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.model.Sach;

public class SachDAO {
    FirebaseDatabase mDatabase;
    DatabaseReference reference;

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
                loaiDAO.insertBook(key,sach, new LoaiDAO.LoaiInsertBook() {
                    @Override
                    public void onCallBack(Boolean check) {
                        //Toast.makeText(context, "Thêm sách vào loại thành công", Toast.LENGTH_SHORT).show();

                    }
                });
                tacGiaDAO.insertBook(key,sach, new TacGiaDAO.TGInsertBook() {
                    @Override
                    public void onCallBack(Boolean check) {
                        //Toast.makeText(context, "Thêm sách vào tác giả thành công", Toast.LENGTH_SHORT).show();

                    }
                });
                iSachDAO.onCallBackInsert(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                iSachDAO.onCallBackInsert(false);

            }
        });
    }
}
