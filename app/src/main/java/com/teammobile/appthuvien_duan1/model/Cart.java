package com.teammobile.appthuvien_duan1.model;

import java.util.ArrayList;

public class Cart {

	private boolean check;
	public boolean kiemtraThayDoi(){
		return check;
	}

	private static Cart instance;
	private ArrayList<Sach> list;

	private Cart(){
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

	public void updateList(ArrayList<Sach> list){
		this.list = list;
		check = true;
	}

	public void addCart(Sach sach) {
		list.add(sach);
	}
}
