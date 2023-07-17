package com.teammobile.appthuvien_duan1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.util.ArrayList;

public class TacGiaAdapter extends RecyclerView.Adapter<TacGiaAdapter.ViewHolder> implements Filterable {
   private Context context;
    private ArrayList<TacGia> list;
    private ArrayList<TacGia> tmp;

    public TacGiaAdapter(Context context, ArrayList<TacGia> list) {
        this.context = context;
        this.list = list;
        this.tmp=list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rcv_tacgia,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTen.setText(list.get(position).getTenTacGia());
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
                String str= constraint.toString();
                ArrayList<TacGia> kq=new ArrayList<>();
                if(str.equals("")){
                    kq=tmp;
                }
                else{
                    for(TacGia tacGia: list){
                        if(tacGia.getTenTacGia().toLowerCase().contains(str.toLowerCase())){
                            kq.add(tacGia);
                        }
                    }
                }

                FilterResults filterResults=new FilterResults();
                filterResults.values=kq;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list= (ArrayList<TacGia>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen=itemView.findViewById(R.id.tvTen);
        }
    }
}
