package com.teammobile.appthuvien_duan1.model;

import java.util.ArrayList;

public class Cart {
	public Cart() {
	}

	private static Cart instance;
	private ArrayList<Sach> list;

	public static synchronized Cart getInstance(){
		if(instance==null){
			instance = new Cart();
		}
		return instance;
	}

	public ArrayList<Sach> getList() {
		return list;
	}

	public void setList(ArrayList<Sach> list) {
		this.list = list;
	}
}
