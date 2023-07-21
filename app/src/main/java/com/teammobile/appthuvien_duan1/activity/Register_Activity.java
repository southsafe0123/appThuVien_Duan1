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

public class Register_Activity extends AppCompatActivity {
	private FirebaseAuth mAuth;
	private FirebaseUser mUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		EditText edtTaikhoan,edtMatkhau,edtNhaplai,edtEmail;
		Button btnDangky;

		mAuth = FirebaseAuth.getInstance();


		edtEmail = findViewById(R.id.edtNhapgmail);
		edtMatkhau = findViewById(R.id.edtMatkhau);
		edtNhaplai = findViewById(R.id.edtNhaplai);
		btnDangky = findViewById(R.id.btnDangky);

		btnDangky.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String email = edtEmail.getText().toString();
				String matkhau = edtMatkhau.getText().toString();
				String nhaplai = edtNhaplai.getText().toString();


				if(!matkhau.equals(nhaplai)){
					Toast.makeText(Register_Activity.this, "Mật khẩu không trùng", Toast.LENGTH_SHORT).show();

					return;
				}
				mAuth.createUserWithEmailAndPassword(email,nhaplai).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(@NonNull Task<AuthResult> task) {
						if(task.isSuccessful()){
							mUser = mAuth.getCurrentUser();
							String gmailnehihi = mUser.getEmail();
							Toast.makeText(Register_Activity.this,""+ gmailnehihi, Toast.LENGTH_SHORT).show();

							Bundle bundle = new Bundle();
							bundle.putString("email",gmailnehihi);
							bundle.putString("password",nhaplai);

							Intent intent = new Intent(Register_Activity.this,Login_Activity.class);
							intent.putExtras(bundle);
							startActivity(intent);
							Toast.makeText(Register_Activity.this, "Success", Toast.LENGTH_SHORT).show();
						}
						else{
							Toast.makeText(Register_Activity.this, "Fail", Toast.LENGTH_SHORT).show();

						}
					}
				});
			}


		});
	}
}