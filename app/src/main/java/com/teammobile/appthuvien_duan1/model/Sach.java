package com.teammobile.appthuvien_duan1.model;

public class Sach {
    private String maSach;
    private String maLoai;
    private String maTG;
    private String tenSach;
    private String hinhAnh;
    private String soLuong;
    private String giaThue;
    private String vitridesach;

    public Sach() {
    }

    public Sach(String maSach, String maLoai, String maTG, String tenSach, String hinhAnh, String soLuong, String giaThue, String vitridesach) {
        this.maSach = maSach;
        this.maLoai = maLoai;
        this.maTG = maTG;
        this.tenSach = tenSach;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaThue = giaThue;
        this.vitridesach = vitridesach;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getMaTG() {
        return maTG;
    }

    public void setMaTG(String maTG) {
        this.maTG = maTG;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(String giaThue) {
        this.giaThue = giaThue;
    }

    public String getVitridesach() {
        return vitridesach;
    }

    public void setVitridesach(String vitridesach) {
        this.vitridesach = vitridesach;
    }
}
