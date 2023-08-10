package com.teammobile.appthuvien_duan1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class QLSachAdapter extends RecyclerView.Adapter<QLSachAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Sach> list,tmp;
    private SachDAO sachDAO;
    public QLSachAdapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
        this.tmp=list;
        sachDAO=new SachDAO();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rcv_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getHinhAnh()).into(holder.ivHinh);
        holder.tvTen.setText(list.get(position).getTenSach());
        holder.tvSL.setText("Tồn: "+list.get(position).getSoLuong());
        String maSach=list.get(position).getMaSach();
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(maSach);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String str=constraint.toString();
                ArrayList<Sach> kq=new ArrayList<>();
                if(str.equals(""))
                    kq=tmp;
                else{
                    for(Sach sach: list){
                        if(sach.getTenSach().toLowerCase().contains(str.toLowerCase())||
                       sach.getTacGia().getTenTacGia().toLowerCase().contains( str.toLowerCase())||
                        sach.getLoai().getTenLoai().toLowerCase().contains(str.toLowerCase())){
                            kq.add(sach);
                        }
                    }
                }
               FilterResults results=new FilterResults();
                results.values=kq;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list= (ArrayList<Sach>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHinh,btnDelete;
        private TextView tvTen,tvSL,tvDoanhThu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinh=itemView.findViewById(R.id.ivHInh);
            tvTen=itemView.findViewById(R.id.tvTen);
            tvSL=itemView.findViewById(R.id.tvSL);
            btnDelete=itemView.findViewById(R.id.btnDelete);
        }
    }
    public void showDialog(String maSach)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("HỆ THỐNG");
        builder.setMessage("Xóa sẽ ko thể hoàn tác ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sachDAO.delete(maSach, new SachDAO.DeleteCallBack() {
                    @Override
                    public void onCallBack(Boolean check) {
                        if(check)
                            Toast.makeText(context, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Xóa sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
//
}
