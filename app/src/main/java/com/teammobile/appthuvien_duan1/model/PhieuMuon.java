package com.teammobile.appthuvien_duan1.model;

public class PhieuMuon {
    private String maPhieuMuon;
    private String maSach;
    private String maUser;
    private String ngayThue;
    private String hanTraSach;
    private String tongTien;
    private String xacNhan;

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getMaUser() {
        return maUser;
    }

    public void setMaUser(String maUser) {
        this.maUser = maUser;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }

    public String getHanTraSach() {
        return hanTraSach;
    }

    public void setHanTraSach(String hanTraSach) {
        this.hanTraSach = hanTraSach;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getXacNhan() {
        return xacNhan;
    }

    public void setXacNhan(String xacNhan) {
        this.xacNhan = xacNhan;
    }

    public PhieuMuon() {
    }

    public PhieuMuon(String maPhieuMuon, String maSach, String maUser, String ngayThue, String hanTraSach, String tongTien, String xacNhan) {
        this.maPhieuMuon = maPhieuMuon;
        this.maSach = maSach;
        this.maUser = maUser;
        this.ngayThue = ngayThue;
        this.hanTraSach = hanTraSach;
        this.tongTien = tongTien;
        this.xacNhan = xacNhan;
    }
}
