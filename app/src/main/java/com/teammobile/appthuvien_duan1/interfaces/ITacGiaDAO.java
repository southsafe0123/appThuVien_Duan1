package com.teammobile.appthuvien_duan1.interfaces;

import com.teammobile.appthuvien_duan1.model.TacGia;

import java.util.ArrayList;

public interface ITacGiaDAO {
    public void onCallBackInsert(Boolean check);
    public void onCallBackGetAll(ArrayList<TacGia> list);
}
