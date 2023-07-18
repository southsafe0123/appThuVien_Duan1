package com.teammobile.appthuvien_duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Activity extends AppCompatActivity {
	private FirebaseAuth mAuth;
	private FirebaseUser mUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mAuth = FirebaseAuth.getInstance();

		EditText edtTaikhoan,edtMatkhau;
		Button btnDangnhap;
		TextView txtDangky = findViewById(R.id.txtDangky);
		edtTaikhoan = findViewById(R.id.edtTaikhoan);
		edtMatkhau = findViewById(R.id.edtMatkhau);
		btnDangnhap = findViewById(R.id.btnDangnhap);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		if(bundle!=null){

			String password = bundle.getString("password");
			String email = bundle.getString("email");
			edtTaikhoan.setText(email);
			edtMatkhau.setText(password);
		}



		btnDangnhap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String taikhoan = edtTaikhoan.getText().toString();
				String matkhau = edtMatkhau.getText().toString();

				mAuth.signInWithEmailAndPassword(taikhoan,matkhau).addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if(task.isSuccessful()){
							Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
							checkUser();
						} else{
							Toast.makeText(Login_Activity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
		});
		txtDangky.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Login_Activity.this,Register_Activity.class);
				startActivity(intent);
			}
		});

	}
	public void checkUser(){
		mUser = mAuth.getCurrentUser();
		if(mUser!=null){
			Intent intent = new Intent(Login_Activity.this,MainActivity.class);
			startActivity(intent);
		}
	}
}