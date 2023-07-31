package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.adapter.CartAdapter;
import com.teammobile.appthuvien_duan1.adapter.HomeAdapter;
import com.teammobile.appthuvien_duan1.dao.CartDAO;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.interfaces.IGioHang;
import com.teammobile.appthuvien_duan1.model.Cart;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;
import com.teammobile.appthuvien_duan1.model.User;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment implements CartAdapter.TongTien {
    private HomeAdapter homeAdapter;
    private RecyclerView recyclerView;
    private TextView txtTongtien;
    private AlertDialog alertDialog;
    private Button btnSumbit;
    private User user;
    private PhieuMuonDAO phieuMuonDAO;
    private SharedPreferences sharedPreferences;
    private Context context;
    private int tongGiohang;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.RvCart);

        btnSumbit=view.findViewById(R.id.btnSumbit);
        txtTongtien = view.findViewById(R.id.txtTongtien);

        khoiTao();
        //ArrayList<Sach> list = cart.getList();
        Map<String,Sach> map=new HashMap<>();

        CartDAO cartDAO = new CartDAO();
        ArrayList<Sach> list = cartDAO.setSoluong1();
        ArrayList<Integer> maxSoluong = Cart.getInstance().getMaxSoLuong();
        CartAdapter adapter = new CartAdapter(list,maxSoluong,context);


 
        if(list==null){

        } else {
            tongGiohang=0;
            for(Sach sach: list){
                tongGiohang += sach.getGiaThue()*sach.getSoLuong();
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            if(tongGiohang==0){
                txtTongtien.setText("Giỏ hàng hiện không có sách");
            } else{
                txtTongtien.setText("Tổng đơn hàng: "+tongGiohang+" VND");
            }
        }
        adapter.setTongTien(new CartAdapter.TongTien() {
            @Override
            public void thayDoiTongTien(int tongTien) {
                if(tongGiohang==0){
                    String tongGiaTri = "Giỏ hàng hiện không có sách";
                    txtTongtien.setText(tongGiaTri);
                } else {
                    String tongGiaTri = "Tổng đơn hàng: " + tongTien + " VND";
                    SpannableString spannableString = new SpannableString(tongGiaTri);
                    spannableString.setSpan(new UnderlineSpan(), 0, tongGiaTri.length(), 0);
                    txtTongtien.setText(spannableString);
                }

            }
        });
        if(list==null || list.isEmpty()){
            Toast.makeText(context, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
        } else{

        }
        btnSumbit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(list==null || list.isEmpty()){
                    Toast.makeText(context, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                    return;
                } else{

                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.item_alertdialog, null);
                    Button btnHuy,btnXacNhan;
                    btnHuy = dialogView.findViewById(R.id.btnHuy);
                    btnXacNhan = dialogView.findViewById(R.id.btnXacnhan);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(dialogView);
                    btnXacNhan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(user==null||user.getIsActive()==0){
                                Toast.makeText(context, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            if(list!=null || !list.isEmpty()){
                                tongGiohang = 0;
                                for(Sach sach: list){
                                    tongGiohang += sach.getGiaThue()*sach.getSoLuong();
                                    Sach item=new Sach(sach.getLoai(),sach.getTacGia(),sach.getTenSach(),sach.getHinhAnh(),sach.getSoLuong(),sach.getGiaThue(),sach.getVitridesach(),sach.getIsActive());
                                    map.put(sach.getMaSach(),item);
                                }
                                String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                                PhieuMuon pm=new PhieuMuon(map,user,timeStamp,"N/A",tongGiohang,0);

                                phieuMuonDAO.insert(pm, new PhieuMuonDAO.InsertCallBack() {
                                    @Override
                                    public void onCallBack(Boolean check) {
                                        if(list==null || list.isEmpty()){
                                            Toast.makeText(context, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                                            alertDialog.dismiss();
                                            return;
                                        }

                                        if(check){
                                            Toast.makeText(context, "Thanh toán đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                            list.clear();
                                            adapter.loadData();
                                            txtTongtien.setText("Xin cảm ơn quý khách!");
                                        }
                                        else
                                            Toast.makeText(context, "Thanh toán đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                });
                            }
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
            }
        });





        return view;
    }
   private void khoiTao()
   {
       phieuMuonDAO=new PhieuMuonDAO();
       sharedPreferences= getActivity().getSharedPreferences("Info", Context.MODE_PRIVATE);
       String uid=sharedPreferences.getString("uid","N/A");
       String email=sharedPreferences.getString("email","N/A");
       String username=sharedPreferences.getString("username","N/A");
       String password=sharedPreferences.getString("password","N/A");
       int role=sharedPreferences.getInt("role",-1);
       int isActive=sharedPreferences.getInt("isActive",-1);
       user=new User(uid,email,username,password,role,isActive);
       context=getContext();
   }





    @Override
    public void thayDoiTongTien(int tongTien) {
        String tongGiaTri = "Tổng đơn hàng: " + tongTien + " VND";
        SpannableString spannableString = new SpannableString(tongGiaTri);
        spannableString.setSpan(new UnderlineSpan(), 0, tongGiaTri.length(), 0);
        txtTongtien.setText(spannableString);
    }
}
