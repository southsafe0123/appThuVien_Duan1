package com.teammobile.appthuvien_duan1.model;

import java.util.ArrayList;

public class Cart {

	private ArrayList<Sach> listSach;
	private boolean check;
	public boolean kiemtraThayDoi(){
		return check;
	}

	private static Cart instance;
	private ArrayList<Sach> list;

	private Cart(){
		listSach = new ArrayList<>();
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

	public ArrayList<Sach> getListSach() {
		return listSach;
	}

	public void updateList(ArrayList<Sach> list){
		this.list = list;
		check = true;
	}

	public void addCart(Sach sach) {
		list.add(sach);
		listSach.add(sach);
	}
}
