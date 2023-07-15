package com.teammobile.appthuvien_duan1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		EditText edtTaikhoan,edtMatkhau;
		Button btnDangnhap;

		edtTaikhoan = findViewById(R.id.edtTaikhoan);
		edtMatkhau = findViewById(R.id.edtMatkhau);
		btnDangnhap = findViewById(R.id.btnDangnhap);

		btnDangnhap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String taikhoan = edtTaikhoan.getText().toString();
				String matkhau = edtMatkhau.getText().toString();
				if(taikhoan.equals("admin") && matkhau.equals("123")){
					Intent intent = new Intent(Login_Activity.this,MainActivity.class);
					startActivity(intent);
					Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
				} else{
					Toast.makeText(Login_Activity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}