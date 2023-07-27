package com.teammobile.appthuvien_duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class Cart3Adapter extends RecyclerView.Adapter<Cart3Adapter.ViewHolder> {
    private Context context;
    private ArrayList<Sach> list;

    public Cart3Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rcv_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTenSach.setText(list.get(position).getTenSach());
        Glide.with(context).load(list.get(position).getHinhAnh()).into(holder.ivHinh);
        holder.tvSL.setText(list.get(position).getSoLuong()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenSach,tvSL;
        private ImageView ivHinh,btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSach=itemView.findViewById(R.id.tvTen);
            tvSL=itemView.findViewById(R.id.tvSL);
            ivHinh=itemView.findViewById(R.id.ivHinh);
        }
    }
}
