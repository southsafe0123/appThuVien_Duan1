package com.teammobile.appthuvien_duan1.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;
import com.teammobile.appthuvien_duan1.dao.LoaiDAO;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.dao.TacGiaDAO;
import com.teammobile.appthuvien_duan1.dao.UserDAO;
import com.teammobile.appthuvien_duan1.interfaces.IFirebaseStorage;
import com.teammobile.appthuvien_duan1.interfaces.ILoaiDAO;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.interfaces.ITacGiaDAO;
import com.teammobile.appthuvien_duan1.model.Loai;
import com.teammobile.appthuvien_duan1.model.Sach;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class QuanLyMenuFragment extends Fragment {
    private Context context;
    private int role;
    private CardView btnFMLoai,btnFMSach,btnFMTacGia,btnFMUser,btnFMHoaDon;
    private UserDAO userDAO;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_quanly_menu,container,false);
       context=getContext();

       SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Info",Context.MODE_PRIVATE);
       role=sharedPreferences.getInt("role",-1);


       btnFMLoai=view.findViewById(R.id.btnFMLoai);
       btnFMTacGia=view.findViewById(R.id.btnFMTacGia);
       btnFMSach=view.findViewById(R.id.btnFMSach);
       btnFMUser=view.findViewById(R.id.btnFMUser);
       btnFMHoaDon=view.findViewById(R.id.btnFMHoaDon);
       btnFMLoai.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                if(role==2)
                    loadFragment(new QuanLyLoaiFragment(),"fragment_loai");
                else
                    showDialog();
           }
       });
       btnFMTacGia.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(role==2)
                    loadFragment(new QuanLyTGFragment(),"fragment_tg");
                else
                    showDialog();
           }
       });
       btnFMSach.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(role==2)
                    loadFragment(new QuanLySachFragment(),"fragment_sach");
               else
                   showDialog();
           }
       });
       btnFMUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(role==2)
                    loadFragment(new QuanLyUserFragment(),"fragment_user");
               else showDialog();
           }
       });
        btnFMHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role>0){
                    loadFragment(new QLPhieuMuonFragment(),"fragment_pm");
                }
                else showDialog();
            }
        });

       return view;
    }
    public void loadFragment(Fragment fragment,String tag)
    {
        FragmentManager fm=getActivity().getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_up,R.anim.slide_down);
        ft.replace(R.id.viewFragmentQuanLy,fragment,tag);
        ft.addToBackStack(null);
        ft.commit();


    }

    public void showDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("HỆ THỐNG");
        builder.setMessage("Bạn chưa được cấp quyền này!");
        builder.setPositiveButton("Đã hiểu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }





}
