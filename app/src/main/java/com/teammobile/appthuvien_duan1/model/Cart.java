package com.teammobile.appthuvien_duan1.model;

import com.teammobile.appthuvien_duan1.dao.CartDAO;

import java.util.ArrayList;

public class Cart {


	private boolean check;
	public boolean kiemtraThayDoi(){
		return check;
	}

	private static Cart instance;
	private ArrayList<Sach> list;
	private ArrayList<Integer> maxSoluong;

	private Cart(){
		maxSoluong = new ArrayList<>();
		list = new ArrayList<>();
	}

	public static synchronized Cart getInstance(){
		if(instance==null){
			instance = new Cart();
		}
		return instance;
	}

	public ArrayList<Sach> getList() {
		return list;
	}

	public ArrayList<Integer> getMaxSoLuong() {
		return maxSoluong;
	}

	public void updateList(ArrayList<Sach> list,ArrayList<Integer> maxSoluong){
		this.list = list;
		this.maxSoluong = maxSoluong;
		check = true;
	}

	public void addCart(Sach sach) {
		list.add(sach);
		maxSoluong.add(sach.getSoLuong());
		check = false;
	}
}
