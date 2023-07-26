package com.teammobile.appthuvien_duan1.dao;

import android.sax.ElementListener;

import com.teammobile.appthuvien_duan1.model.Cart;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class CartDAO {

	public ArrayList<Sach> defaultSoluong(){
		ArrayList<Sach> list = Cart.getInstance().getList();
		if(!Cart.getInstance().kiemtraThayDoi()){
			for(int i = 0; i<list.size(); i++){
				if(i == list.size()-1){
					list.get(i).setSoLuong(1);
				}
			}
		}
		return list;
	}

}
