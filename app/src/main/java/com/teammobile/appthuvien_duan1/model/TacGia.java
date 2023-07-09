package com.teammobile.appthuvien_duan1.model;

public class TacGia {
    private String maTG;
    private String tenTacGia;

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTenTacGia() {
        return tenTacGia;
    }

    public void setTenTacGia(String tenTacGia) {
        this.tenTacGia = tenTacGia;
    }

    public TacGia(String maTG, String tenTacGia) {
        this.maTG = maTG;
        this.tenTacGia = tenTacGia;
    }

    public TacGia() {
    }
}
