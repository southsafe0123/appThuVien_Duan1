package com.teammobile.appthuvien_duan1.interfaces;

import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public interface ISachDAO {
    public void onCallBackInsert(Boolean check);
    public void onCallBackGetAll(ArrayList<Sach> list);

}
