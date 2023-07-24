package com.teammobile.appthuvien_duan1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
	private ArrayList<Sach> list;
	private Context context;

	private ArrayList<Integer> soluonglist;

	private int soluong;

	public CartAdapter(ArrayList<Sach> list, Context context) {
		this.list = list;
		this.context = context;
		soluonglist = new ArrayList<>();
		for(int i = 0;list.size()>i;i++){
			soluonglist.add(1);
		}
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
		soluong = soluonglist.get(holder.getAdapterPosition());
		holder.txtSoluong.setText(""+soluong);

		holder.ivXoa.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				canhbaoXoa(holder);
			}
		});

		holder.ivTang.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int vitribam = holder.getAdapterPosition();
				int soluong = soluonglist.get(vitribam);

				if(soluong>2){
					Toast.makeText(context, "Bạn đã đạt giới hạn số lượng cho thuê", Toast.LENGTH_SHORT).show();
				} else{
					soluong++;
					soluonglist.set(vitribam,soluong);
					notifyDataSetChanged();
				}

			}
		});

		holder.ivGiam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int vitribam = holder.getAdapterPosition();
				int soluong = soluonglist.get(vitribam);

				if(soluong==1){
					canhbaoXoa(holder);
				} else{
					soluong--;
					soluonglist.set(vitribam,soluong);
					notifyDataSetChanged();
				}
			}
		});




	}

	@Override
	public int getItemCount() {

		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView txtTen,txtTheLoai,txtGia,txtSoluong,txtTacGia;
		ImageView ivAnh,ivTang,ivGiam,ivXoa;
		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			txtGia = itemView.findViewById(R.id.txtGia);
			txtTheLoai = itemView.findViewById(R.id.txtTheLoai);
			txtSoluong = itemView.findViewById(R.id.txtSoluong);
			txtTen = itemView.findViewById(R.id.txtTen);
			txtTacGia = itemView.findViewById(R.id.txtTacgia);

			ivAnh = itemView.findViewById(R.id.ivAnh);
			ivTang = itemView.findViewById(R.id.ivTang);
			ivGiam = itemView.findViewById(R.id.ivGiam);
			ivXoa = itemView.findViewById(R.id.ivXoa);

		}
	}
	private void canhbaoXoa(ViewHolder holder){

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Bạn có chắc muốn bỏ khỏi giỏ hàng?");
		builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				list.remove(holder.getAdapterPosition());
				soluonglist.remove(holder.getAdapterPosition());
				notifyDataSetChanged();
			}
		});

		builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}
}

