package com.teammobile.appthuvien_duan1.interfaces;

import com.teammobile.appthuvien_duan1.model.Sach;

import java.util.ArrayList;

public class Callback_Interface {
    public interface CheckBoolean{
        void isSuccess(boolean check);
    }
    public interface loadListSach{
        void listSach(ArrayList<Sach> list);
    }
}
