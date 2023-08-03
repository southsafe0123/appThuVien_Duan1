package com.teammobile.appthuvien_duan1.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.adapter.HomeAdapter;
import com.teammobile.appthuvien_duan1.adapter.SearchAdapter;
import com.teammobile.appthuvien_duan1.dao.LoaiDAO;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.dao.TacGiaDAO;
import com.teammobile.appthuvien_duan1.interfaces.ILoaiDAO;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.interfaces.ITacGiaDAO;
import com.teammobile.appthuvien_duan1.model.Loai;
import com.teammobile.appthuvien_duan1.model.Sach;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class SearchFragment extends Fragment {
    private AppCompatAutoCompleteTextView edtTenTruyen;
    private Spinner spnTheLoai, spnTacGia;
    private Button btnTimKiem;
    CheckBox chkTacgia, chkTheloai;
    private RecyclerView rvSearched;
    private SachDAO sachDAO;
    private TacGiaDAO tacGiaDAO;
    private LoaiDAO loaiDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        edtTenTruyen = view.findViewById(R.id.edtTenTruyen);
        rvSearched = view.findViewById(R.id.rvSearched);
        edtTenTruyen.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        final Context context =getContext();
        sachDAO = new SachDAO();
        tacGiaDAO = new TacGiaDAO();
        loaiDAO = new LoaiDAO();



        TacGiaDAO tacGiaDAO = new TacGiaDAO();
        LoaiDAO theloaiDAO = new LoaiDAO();

        //thanh tim kiem

        SachDAO sachDAO = new SachDAO();
        sachDAO.getAll(new ISachDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<Sach> list) {

                ArrayList<String> suggestion = new ArrayList<>();
                for (Sach sach:list){
                    suggestion.add(sach.getTenSach());
                    if(!suggestion.contains(sach.getTacGia().getTenTacGia())){
                        suggestion.add(sach.getTacGia().getTenTacGia());
                    }
                    if (!suggestion.contains(sach.getLoai().getTenLoai())){
                        suggestion.add(sach.getLoai().getTenLoai());
                    }
                }

                SearchAdapter searchAdapter = new SearchAdapter(context,suggestion);
                edtTenTruyen.setAdapter(searchAdapter);
                edtTenTruyen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String key = (String) parent.getItemAtPosition(position);
                        edtTenTruyen.clearFocus();
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                        timkiem(key,context);
                    }
                });

                edtTenTruyen.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String key = s.toString();
                        timkiem(key,context);
                    }
                });
            }
        });
        edtTenTruyen.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    timkiem(edtTenTruyen.getText().toString(),context);
                    return true;
                }
                return false;
            }
        });

        return view;
    }
    public void timkiem(String key, Context context){
        sachDAO.searchAll(key, new SachDAO.IGetSlSachByTen() {
            @Override
            public void onCallBack(ArrayList<Sach> list) {
                setAdpter(list,context);
            }
        });
    }

    public void setAdpter(ArrayList<Sach> list, Context context){
        rvSearched.setAdapter(new HomeAdapter(list,context));
        rvSearched.setLayoutManager(new LinearLayoutManager(context));
    }
}
