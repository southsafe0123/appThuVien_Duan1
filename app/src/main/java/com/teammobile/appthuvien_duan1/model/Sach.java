package com.teammobile.appthuvien_duan1.model;

import java.io.Serializable;

public class Sach implements Serializable {
    private String maSach;
    private Loai loai;
    private TacGia tacGia;
    private String tenSach;
    private String hinhAnh;
    private int soLuong;
    private int giaThue;
    private String vitridesach;
    private int isActive;
    public Sach() {
    }

    public Sach(String maSach, Loai loai, TacGia tacGia, String tenSach, String hinhAnh, int soLuong, int giaThue, String vitridesach, int isActive) {
        this.maSach = maSach;
        this.loai = loai;
        this.tacGia = tacGia;
        this.tenSach = tenSach;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaThue = giaThue;
        this.vitridesach = vitridesach;
        this.isActive = isActive;
    }

    public Sach(Loai loai, TacGia tacGia, String tenSach, String hinhAnh, int soLuong, int giaThue, String vitridesach, int isActive) {
        this.loai = loai;
        this.tacGia = tacGia;
        this.tenSach = tenSach;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaThue = giaThue;
        this.vitridesach = vitridesach;
        this.isActive = isActive;
    }

    public String getMaSach() {
        return maSach;
    }

    public Loai getLoai() {
        return loai;
    }

    public TacGia getTacGia() {
        return tacGia;
    }

    public String getTenSach() {
        return tenSach;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public String getVitridesach() {
        return vitridesach;
    }

    public int getIsActive() {
        return isActive;
    }
}
