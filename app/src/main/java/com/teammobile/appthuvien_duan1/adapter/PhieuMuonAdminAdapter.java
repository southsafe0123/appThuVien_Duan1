package com.teammobile.appthuvien_duan1.adapter;

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
import com.teammobile.appthuvien_duan1.activity.QuanLyActivity;
import com.teammobile.appthuvien_duan1.fragment.AdminPmFragment;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonAdminAdapter extends RecyclerView.Adapter<PhieuMuonAdminAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PhieuMuon> list;
    private QuanLyActivity activity;
    public PhieuMuonAdminAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        activity= (QuanLyActivity) context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rcv_pm_admin,parent,false);
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
                holder.tvTT.setTextColor(Color.parseColor("#FFD700"));
                break;
            case 1:
                tt="Bị thay đổi";
                holder.tvTT.setTextColor(Color.parseColor("#9ACD32"));

                break;
            case 2:
                tt="Đã xác nhận";
                holder.tvTT.setTextColor(Color.parseColor("#9370DB"));

                break;
            case 3:
                tt="Thanh toán thành công";
                holder.tvTT.setTextColor(Color.parseColor("#0000CD"));
                break;
            case 4:
                tt="Hoàn thành";
                holder.tvTT.setTextColor(Color.parseColor("#00FF7F"));
            default:

        }
        holder.tvTT.setText(tt);
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdminPmFragment fragment=new AdminPmFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("pm",list.get(holder.getAdapterPosition()));
                activity.setTrangThai(list.get(holder.getAdapterPosition()).getTrangThai());
                fragment.setArguments(bundle);
                activity.loadFragment(fragment);

            }
        });
        holder.tvTenKH.setText("Tên KH: "+list.get(position).getUser().getUsername());
        holder.tvTongTien.setText("Tổng tiền: "+list.get(position).getTongTien()+" VNĐ");
        holder.tvNgay.setText("Ngày tạo: "+list.get(position).getNgayTao());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMa,tvTT,tvTenKH,tvTongTien,tvNgay;
        private ImageView btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMa=itemView.findViewById(R.id.tvMa);
            tvTT=itemView.findViewById(R.id.tvTT);
            btnEdit=itemView.findViewById(R.id.btnEdit);
            tvTenKH=itemView.findViewById(R.id.tvTenKH);
            tvTongTien=itemView.findViewById(R.id.tvTongTien);
            tvNgay=itemView.findViewById(R.id.tvNgay);
        }
    }
}
