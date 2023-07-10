package com.teammobile.appthuvien_duan1.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.LoaiDAO;
import com.teammobile.appthuvien_duan1.interfaces.ILoai;
import com.teammobile.appthuvien_duan1.model.LoaiSach;

public class UserFragment extends Fragment {
    Context context;
    LoaiDAO loaiDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_user,container,false);
       context=getContext();
       Button btnThemLoai=view.findViewById(R.id.btnThemLoai);
       btnThemLoai.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDialogThem();
           }
       });
       return view;
    }
    public void showDialogThem()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_them_loai,null);
        builder.setView(view);
        EditText edtName=view.findViewById(R.id.edtName);
        Button btnAdd=view.findViewById(R.id.btnAdd);
        Dialog dialog=builder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edtName.getText().toString();
                loaiDAO=new LoaiDAO();
                loaiDAO.insert(new LoaiSach(name), new ILoai() {
                    @Override
                    public void onCallBack(Boolean check) {
                        if(check){
                            Toast.makeText(context, "Thêm loại thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else
                            Toast.makeText(context, "Thêm loại thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        dialog.show();
    }
}
