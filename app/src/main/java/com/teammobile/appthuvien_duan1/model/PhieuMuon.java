package com.teammobile.appthuvien_duan1.model;

import com.teammobile.appthuvien_duan1.dao.UserDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class PhieuMuon implements Serializable {
    private String ma;
    Map<String,Sach> sach;
    private User user;
    private String ngayTao,ngayTra;
    private int tongTien,trangThai;

    public PhieuMuon(String ma, Map<String, Sach> sach, User user, String ngayTao, String ngayTra, int tongTien, int trangThai) {
        this.ma = ma;
        this.sach = sach;
        this.user = user;
        this.ngayTao = ngayTao;
        this.ngayTra = ngayTra;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public PhieuMuon(Map<String, Sach> sach, User user, String ngayTao, String ngayTra, int tongTien, int trangThai) {
        this.sach = sach;
        this.user = user;
        this.ngayTao = ngayTao;
        this.ngayTra = ngayTra;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public PhieuMuon() {
    }

    public String getMa() {
        return ma;
    }

    public Map<String, Sach> getSach() {
        return sach;
    }

    public User getUser() {
        return user;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public String getNgayTra() {
        return ngayTra;
    }

    public int getTongTien() {
        return tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

}
