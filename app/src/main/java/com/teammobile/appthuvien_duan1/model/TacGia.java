package com.teammobile.appthuvien_duan1.model;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class TacGia implements Serializable {
    private String maTG;
    private String tenTacGia;
    private int isActive;

    public TacGia(String maTG, String tenTacGia, int isActive) {
        this.maTG = maTG;
        this.tenTacGia = tenTacGia;
        this.isActive = isActive;
    }

    public TacGia(String tenTacGia, int isActive) {
        this.tenTacGia = tenTacGia;
        this.isActive = isActive;
    }

    public TacGia() {
    }

    public String getMaTG() {
        return maTG;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public int getIsActive() {
        return isActive;
    }

    @NonNull
    @Override
    public String toString() {
        return getTenTacGia();
    }
}
