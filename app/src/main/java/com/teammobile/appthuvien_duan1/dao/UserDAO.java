package com.teammobile.appthuvien_duan1.dao;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teammobile.appthuvien_duan1.model.User;

import java.util.ArrayList;

public class UserDAO {
    FirebaseDatabase mDatabase;
    DatabaseReference reference;
    ArrayList<User> list=new ArrayList<>();
    private Context context;
    public UserDAO(Context context)
    {
        mDatabase=FirebaseDatabase.getInstance();
        reference=mDatabase.getReference().child("User");
        this.context=context;

    }
    public void insert(User user, InsertCallBack insertCallBack)
    {
       reference.child(user.getMa())
               .setValue(new User(user.getEmail(),user.getUsername(), user.getPassword(),user.getRole(),user.getIsActive()))
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       insertCallBack.onCallBack(true);
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       insertCallBack.onCallBack(false);
                   }
               });

    }
    public void getAll(GetAllCallBack getAllCallBack)
    {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    User user=data.getValue(User.class);
                    if(user.getIsActive()>0&&user.getRole()<2)
                        list.add(new User(data.getKey(),user.getEmail(),user.getUsername(), user.getPassword(), user.getRole(), user.getIsActive()));

                }
                getAllCallBack.onCallBack(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void upgradeRole(String ma,UpgradeRoleCallBack upgradeRoleCallBack)
    {
        DatabaseReference mRef=reference.child(ma+"/role");
        mRef.setValue(1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        upgradeRoleCallBack.onCallBack(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        upgradeRoleCallBack.onCallBack(false);
                    }
                });

    }
    public void loadInfo(String ma, LoadInfoCallBack loadInfoCallBack)
    {
        DatabaseReference mRef=reference.child(ma);
        SharedPreferences sharedPreferences=context.getSharedPreferences("Info",Context.MODE_PRIVATE);

        mRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                        DataSnapshot data=task.getResult();
                            sharedPreferences.edit().putInt("role",data.child("role").getValue(Integer.class)).apply();
                        sharedPreferences.edit().putString("uid",ma).apply();
                        sharedPreferences.edit().putString("email",data.child("email").getValue(String.class)).apply();
                        sharedPreferences.edit().putString("username",data.child("username").getValue(String.class)).apply();
                        sharedPreferences.edit().putString("password",data.child("password").getValue(String.class)).apply();

                }
                loadInfoCallBack.onCallBack();
            }
        });

    }
    public interface  InsertCallBack
    {
        public void onCallBack(Boolean check);
    }
    public interface GetAllCallBack
    {
        public void onCallBack(ArrayList<User> list);
    }
    public interface UpgradeRoleCallBack
    {
        public void onCallBack(Boolean check);
    }
    public interface LoadInfoCallBack
    {
        public void onCallBack();
    }
}
