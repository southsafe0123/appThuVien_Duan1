package com.teammobile.appthuvien_duan1.dao;

import com.teammobile.appthuvien_duan1.model.Cart;
import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class CartDAO {

	public ArrayList<Sach> defaultSoluong(){
		ArrayList<Sach> list = Cart.getInstance().getList();
		if(!Cart.getInstance().kiemtraThayDoi()){
			for(Sach sach: list){
				sach.setSoLuong(1);
			}
		}
		return list;
	}

}
