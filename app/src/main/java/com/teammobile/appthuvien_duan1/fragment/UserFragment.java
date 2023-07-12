package com.teammobile.appthuvien_duan1.fragment;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Date;

public class UserFragment extends Fragment {
    private Context context;
    private LoaiDAO loaiDAO;
    private TacGiaDAO tacGiaDAO;
    private Loai curLoai;
    private TacGia curTG;
    private SachDAO sachDAO;
    private FirebaseStorage storage;
    private StorageReference reference;
    private Uri selectedImg;
    public Loai getCurLoai() {
        return curLoai;
    }

    public void setCurLoai(Loai curLoai) {
        this.curLoai = curLoai;
    }

    public TacGia getCurTG() {
        return curTG;
    }

    public void setCurTG(TacGia curTG) {
        this.curTG = curTG;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_user,container,false);
       context=getContext();
       
       storage=FirebaseStorage.getInstance();
       reference=storage.getReference();
       Button btnThemLoai=view.findViewById(R.id.btnThemLoai);
       Button btnThemTG=view.findViewById(R.id.btnThemTacGia);
       Button btnThemSach=view.findViewById(R.id.btnThemSach);
       btnThemLoai.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDialogThemLoai();
           }
       });
       btnThemTG.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDialogThemTG();
           }
       });
       btnThemSach.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showDialogThemSach();
           }
       });
       loaiDAO=new LoaiDAO();
       tacGiaDAO=new TacGiaDAO();
       return view;
    }
    public void showDialogThemLoai()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_them_loai,null);
        builder.setView(view);
        EditText edtName=view.findViewById(R.id.edtName);
        Button btnAdd=view.findViewById(R.id.btnAdd);
        Dialog dialog=builder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edtName.getText().toString();
                loaiDAO=new LoaiDAO();
                loaiDAO.insert(new Loai(name, null), new ILoaiDAO() {
                    @Override
                    public void onCallBackInsert(Boolean check) {
                        Toast.makeText(context, "Insert tác giả thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCallBackGetAll(ArrayList<Loai> list) {

                    }

                    @Override
                    public void onCallBackInsertBook(Boolean check) {

                    }


                });
            }
        });
        dialog.show();
    }
    public void showDialogThemTG()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_them_tacgia,null);
        builder.setView(view);
        EditText edtName=view.findViewById(R.id.edtName);
        Button btnAdd=view.findViewById(R.id.btnAdd);
        Dialog dialog=builder.create();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edtName.getText().toString();
                tacGiaDAO=new TacGiaDAO();
                tacGiaDAO.insert(new TacGia(name, null), new ITacGiaDAO() {

                    @Override
                    public void onCallBackInsert(Boolean check) {
                        Toast.makeText(context, "Insert tác giả thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                    @Override
                    public void onCallBackGetAll(ArrayList<TacGia> list) {

                    }
                });
            }
        });
        dialog.show();
    }
    public void showDialogThemSach()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        View view=LayoutInflater.from(context).inflate(R.layout.dialog_them_sach,null);
        builder.setView(view);
        loaiDAO=new LoaiDAO();
        tacGiaDAO=new TacGiaDAO();
        Dialog dialog=builder.create();
        EditText edtTen=view.findViewById(R.id.edtTen);
        EditText edtGia=view.findViewById(R.id.edtGia);
        EditText edtSL=view.findViewById(R.id.edtSL);
        EditText edtViTri=view.findViewById(R.id.edtViTri);
        Button btnPickImg=view.findViewById(R.id.btnPickImg);
        Button btnAdd=view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=edtTen.getText().toString();
                int gia= Integer.parseInt(edtGia.getText().toString());
                int sl=Integer.parseInt(edtSL.getText().toString());
                String viTri=edtViTri.getText().toString();

            }
        });
        AutoCompleteTextView actvLoai=view.findViewById(R.id.actvLoai);
        AutoCompleteTextView actvTG=view.findViewById(R.id.actvTG);

        loaiDAO.getAll(new ILoaiDAO() {
            @Override
            public void onCallBackInsert(Boolean check) {

            }

            @Override
            public void onCallBackGetAll(ArrayList<Loai> list) {
                loadDataLoai(list,actvLoai);
            }

            @Override
            public void onCallBackInsertBook(Boolean check) {

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
               Loai loai=getCurLoai();
               TacGia tg=getCurTG();
               String ten=edtTen.getText().toString();
               int sl= Integer.parseInt(edtSL.getText().toString());
               int gia= Integer.parseInt(edtGia.getText().toString());
               String vitri=edtViTri.getText().toString();
               //Toast.makeText(context, loai.getMaLoai()+" "+tg.getMaTG(), Toast.LENGTH_SHORT).show();
                uploadAndGetUrl(selectedImg, new IFirebaseStorage() {
                    @Override
                    public void onCallBack(String url) {
                        if(url==null)
                            Toast.makeText(context, "Ko tìm thấy link", Toast.LENGTH_SHORT).show();
                        sachDAO=new SachDAO();
                        sachDAO.insert(new Sach(loai,tg,ten,url,sl,gia,vitri), new ISachDAO() {
                            @Override
                            public void onCallBackInsert(Boolean check) {
                                if(check){
                                    //Toast.makeText(context, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
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
       });
        dialog.show();
    }
    public void pickUpImg()
    {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
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
    ActivityResultLauncher<Intent> launchSomeActivity
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
}
