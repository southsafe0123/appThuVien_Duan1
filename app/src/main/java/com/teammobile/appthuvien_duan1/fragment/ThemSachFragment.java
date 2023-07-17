package com.teammobile.appthuvien_duan1.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.LoaiDAO;
import com.teammobile.appthuvien_duan1.dao.SachDAO;
import com.teammobile.appthuvien_duan1.dao.TacGiaDAO;
import com.teammobile.appthuvien_duan1.interfaces.IFirebaseStorage;
import com.teammobile.appthuvien_duan1.interfaces.ILoaiDAO;
import com.teammobile.appthuvien_duan1.interfaces.ISachDAO;
import com.teammobile.appthuvien_duan1.interfaces.ITacGiaDAO;
import com.teammobile.appthuvien_duan1.model.Loai;
import com.teammobile.appthuvien_duan1.model.Sach;
import com.teammobile.appthuvien_duan1.model.TacGia;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ThemSachFragment extends Fragment {
    private Context context;
    private ImageView btnClose;
    private LoaiDAO loaiDAO;
    private TacGiaDAO tacGiaDAO;
    private SachDAO sachDAO;
    private Button btnAdd,btnPickImg;
    private Loai curLoai;
    private TacGia curTG;
    private EditText edtTen,edtGia,edtSL,edtViTri;
    private AutoCompleteTextView actvLoai,actvTG;
    private Uri selectedImg;
    private StorageReference reference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getContext();
        View view=LayoutInflater.from(context).inflate(R.layout.fragment_them_sach,container,false);
        loaiDAO=new LoaiDAO();
        tacGiaDAO=new TacGiaDAO();
        sachDAO=new SachDAO();
        btnClose=view.findViewById(R.id.btnClose);
        btnAdd=view.findViewById(R.id.btnAdd);
        edtTen=view.findViewById(R.id.edtTen);
        edtGia=view.findViewById(R.id.edtGia);
        edtSL=view.findViewById(R.id.edtSL);
        edtViTri=view.findViewById(R.id.edtViTri);
        btnPickImg=view.findViewById(R.id.btnPickImg);
        btnAdd=view.findViewById(R.id.btnAdd);
        actvLoai=view.findViewById(R.id.actvLoai);
        actvTG=view.findViewById(R.id.actvTG);
        reference= FirebaseStorage.getInstance().getReference();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        loaiDAO.getAll(new ILoaiDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<Loai> list) {
                loadDataLoai(list,actvLoai);
            }


        });
        tacGiaDAO.getAll(new ITacGiaDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<TacGia> list) {
                Toast.makeText(context, ""+list.size(), Toast.LENGTH_SHORT).show();

                loadDataTG(list,actvTG  );
            }
        });
        btnPickImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickUpImg();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themSach(view);
            }
        });

        return view;
    }
    public void closeFragment()
    {
        FragmentManager fm=getActivity().getSupportFragmentManager();
        fm.popBackStack();
    }
    public void themSach(View view)
    {


        Loai loai=getCurLoai();
        TacGia tg=getCurTG();
        String ten=edtTen.getText().toString();
        int gia= Integer.parseInt(edtGia.getText().toString());
        int sl=Integer.parseInt(edtSL.getText().toString());
        String viTri=edtViTri.getText().toString();
        uploadAndGetUrl(selectedImg, new IFirebaseStorage() {
            @Override
            public void onCallBack(String url) {
                if(url==null)
                    Toast.makeText(context, "Ko tìm thấy link", Toast.LENGTH_SHORT).show();
                sachDAO=new SachDAO();
                sachDAO.insert(new Sach(loai,tg,ten,url,sl,gia,viTri,1), new ISachDAO() {
                    @Override
                    public void onCallBackInsert(Boolean check) {
                        if(check){
                            //Toast.makeText(context, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                            closeFragment();
                        }
                        else{
                            Toast.makeText(context, "Thêm sách thành công", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCallBackGetAll(ArrayList<Sach> list) {

                    }
                });

            }
        });



    }
    public void uploadAndGetUrl(Uri uri, IFirebaseStorage iFirebaseStorage)
    {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());

        StorageReference refImg=reference.child("images/"+timeStamp);
        refImg.putFile(selectedImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                refImg.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Toast.makeText(context, ""+uri.toString(), Toast.LENGTH_SHORT).show();
                        iFirebaseStorage.onCallBack(uri.toString());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "FAIL", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    public void loadDataLoai(ArrayList<Loai> list, AutoCompleteTextView view)
    {

        ArrayAdapter<Loai> adapter=new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, android.R.id.text1,list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof Loai) {
                    Loai loai = (Loai) item;
                    // do something with the studentInfo object
                    Toast.makeText(context, ""+loai.getTenLoai(), Toast.LENGTH_SHORT).show();
                    setCurLoai(loai);
                }
            }
        });
    }
    public void loadDataTG(ArrayList<TacGia> list, AutoCompleteTextView view)
    {
        ArrayAdapter<TacGia> adapter=new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, android.R.id.text1,list);
        view.setAdapter(adapter);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item instanceof TacGia) {
                    TacGia tacGia = (TacGia) item;
                    // do something with the studentInfo object
                    Toast.makeText(context, ""+tacGia.getTenTacGia(), Toast.LENGTH_SHORT).show();
                    setCurTG(tacGia);
                }
            }
        });
    }
    private void setCurTG(TacGia tacGia) {
        this.curTG=tacGia;
    }

    public void setCurLoai(Loai curLoai) {
        this.curLoai = curLoai;
    }
    public Loai getCurLoai() {
        return this.curLoai;
    }
    public TacGia getCurTG() {
        return curTG;
    }
    public void pickUpImg()
    {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }
    private ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        selectedImg = data.getData();

                    }
                }
            });

}
