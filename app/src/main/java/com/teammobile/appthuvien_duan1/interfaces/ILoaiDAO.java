package com.teammobile.appthuvien_duan1.interfaces;

import com.teammobile.appthuvien_duan1.model.Loai;

import java.util.ArrayList;

public interface ILoaiDAO {
    public void onCallBackInsert(Boolean check);
    public void onCallBackGetAll(ArrayList<Loai> list);
}
