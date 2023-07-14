package com.teammobile.appthuvien_duan1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.teammobile.appthuvien_duan1.fragment.QuanLyMenuFragment;

public class MainActivity extends AppCompatActivity {
    NavigationBarView bnvMain;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnvMain=findViewById(R.id.bnvMain);
//        mAuth=FirebaseAuth.getInstance();
//        mAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//
//            }
//        });
        bnvMain.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager=getSupportFragmentManager();
                Fragment fragment=null;
                switch (item.getItemId()){
                    case R.id.mnHome:
                    {
                        Toast.makeText(MainActivity.this,"Bạn chọn Home",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.mnSearch:
                    {
                        Toast.makeText(MainActivity.this,"Bạn chọn Search",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.mnCart:
                    {
                        Toast.makeText(MainActivity.this,"Bạn chọn Cart",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.mnUser:
                    {
                        fragment=new QuanLyMenuFragment();
                        Toast.makeText(MainActivity.this,"Bạn chọn User",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:
                        break;
                }
                if(fragment!=null)
                    manager.beginTransaction().replace(R.id.viewFragment,fragment).commit();
                return true;
            }
        });
    }

}