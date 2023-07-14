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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.model.Loai;

import java.util.ArrayList;

public class LoaiAdapter extends RecyclerView.Adapter<LoaiAdapter.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Loai> list,tmp;

    public LoaiAdapter(Context context, ArrayList<Loai> list) {
        this.context = context;
        this.list = list;
        tmp=list;
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
        TextView tvTen;
        ImageView ivDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen=itemView.findViewById(R.id.tvTen);
        }
    }
}
