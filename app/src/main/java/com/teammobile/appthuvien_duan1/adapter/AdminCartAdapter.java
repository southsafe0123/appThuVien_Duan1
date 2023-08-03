package com.teammobile.appthuvien_duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class AdminCartAdapter extends RecyclerView.Adapter<AdminCartAdapter.ViewHodler> {
    private Context context;
    private ArrayList<Sach> list;
    private QuanLyActivity activity;

    public AdminCartAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        activity= (QuanLyActivity) context;

    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_rcv_cart,parent,false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodler holder, int position) {
        Glide.with(context).load(list.get(position).getHinhAnh()).into(holder.ivHinh);
        holder.tvSL.setText(list.get(position).getSoLuong()+"");
        holder.tvTen.setText(list.get(position).getTenSach());
        int sl= Integer.parseInt(holder.tvSL.getText().toString());
        if(activity.getTrangThai()==0){
            holder.btnTang.setVisibility(View.VISIBLE);
            holder.btnGiam.setVisibility(View.VISIBLE);
            holder.btnTang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mx=activity.getStock().get(list.get(holder.getAdapterPosition()).getMaSach()).getSoLuong();
                    if(sl==mx){
                        Toast.makeText(context, "Số sách đạt sl tối đa!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int i=list.get(holder.getAdapterPosition()).getSoLuong();
                    list.get(holder.getAdapterPosition()).setSoLuong(i+1);
                    notifyDataSetChanged();
                }
            });
            holder.btnGiam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sl==0){
                        Toast.makeText(context, "Ko thể giảm sl sách!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int i=list.get(holder.getAdapterPosition()).getSoLuong();
                    list.get(holder.getAdapterPosition()).setSoLuong(i-1);
                    notifyDataSetChanged();
                }
            });
        }
        holder.tvGia.setText("Giá: "+list.get(position).getSoLuong()*list.get(position).getGiaThue()+" VNĐ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHodler extends RecyclerView.ViewHolder {
        private ImageView ivHinh,btnTang,btnGiam;
        private TextView tvSL,tvTen,tvGia;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            ivHinh=itemView.findViewById(R.id.ivHinh);
            btnTang=itemView.findViewById(R.id.btnTang);
            btnGiam=itemView.findViewById(R.id.btnGiam);
            tvSL=itemView.findViewById(R.id.tvSL);
            tvTen=itemView.findViewById(R.id.tvTen);
            tvGia=itemView.findViewById(R.id.tvGia);
        }
    }
}
