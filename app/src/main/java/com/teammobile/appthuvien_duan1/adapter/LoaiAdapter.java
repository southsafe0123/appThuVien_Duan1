package com.teammobile.appthuvien_duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.LoaiDAO;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.model.Loai;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Loai> list,tmp;
    private LoaiDAO loaiDAO;
    private SachDAO sachDAO;

    public LoaiAdapter(Context context, ArrayList<Loai> list) {
        this.context = context;
        this.list = list;
        tmp=list;
        loaiDAO=new LoaiDAO();
        sachDAO=new SachDAO();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_rcv_loai,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTen.setText(list.get(position).getTenLoai()+"");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaiDAO.delete(list.get(holder.getAdapterPosition()).getMaLoai(), new LoaiDAO.DeleteLoaiCallBack() {
                    @Override
                    public void onCallBack(Boolean check) {
                        if(check)
                            Toast.makeText(context, "Xóa loại thành công", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Xóa loại thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        sachDAO.getSLSachByLoai(list.get(position).getMaLoai(), new SachDAO.IGetSLSachByLoai() {
            @Override
            public void onCallBack(ArrayList<Sach> list) {
                holder.tvSlSach.setText("Số sách hiện có: "+list.size());
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
                FilterResults filterResults=new FilterResults();
                ArrayList<Loai> kq=new ArrayList<>();
                if(str.equals("")){
                    kq=tmp;
                }
                else{
                    for(Loai loai: list){
                        if(loai.getTenLoai().toLowerCase().contains(str.toLowerCase())){
                            kq.add(loai);
                        }
                    }
                }

                filterResults.values=kq;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list= (ArrayList<Loai>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTen,tvSlSach;
        private ImageView btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen=itemView.findViewById(R.id.tvTen);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            tvSlSach=itemView.findViewById(R.id.tvSlSach);
        }
    }
//
}
