package com.teammobile.appthuvien_duan1.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class TacGia {
    private String maTG;
    private String tenTacGia;
    private ArrayList<Sach> list;

    public TacGia(String maTG, String tenTacGia, ArrayList<Sach> list) {
        this.maTG = maTG;
        this.tenTacGia = tenTacGia;
        this.list = list;
    }

    public TacGia(String tenTacGia, ArrayList<Sach> list) {
        this.tenTacGia = tenTacGia;
        this.list = list;
    }

    public String getMaTG() {
        return maTG;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public ArrayList<Sach> getList() {
        return list;
    }

    public TacGia() {
    }

    @NonNull
    @Override
    public String toString() {
        return getTenTacGia();
    }
}
