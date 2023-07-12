package com.teammobile.appthuvien_duan1.model;

public class Sach {
    private String maSach;
    private Loai loai;
    private TacGia tacGia;
    private String tenSach;
    private String hinhAnh;
    private int soLuong;
    private int giaThue;
    private String vitridesach;

    public Sach() {
    }

    public Sach(TacGia tacGia, String tenSach, String hinhAnh, int soLuong, int giaThue, String vitridesach) {
        this.tacGia = tacGia;
        this.tenSach = tenSach;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaThue = giaThue;
        this.vitridesach = vitridesach;
    }

    public Sach(Loai loai, String tenSach, String hinhAnh, int soLuong, int giaThue, String vitridesach) {
        this.loai = loai;
        this.tenSach = tenSach;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaThue = giaThue;
        this.vitridesach = vitridesach;
    }

    public Sach(Loai loai, TacGia tacGia, String tenSach, String hinhAnh, int soLuong, int giaThue, String vitridesach) {
        this.loai = loai;
        this.tacGia = tacGia;
        this.tenSach = tenSach;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaThue = giaThue;
        this.vitridesach = vitridesach;
    }

    public Sach(String maSach, Loai loai, TacGia tacGia, String tenSach, String hinhAnh, int soLuong, int giaThue, String vitridesach) {
        this.maSach = maSach;
        this.loai = loai;
        this.tacGia = tacGia;
        this.tenSach = tenSach;
        this.hinhAnh = hinhAnh;
        this.soLuong = soLuong;
        this.giaThue = giaThue;
        this.vitridesach = vitridesach;
    }

    public String getMaSach() {
        return maSach;
    }

    public Loai getLoaiSach() {
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
}
