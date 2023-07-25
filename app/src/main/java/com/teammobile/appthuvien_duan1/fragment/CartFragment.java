package com.teammobile.appthuvien_duan1.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.adapter.CartAdapter;
import com.teammobile.appthuvien_duan1.adapter.HomeAdapter;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.interfaces.IGioHang;
import com.teammobile.appthuvien_duan1.model.Cart;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;
import com.teammobile.appthuvien_duan1.model.Sach;
import com.teammobile.appthuvien_duan1.model.User;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private HomeAdapter homeAdapter;
    private RecyclerView recyclerView;
    private Button btnSumbit;
    private User user;
    private PhieuMuonDAO phieuMuonDAO;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.RvCart);
        btnSumbit=view.findViewById(R.id.btnSumbit);
        Cart cart = Cart.getInstance();
        khoiTao();
        ArrayList<Sach> list = cart.getList();
        if(list==null){

        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new CartAdapter(list,getContext()));
        }
        btnSumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list!=null){
//                    PhieuMuon pm=new PhieuMuon(list,)
//                    phieuMuonDAO.insert();
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

   }
}
