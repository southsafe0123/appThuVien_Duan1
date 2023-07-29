package com.teammobile.appthuvien_duan1.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.MainActivity;
import com.teammobile.appthuvien_duan1.fragment.ClientPmFragment;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonUserAdapter extends RecyclerView.Adapter<PhieuMuonUserAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PhieuMuon> list;
    private MainActivity activity;
    public PhieuMuonUserAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        activity= (MainActivity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rcv_pm_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String tt="";
        switch (list.get(position).getTrangThai())
        {
            case 0:
                tt="Chờ xác nhận";
                holder.tvTrangThai.setTextColor(Color.parseColor("#FFD700"));
                break;
            case 1:
                tt="Hóa đơn được thay đổi";
                holder.tvTrangThai.setTextColor(Color.parseColor("#9ACD32"));
                break;
            case 2:
                tt="Nhận sách tại thư viện";
                holder.tvTrangThai.setTextColor(Color.parseColor("#9370DB"));

                break;
            case 3:
                tt="Đã nhận sách";
                holder.tvTrangThai.setTextColor(Color.parseColor("#0000CD"));
                break;
            case 4:
                tt="Đã trả sách";
                holder.tvTrangThai.setTextColor(Color.parseColor("#00FF7F"));
                break;
            default:

        }
        holder.tvTrangThai.setText(tt);
        holder.tvNgay.setText("Ngày thuê: "+list.get(position).getNgayTao());
        holder.tvMaPM.setText("Mã hóa đơn: "+list.get(position).getMa());
        holder.tvTongTien.setText("Tổng tiền: "+list.get(position).getTongTien()+" VNĐ");
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientPmFragment fragment=new ClientPmFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("pm",list.get(holder.getAdapterPosition()));
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
        private TextView tvMaPM,tvTrangThai,tvTongTien,tvNgay;
        private ImageView btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPM=itemView.findViewById(R.id.tvMa);
            tvTrangThai=itemView.findViewById(R.id.tvTT);
            tvTongTien=itemView.findViewById(R.id.tvTongTien);
            tvNgay=itemView.findViewById(R.id.tvNgay);
            btnEdit=itemView.findViewById(R.id.btnEdit);
        }
    }
}
