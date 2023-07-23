package com.teammobile.appthuvien_duan1.adapter;

import android.content.Context;
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

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.dao.TacGiaDAO;
import com.teammobile.appthuvien_duan1.model.Sach;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.util.ArrayList;

public class TacGiaAdapter extends RecyclerView.Adapter<TacGiaAdapter.ViewHolder> implements Filterable {
   private Context context;
    private ArrayList<TacGia> list;
    private ArrayList<TacGia> tmp;
    private TacGiaDAO tacGiaDAO;
    private SachDAO sachDAO;
    public TacGiaAdapter(Context context, ArrayList<TacGia> list) {
        this.context = context;
        this.list = list;
        this.tmp=list;
        tacGiaDAO=new TacGiaDAO();
        sachDAO=new SachDAO();
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
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tacGiaDAO.delete(list.get(holder.getAdapterPosition()).getMaTG(), new TacGiaDAO.DeleteTGCallBack() {
                    @Override
                    public void onCallBack(Boolean check) {
                        if(check)
                            Toast.makeText(context, "Xóa tác giả thành công", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Xóa tác giả thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        sachDAO.getSLSachByTG(list.get(position).getMaTG(), new SachDAO.IGetSLSachByTG() {
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
        private TextView tvTen,tvSlSach;
        private ImageView btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen=itemView.findViewById(R.id.tvTen);
            btnDelete=itemView.findViewById(R.id.btnDelete);
            tvSlSach=itemView.findViewById(R.id.tvSlSach);
        }
    }

}
