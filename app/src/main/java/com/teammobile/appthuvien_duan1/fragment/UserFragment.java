package com.teammobile.appthuvien_duan1.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.Login_Activity;
import com.teammobile.appthuvien_duan1.activity.MainActivity;
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;

public class UserFragment extends Fragment {
    private Context context;
    private CardView btnQuanLy,btnLogOut;
    private int role=-1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_user,container,false);
        btnQuanLy=view.findViewById(R.id.btnQuanLy);
        btnLogOut=view.findViewById(R.id.btnLogOut);
        SharedPreferences sharedPreferences=context.getSharedPreferences("Info",Context.MODE_PRIVATE);
        role=sharedPreferences.getInt("role",-1);
        btnQuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(role>0)
                    startActivity(new Intent(context, QuanLyActivity.class));
                else
                    Toast.makeText(context, "LỖI ", Toast.LENGTH_SHORT).show();
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog();
            }
        });
        return view;
    }
    public void showDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo ");
        builder.setMessage("Bạn có muốn thoát ứng dụng ?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(context, Login_Activity.class));
            }
        });
        builder.show();
    }
}
