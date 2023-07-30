package com.teammobile.appthuvien_duan1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.LoaiDAO;
import com.teammobile.appthuvien_duan1.dao.TacGiaDAO;
import com.teammobile.appthuvien_duan1.interfaces.ILoaiDAO;
import com.teammobile.appthuvien_duan1.interfaces.ITacGiaDAO;
import com.teammobile.appthuvien_duan1.model.Loai;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SearchFragment extends Fragment {
    private EditText edtTenTruyen;
    private Spinner spnTheLoai, spnTacGia;
    private Button btnTimKiem;
    CheckBox chkTacgia, chkTheloai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        edtTenTruyen = view.findViewById(R.id.edtTenTruyen);
        spnTacGia = view.findViewById(R.id.spnTacGia);
        spnTheLoai = view.findViewById(R.id.spnTheLoai);
        btnTimKiem = view.findViewById(R.id.btnTimKiem);
        chkTacgia = view.findViewById(R.id.chkTacgia);
        chkTheloai = view.findViewById(R.id.chkTheloai);

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

        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!chkTacgia.isChecked() && !chkTheloai.isChecked()){
                    String ten = edtTenTruyen.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("ten", ten);
                    SearchFragment2 searchFragment2 = new SearchFragment2();
                    searchFragment2.setArguments(bundle);
                    FragmentManager fm= requireActivity().getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.frag_main,searchFragment2).commit();
                } else if (chkTacgia.isChecked() && !chkTheloai.isChecked()){

                } else if (chkTacgia.isChecked() && chkTheloai.isChecked()){

                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chkTacgia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chkTacgia.isChecked()){
                    spnTacGia.setVisibility(View.VISIBLE);
                } else {
                    spnTacGia.setVisibility(View.GONE);
                }
            }
        });

        chkTheloai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chkTheloai.isChecked()){
                    spnTheLoai.setVisibility(View.VISIBLE);
                } else {
                    spnTheLoai.setVisibility(View.GONE);
                }
            }
        });

    }
}
