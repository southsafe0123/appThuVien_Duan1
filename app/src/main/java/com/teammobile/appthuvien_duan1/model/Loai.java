package com.teammobile.appthuvien_duan1.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Loai {
    private String maLoai;
    private String tenLoai;
    private int isActive;


    public Loai(String maLoai, String tenLoai, int isActive) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
        this.isActive = isActive;
    }

    public Loai(String tenLoai, int isActive) {
        this.tenLoai = tenLoai;
        this.isActive = isActive;
    }

    public int getIsActive() {
        return isActive;
    }

    public Loai()
    {

    }
    public String getMaLoai() {
        return maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    @NonNull
    @Override
    public String toString() {
        return getTenLoai();
    }
}
