package com.teammobile.appthuvien_duan1.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Loai {
    private String maLoai;
    private String tenLoai;
    private ArrayList<Sach> list;

    public Loai(String maLoai, String tenLoai, ArrayList<Sach> list) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.list = list;
    }

    public Loai(String tenLoai, ArrayList<Sach> list) {
        this.tenLoai = tenLoai;
        this.list = list;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public ArrayList<Sach> getList() {
        return list;
    }

    public Loai() {

    }

    @NonNull
    @Override
    public String toString() {
        return getTenLoai();
    }
}
