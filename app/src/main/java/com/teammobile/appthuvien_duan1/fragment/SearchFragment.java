package com.teammobile.appthuvien_duan1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.LoaiDAO;
import com.teammobile.appthuvien_duan1.dao.TacGiaDAO;
import com.teammobile.appthuvien_duan1.interfaces.ILoaiDAO;
import com.teammobile.appthuvien_duan1.interfaces.ITacGiaDAO;
import com.teammobile.appthuvien_duan1.model.Loai;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private EditText edtTenTruyen;
    private Spinner spnTheLoai, spnTacGia;
    private Button btnTimKiem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_search, null);
        edtTenTruyen = view.findViewById(R.id.edtTenTruyen);
        spnTacGia = view.findViewById(R.id.spnTacGia);
        spnTheLoai = view.findViewById(R.id.spnTheLoai);
        btnTimKiem = view.findViewById(R.id.btnTimKiem);
        TacGiaDAO tacGiaDAO = new TacGiaDAO();
        LoaiDAO theloaiDAO = new LoaiDAO();

        
        theloaiDAO.getAll(new ILoaiDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<Loai> list) {
                ArrayAdapter<Loai> listTheLoai = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
                listTheLoai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTheLoai.setAdapter(listTheLoai);
            }
        });
        tacGiaDAO.getAll(new ITacGiaDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<TacGia> list) {
                ArrayAdapter<TacGia> listTacGia = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
                listTacGia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spnTacGia.setAdapter(listTacGia);
            }
        });




        return view;
    }
}
