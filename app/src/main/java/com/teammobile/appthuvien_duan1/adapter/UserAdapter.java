package com.teammobile.appthuvien_duan1.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.dao.UserDAO;
import com.teammobile.appthuvien_duan1.model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> list;
    private UserDAO userDAO;
    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        userDAO=new UserDAO();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rcv_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTen.setText("Tên : "+list.get(position).getUsername());
        String chucVu=list.get(position).getRole()==0?"Người dùng":"Thử thư";
        holder.tvChucVu.setText("Chức vụ: "+chucVu);
        if(list.get(position).getRole()==1)
            loadStatus(holder);
        holder.btnCapQuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userDAO.upgradeRole(list.get(holder.getAdapterPosition()).getMa(), new UserDAO.UpgradeRoleCallBack() {
                    @Override
                    public void onCallBack(Boolean check) {
                        if(check)
                            Toast.makeText(context, "Upgrade thủ thư thành công", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(context, "Upgrade thủ thư thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
    public void loadStatus(ViewHolder holder)
    {
        Drawable img=context.getResources().getDrawable(R.drawable.baseline_check_24);
        img.setBounds(0, 0, 60, 60);
        holder.btnCapQuyen.setCompoundDrawables(img,null,null,null);
        holder.btnCapQuyen.setText("đã cấp quyền");
        holder.btnCapQuyen.setEnabled(false);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTen,tvChucVu;
        private AppCompatButton btnCapQuyen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen=itemView.findViewById(R.id.tvTen);
            tvChucVu=itemView.findViewById(R.id.tvChucVu);
            btnCapQuyen=itemView.findViewById(R.id.btnCapQuyen);
        }
    }
}
