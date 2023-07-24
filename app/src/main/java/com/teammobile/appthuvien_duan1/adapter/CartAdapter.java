package com.teammobile.appthuvien_duan1.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
	private ArrayList<Sach> list;
	private Context context;



	public CartAdapter(ArrayList<Sach> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		View view  = inflater.inflate(R.layout.item_cart,parent,false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		holder.txtTen.setText(list.get(holder.getAdapterPosition()).getTenSach());
		holder.txtGia.setText("" + list.get(holder.getAdapterPosition()).getGiaThue());
		holder.txtTacGia.setText(list.get(holder.getAdapterPosition()).getTacGia().getTenTacGia());
		holder.txtTheLoai.setText(list.get(holder.getAdapterPosition()).getLoai().getTenLoai());


	}

	@Override
	public int getItemCount() {

		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView txtTen,txtTheLoai,txtGia,txtSoluong,txtTacGia;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			txtGia = itemView.findViewById(R.id.txtGia);
			txtTheLoai = itemView.findViewById(R.id.txtTheLoai);
			txtSoluong = itemView.findViewById(R.id.txtSoluong);
			txtTen = itemView.findViewById(R.id.txtTen);
			txtTacGia = itemView.findViewById(R.id.txtTacgia);


		}
	}
}
