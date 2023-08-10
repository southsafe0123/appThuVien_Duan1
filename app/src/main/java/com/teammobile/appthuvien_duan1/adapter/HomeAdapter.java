package com.teammobile.appthuvien_duan1.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import androidx.annotation.Size;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.MainActivity;
import com.teammobile.appthuvien_duan1.dao.CartDAO;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.fragment.BadgeCartFragment;
import com.teammobile.appthuvien_duan1.fragment.HomeFragment;
import com.teammobile.appthuvien_duan1.interfaces.IGioHang;
import com.teammobile.appthuvien_duan1.model.Cart;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.sql.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>  {


    private ArrayList<Sach> list;
    private Context context;
    private ArrayList<Sach> gioHang;
    private IGioHang iGioHang;

    private MainActivity mainActivity;

    private CartDAO cartDAO;
    private NumberFormat format;
    private Cart cart;
    private Sach sach;

    public HomeAdapter(IGioHang iGioHang) {
        this.iGioHang = iGioHang;
    }

    public HomeAdapter(ArrayList<Sach> list, Context context){
        this.list = list;
        this.context = context;
        cart = Cart.getInstance();
        gioHang = new ArrayList<>();
        cartDAO = new CartDAO();
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
        Glide.with(context).load(list.get(position).getHinhAnh()).fitCenter().into(holder.ivHinh);
        format = NumberFormat.getInstance(Locale.US);
        holder.tvGia.setText("Giá: "+""+format.format(list.get(holder.getAdapterPosition()).getGiaThue())+" VND");
        holder.tvTen.setText(list.get(holder.getAdapterPosition()).getTenSach());
        holder.tvTheloai.setText("Thể loại: "+list.get(holder.getAdapterPosition()).getLoai().getTenLoai());
        holder.tvTacGia.setText("Tác giả: "+list.get(holder.getAdapterPosition()).getTacGia().getTenTacGia());

        if(list.get(holder.getAdapterPosition()).getSoLuong()<1){
            holder.tvSoluong.setText("Hết hàng");
            holder.tvSoluong.setTextSize(19);
            holder.tvSoluong.setTextColor(Color.RED);
        }else {
            holder.tvSoluong.setText("Còn hàng");
            holder.tvSoluong.setTextColor(Color.GREEN);
        }

        if (list.get(holder.getAdapterPosition()).getSoLuong() < 1) {
            holder.tvSoluong.setText("Hết hàng");
            holder.tvSoluong.setTextColor(Color.RED);
            holder.btnThemGioHang.setEnabled(false);
            holder.btnThemGioHang.setText("Đã hết hàng");
            holder.btnThemGioHang.setBackgroundColor(Color.RED);
            holder.btnThemGioHang.setTextColor(Color.WHITE);
            holder.btnThemGioHang.setBackgroundResource(R.drawable.btn_hethang);
            holder.tvGia.setText("");
        } else {
            holder.btnThemGioHang.setEnabled(true);
            holder.btnThemGioHang.setText("Thêm vào giỏ hàng");
            holder.btnThemGioHang.setTextColor(Color.WHITE);
            holder.btnThemGioHang.setBackgroundResource(R.drawable.backgr_btn2);
            holder.btnThemGioHang.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    sach = list.get(holder.getAdapterPosition());
                    if(sach.getSoLuong()<1){
                        Toast.makeText(context, "Sách đã hết hàng", Toast.LENGTH_SHORT).show();
                    }else{
                        gioHang = cart.getList();
                        boolean flag = false;
                        if(gioHang.isEmpty()){
                            setGioHang();
                        } else{

                            for(int i =0;i<gioHang.size();i++){
                                if (gioHang.get(i).getMaSach().equals(list.get(holder.getAdapterPosition()).getMaSach())){
                                    flag = true;
                                    Toast.makeText(context, "Giỏ hàng đã có sách này", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if (!flag){
//                        Toast.makeText(context, ""+sach.getMaSach(), Toast.LENGTH_SHORT).show();
                                setGioHang();
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen,tvTheloai,tvSoluong,tvTacGia,tvGia;
        Button btnThemGioHang;
        ImageView ivHinh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGia = itemView.findViewById(R.id.tvGia);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvTacGia = itemView.findViewById(R.id.tvTacGia);
            tvSoluong = itemView.findViewById(R.id.tvSoluong);
            tvTheloai = itemView.findViewById(R.id.tvTheloai);
            btnThemGioHang = itemView.findViewById(R.id.btnThemGioHang);
            ivHinh = itemView.findViewById(R.id.ivHinh);
        }
    }
    public void setGioHang(){
        Toast.makeText(context, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
        BadgeCartFragment.cartCount++;
        ((MainActivity)context).updateCartCount(BadgeCartFragment.cartCount);
        cart.addCart(sach);
        gioHang = cartDAO.setSoluong1();
    }

}





