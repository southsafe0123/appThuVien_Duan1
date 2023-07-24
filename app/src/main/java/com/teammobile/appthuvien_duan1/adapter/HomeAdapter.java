package com.teammobile.appthuvien_duan1.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.fragment.HomeFragment;
import com.teammobile.appthuvien_duan1.interfaces.IGioHang;
import com.teammobile.appthuvien_duan1.model.Cart;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>  {


    private ArrayList<Sach> list;
    private Context context;
    private ArrayList<Sach> gioHang;
    private IGioHang iGioHang;

    public HomeAdapter(IGioHang iGioHang) {
        this.iGioHang = iGioHang;
    }

    public HomeAdapter(ArrayList<Sach> list, Context context){
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.tvTen.setText(list.get(holder.getAdapterPosition()).getTenSach());
        holder.tvTheloai.setText(list.get(holder.getAdapterPosition()).getLoai().getTenLoai());
        holder.tvSoluong.setText(""+list.get(holder.getAdapterPosition()).getSoLuong());
        holder.tvTacGia.setText(list.get(holder.getAdapterPosition()).getTacGia().getTenTacGia());
        holder.btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Sach sach = list.get(holder.getAdapterPosition());
                gioHang.add(sach);
                Toast.makeText(context, ""+gioHang.size(), Toast.LENGTH_SHORT).show();
//                Cart.getInstance().setList(gioHang);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen,tvTheloai,tvSoluong,tvTacGia;
        Button btnThemGioHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvTacGia = itemView.findViewById(R.id.tvTacGia);
            tvSoluong = itemView.findViewById(R.id.tvSoluong);
            tvTheloai = itemView.findViewById(R.id.tvTheloai);
            btnThemGioHang = itemView.findViewById(R.id.btnThemGioHang);
        }
    }
}





