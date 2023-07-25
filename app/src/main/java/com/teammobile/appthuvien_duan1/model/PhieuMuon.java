package com.teammobile.appthuvien_duan1.model;

import com.teammobile.appthuvien_duan1.dao.UserDAO;

import java.util.ArrayList;

public class PhieuMuon {
    private String ma;
    private ArrayList<Sach> list;
    private User user;
    private String ngayTao,ngayTra;
    private int tongTien,trangThai;

    public PhieuMuon(String ma, ArrayList<Sach> list, User user, String ngayTao, String ngayTra, int tongTien, int trangThai) {
        this.ma = ma;
        this.list = list;
        this.user = user;
        this.ngayTao = ngayTao;
        this.ngayTra = ngayTra;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public PhieuMuon(ArrayList<Sach> list, User user, String ngayTao, String ngayTra, int tongTien, int trangThai) {
        this.list = list;
        this.user = user;
        this.ngayTao = ngayTao;
        this.ngayTra = ngayTra;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public ArrayList<Sach> getList() {
        return list;
    }

    public PhieuMuon() {
    }

    public String getMa() {
        return ma;
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
