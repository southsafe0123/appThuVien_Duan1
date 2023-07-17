package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.MainActivity;
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;

public class UserFragment extends Fragment {
    private Context context;
    private Button btnQuanLy;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_user,container,false);
        btnQuanLy=view.findViewById(R.id.btnQuanLy);
        btnQuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, QuanLyActivity.class));
            }
        });
        return view;
    }
}
