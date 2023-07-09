package com.teammobile.appthuvien_duan1.dao;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.teammobile.appthuvien_duan1.interfaces.Callback_Interface;
import com.teammobile.appthuvien_duan1.model.User;

public class Dao_User {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userRef;
    private User user;

    public Dao_User(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        userRef = firebaseDatabase.getReference("User");
    }


    //chức năng đăng nhập.
    //truyền thông tin đăng nhập vào đây > lấy thông tin từ database về > so sánh > trả về giá trị true false kiểm tra.
    //lưu ý khi truyền vào giá trị, nhớ truyền vào thông tin như taikhoan,matkhau, new Callback_Interface.CheckBoolean() và bấm tab để có thể thực thi dữ liệu.

    //ví dụ khi sử dụng hàm login này ở loginActivity

    //login(taikhoan, matkhau, new LoginCallback() {
    //    @Override
    //    public void onLoginResult(boolean isSuccessful) {
    //        if (isSuccessful) {
    //            // Xử lý khi đăng nhập thành công
    //        } else {
    //            // Xử lý khi đăng nhập không thành công
    //        }
    //    }
    //});

    //trong đó taikhoan,matkhau là 2 dữ liệu nhận từ người dùng nhập vào

    public void login(String taikhoan, String matkhau, Callback_Interface.CheckBoolean checkBoolean){

        Query query = userRef.orderByChild("taikhoan").equalTo(taikhoan);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean check = false;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    user = dataSnapshot.getValue(User.class);
                    if(user.getMatkhau().equals(matkhau)){

                        check = true;
                        break;
                    }

                }
                checkBoolean.isSuccess(check);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                    // thao tác nếu không trùng khớp
                checkBoolean.isSuccess(false);
            }
        });
    }

    //lấy dữ liệu user đã lấy khi login.
    public User getUser(){
        return user;
    }

    public void putUser(String taikhoan,String matkhau,String hoten,String trangthai,String role){
        String maUser = userRef.push().getKey(); // Tạo key ngẫu nhiên cho người dùng

        userRef.child(maUser).setValue(new User(maUser,taikhoan,matkhau,hoten,trangthai,role));
    }
}
