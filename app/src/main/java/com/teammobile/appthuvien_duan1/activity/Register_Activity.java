package com.teammobile.appthuvien_duan1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;
import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.UserDAO;
import com.teammobile.appthuvien_duan1.model.User;

import java.util.HashMap;
import java.util.Map;

public class Register_Activity extends AppCompatActivity {
	private FirebaseAuth mAuth;
	private FirebaseUser mUser;
	private UserDAO userDAO;
	private EditText edtUsername;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		userDAO=new UserDAO(this);
		EditText edtTaikhoan,edtMatkhau,edtNhaplai,edtEmail;
		Button btnDangky, btnLogin;

		mAuth = FirebaseAuth.getInstance();


		edtEmail = findViewById(R.id.edtNhapgmail);
		edtMatkhau = findViewById(R.id.edtMatkhau);
		edtNhaplai = findViewById(R.id.edtNhaplai);
		btnDangky = findViewById(R.id.btnDangky);

		btnLogin = findViewById(R.id.btnLogin);


		edtUsername=findViewById(R.id.edtTaikhoan);

		btnDangky.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String taikhoan=edtUsername.getText().toString();
				String email = edtEmail.getText().toString();
				String matkhau = edtMatkhau.getText().toString();
				String nhaplai = edtNhaplai.getText().toString();
				Map<String,Object> map=new HashMap<>();




				if (taikhoan.isEmpty() && email.isEmpty() && matkhau.isEmpty() && nhaplai.isEmpty()) {
					Toast.makeText(Register_Activity.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
					return;
				}else if (taikhoan.isEmpty()) {
						 Toast.makeText(Register_Activity.this, "Vui lòng nhập tài khoản", Toast.LENGTH_SHORT).show();
					 } else if (taikhoan.matches(".*[@#$%^&+=]+.*")) {
					Toast.makeText(Register_Activity.this, "Không được sử dụng ký tự đặc biệt để đăng ký", Toast.LENGTH_SHORT).show();
					}else if (email.isEmpty()) {
						Toast.makeText(Register_Activity.this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
					} else if (matkhau.matches(".*[@#$%^&+=]+.*")) {
						Toast.makeText(Register_Activity.this, "Không được sử dụng ký tự đặc biệt để đăng ký", Toast.LENGTH_SHORT).show();
					} else if (matkhau.isEmpty()) {
						Toast.makeText(Register_Activity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
					} else if (!matkhau.equals(nhaplai)) {
						Toast.makeText(Register_Activity.this, "Mật khẩu không trùng", Toast.LENGTH_SHORT).show();
						return;
					} else if (matkhau.length() < 6) {
						Toast.makeText(Register_Activity.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
					} else if (!matkhau.matches(".*[a-zA-Z].*")) {
						Toast.makeText(Register_Activity.this, "Mật khẩu phải chứa ít nhất 1 ký tự chữ", Toast.LENGTH_SHORT).show();
					} else if (!matkhau.matches(".*\\d.*")) {
						Toast.makeText(Register_Activity.this, "Mật khẩu phải chứa ít nhất 1 số", Toast.LENGTH_SHORT).show();
					} else {
						mAuth.createUserWithEmailAndPassword(email, nhaplai).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								if (task.isSuccessful()) {
									mUser = mAuth.getCurrentUser();

									User user = new User(mUser.getUid(), email, taikhoan, matkhau, 0, 1);

									userDAO.insert(user, new UserDAO.InsertCallBack() {
										@Override
										public void onCallBack(Boolean check) {
											if (check) {
												Toast.makeText(Register_Activity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
											} else {
												Toast.makeText(Register_Activity.this, "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show();
											}
										}
									});

									Bundle bundle = new Bundle();
									bundle.putString("email", email);
									bundle.putString("password", nhaplai);

									Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
									intent.putExtras(bundle);
									startActivity(intent);
									Toast.makeText(Register_Activity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(Register_Activity.this, "Email không hợp lê", Toast.LENGTH_SHORT).show();
								}
							}
						});
				}

			}


		});
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Register_Activity.this, Login_Activity.class);
				startActivity(intent);
			}
		});
	}
//
}