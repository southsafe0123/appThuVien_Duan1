package com.teammobile.appthuvien_duan1.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;
import com.teammobile.appthuvien_duan1.fragment.ChitietPmFragment;
import com.teammobile.appthuvien_duan1.fragment.QLPhieuMuonFragment;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;
import java.util.Map;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PhieuMuon> list;
    private QuanLyActivity activity;
    public PhieuMuonAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        activity= (QuanLyActivity) context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rcv_phieumuon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String maPM=list.get(position).getMa();
        holder.tvMa.setText("Mã hóa đơn: "+list.get(position).getMa()+"");
        String tt="";
        switch (list.get(position).getTrangThai()){
            case 0:
                tt="Chờ xác nhận";
                break;
            default:
                break;
        }
        holder.tvTT.setText("Trạng thái: "+tt);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChitietPmFragment fragment=new ChitietPmFragment();
                Bundle bundle=new Bundle();
                bundle.putString("maPM",list.get(holder.getAdapterPosition()).getMa());
                fragment.setArguments(bundle);
                activity.loadFragment(fragment);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMa,tvTT;
        private ImageView btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa=itemView.findViewById(R.id.tvMa);
            tvTT=itemView.findViewById(R.id.tvTT);
            btnEdit=itemView.findViewById(R.id.btnEdit);
        }
    }
}
