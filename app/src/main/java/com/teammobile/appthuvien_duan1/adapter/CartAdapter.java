package com.teammobile.appthuvien_duan1.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.CartDAO;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.fragment.CartFragment;
import com.teammobile.appthuvien_duan1.model.Cart;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
	private ArrayList<Sach> list;
	private ArrayList<Integer> maxSoluong;
	private Context context;

	private TongTien tongTien;
	private TongTien tongTienListener;



	public void setTongTienListener(TongTien tongTienListener) {
		this.tongTienListener = tongTienListener;
	}

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

		holder.txtTen.setText("Tên sách: "+list.get(holder.getAdapterPosition()).getTenSach());
		holder.txtGia.setText("Giá tiền: " + list.get(holder.getAdapterPosition()).getGiaThue() + " VND");
		holder.txtTacGia.setText("Tác giả: "+list.get(holder.getAdapterPosition()).getTacGia().getTenTacGia());
		holder.txtTheLoai.setText("Thể loại: "+list.get(holder.getAdapterPosition()).getLoai().getTenLoai());
		holder.txtSoluong.setText(""+list.get(holder.getAdapterPosition()).getSoLuong());

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
				Toast.makeText(context, ""+maxSoluong.get(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
				if(list.get(holder.getAdapterPosition()).getSoLuong()==maxSoluong.get(holder.getAdapterPosition())){
					Toast.makeText(context, "Bạn đã đạt giới hạn số lượng sách có", Toast.LENGTH_SHORT).show();
				} else{
					Sach sach = list.get(holder.getAdapterPosition());
					int soluong = sach.getSoLuong();
					soluong++;

					sach.setSoLuong(soluong);
					list.set(vitribam,sach);
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
					Toast.makeText(context, "Bạn đã đạt giới hạn số lượng cho thuê", Toast.LENGTH_SHORT).show();
				} else{
					Sach sach = list.get(holder.getAdapterPosition());
					int soluong = sach.getSoLuong();
					soluong--;
					sach.setSoLuong(soluong);
					list.set(vitribam,sach);
					loadData();
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
				maxSoluong.remove(holder.getAdapterPosition());
				loadData();
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

	public interface TongTien{
		void thayDoiTongTien(int tongTien);
	}







	public void loadData(){
		tongGiohang = 0;
		for(Sach sach: list){
			tongGiohang += sach.getGiaThue()*sach.getSoLuong();
		}
		if(tongTien!=null){
			tongTien.thayDoiTongTien(tongGiohang);
		}
		Cart cart = Cart.getInstance();
		cart.updateList(list,maxSoluong);
		notifyDataSetChanged();
	}
}

