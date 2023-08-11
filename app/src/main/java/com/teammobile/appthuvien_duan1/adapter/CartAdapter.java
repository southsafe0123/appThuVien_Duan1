package com.teammobile.appthuvien_duan1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import com.bumptech.glide.Glide;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.MainActivity;
import com.teammobile.appthuvien_duan1.fragment.BadgeCartFragment;
import com.teammobile.appthuvien_duan1.model.Cart;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
	private ArrayList<Sach> list;
	private ArrayList<Integer> maxSoluong;
	private Context context;
	private int originalTextColor;

	private NumberFormat format;

	private BottomNavigationView bottomNavigationView;

	private AlertDialog alertDialog;



	private TongTien tongTien;



	int tongGiohang = 0;


	public void setTongTien(TongTien tongTien) {
		this.tongTien = tongTien;
	}

	public CartAdapter(ArrayList<Sach> list, ArrayList<Integer> maxSoluong, Context context) {
		this.list = list;
		this.context = context;
		this.maxSoluong = maxSoluong;
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
		Sach sach = list.get(position);

		 format = NumberFormat.getInstance(Locale.US);


		holder.txtTen.setText(""+list.get(holder.getAdapterPosition()).getTenSach());
		holder.txtGia.setText("Giá: " + format.format(list.get(holder.getAdapterPosition()).getGiaThue())+ " VND");
		holder.txtTacGia.setText("Tác giả: "+list.get(holder.getAdapterPosition()).getTacGia().getTenTacGia());
		holder.txtTheLoai.setText("Thể loại: "+list.get(holder.getAdapterPosition()).getLoai().getTenLoai());
		String soluong = "" + list.get(holder.getAdapterPosition()).getSoLuong();
		SpannableString underlineSoluong = new SpannableString(soluong);
		underlineSoluong.setSpan(new UnderlineSpan(), 0, soluong.length(), 0);
		holder.txtSoluong.setText(underlineSoluong);





		Glide.with(context).load(list.get(position).getHinhAnh()).into(holder.ivAnh);






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
//				Toast.makeText(context, ""+maxSoluong.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
				if(list.get(holder.getAdapterPosition()).getSoLuong()==maxSoluong.get(holder.getAdapterPosition())){
					Toast.makeText(context, "Bạn đã đạt giới hạn số lượng sách có", Toast.LENGTH_SHORT).show();
					holder.txtSoluong.setTextColor(Color.parseColor("#707070")	);
					holder.ivTang.setColorFilter(Color.parseColor("#C3C3C3"));

				} else{
					Sach sach = list.get(holder.getAdapterPosition());
					int soluong = sach.getSoLuong();
					soluong++;
					sach.setSoLuong(soluong);
					list.set(vitribam,sach);

					holder.txtSoluong.setTextColor(Color.RED);
					holder.ivTang.setColorFilter(Color.BLACK);
					holder.ivGiam.setColorFilter(originalTextColor);

					BadgeCartFragment.cartCount++;
					updateBadge();
					loadData();


				}
			}
		});

		holder.ivGiam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int vitribam = holder.getAdapterPosition();
				if(list.get(holder.getAdapterPosition()).getSoLuong()==1){
					canhbaoXoa(holder);
					holder.txtSoluong.setTextColor(Color.parseColor("#707070"));
					holder.ivGiam.setColorFilter(Color.parseColor("#C3C3C3"));
				} else{
					Sach sach = list.get(holder.getAdapterPosition());
					int soluong = sach.getSoLuong();
					soluong--;

					sach.setSoLuong(soluong);
					list.set(vitribam,sach);
					holder.txtSoluong.setTextColor(Color.RED);
					holder.ivGiam.setColorFilter(Color.BLACK);
					holder.ivTang.setColorFilter(originalTextColor);

					BadgeCartFragment.cartCount--;
					updateBadge();
					loadData();
					if(Integer.parseInt(holder.txtSoluong.getText().toString())<3){
						holder.txtSoluong.setTextColor(Color.BLACK);
					}

				}
			}
		});





	}

	@Override
	public int getItemCount() {

		return list.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		TextView txtTen,txtTheLoai,txtGia,txtSoluong,txtTacGia,txtTonggiatri;
		ImageView ivAnh,ivTang,ivGiam,ivXoa;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			txtGia = itemView.findViewById(R.id.txtGia);
			txtTheLoai = itemView.findViewById(R.id.txtTheLoai);
			txtSoluong = itemView.findViewById(R.id.txtSoluong);
			txtTen = itemView.findViewById(R.id.txtTen);
			txtTacGia = itemView.findViewById(R.id.txtTacgia);
			originalTextColor = txtSoluong.getCurrentTextColor();



			ivAnh = itemView.findViewById(R.id.ivAnh);
			ivTang = itemView.findViewById(R.id.ivTang);
			ivGiam = itemView.findViewById(R.id.ivGiam);
			ivXoa = itemView.findViewById(R.id.ivXoa);

		}
	}
	private void canhbaoXoa(ViewHolder holder){
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.item_xoadialog, null);
		Button btnHuy,btnXacNhan;
		btnHuy = dialogView.findViewById(R.id.btnHuy);
		btnXacNhan = dialogView.findViewById(R.id.btnXacNhan);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setView(dialogView);
		builder = new AlertDialog.Builder(context);
		builder.setView(dialogView);
		btnXacNhan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				list.remove(holder.getAdapterPosition());
				int sl= Integer.parseInt(holder.txtSoluong.getText().toString());
				BadgeCartFragment.cartCount-=sl;
				maxSoluong.remove(holder.getAdapterPosition());
				loadData();
				alertDialog.dismiss();
				updateBadge();
			}
		});
		btnHuy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
			}
		});

		alertDialog = builder.create();
		alertDialog.show();
	}

	public void updateBadge(){
		((MainActivity)context).updateCartCount(BadgeCartFragment.cartCount);
	}

	public interface TongTien{
		void thayDoiTongTien(int tongTien);
	}






	public void loadData(){
		tongGiohang = 0;
		for(Sach sach: list){
			tongGiohang +=sach.getGiaThue()*sach.getSoLuong();
		}
		if(tongTien!=null){
			tongTien.thayDoiTongTien(tongGiohang);
		}
		Cart cart = Cart.getInstance();
		cart.updateList(list,maxSoluong);


		notifyDataSetChanged();
	}
//
}
