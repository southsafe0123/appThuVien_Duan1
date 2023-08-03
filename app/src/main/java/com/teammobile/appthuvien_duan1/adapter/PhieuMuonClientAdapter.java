package com.teammobile.appthuvien_duan1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teammobile.appthuvien_duan1.R;
import com.teammobile.appthuvien_duan1.activity.MainActivity;
import com.teammobile.appthuvien_duan1.dao.PhieuMuonDAO;
import com.teammobile.appthuvien_duan1.fragment.ClientPmFragment;
import com.teammobile.appthuvien_duan1.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonClientAdapter extends RecyclerView.Adapter<PhieuMuonClientAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PhieuMuon> list;
    private MainActivity activity;
    private PhieuMuonDAO phieuMuonDAO;
    public PhieuMuonClientAdapter(Context context, ArrayList<PhieuMuon> list) {
        this.context = context;
        this.list = list;
        activity= (MainActivity) context;
        phieuMuonDAO=new PhieuMuonDAO();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_rcv_pm_user,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String tt="";
        switch (list.get(position).getTrangThai())
        {
            case -1:
                tt="Bị hủy";
                holder.tvTrangThai.setTextColor(Color.parseColor("#FF0000"));
                break;
            case 0:
                tt="Chờ xác nhận";
                holder.tvTrangThai.setTextColor(Color.parseColor("#FFD700"));
                break;
            case 1:
                tt="Hóa đơn được thay đổi";
                holder.tvTrangThai.setTextColor(Color.parseColor("#9ACD32"));
                break;
            case 2:
                tt="Nhận sách tại thư viện";
                holder.tvTrangThai.setTextColor(Color.parseColor("#9370DB"));

                break;
            case 3:
                tt="Đã nhận sách";
                holder.tvTrangThai.setTextColor(Color.parseColor("#0000CD"));
                break;
            case 4:
                tt="Đã trả sách";
                holder.tvTrangThai.setTextColor(Color.parseColor("#00FF7F"));
                break;
            default:

        }
        holder.tvTrangThai.setText(tt);
        holder.tvNgay.setText("Ngày thuê: "+list.get(position).getNgayTao());
        holder.tvMaPM.setText("Mã hóa đơn: "+list.get(position).getMa());
        holder.tvTongTien.setText("Tổng tiền: "+list.get(position).getTongTien()+" VNĐ");
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setCurPM(list.get(holder.getAdapterPosition()));
                phieuMuonDAO.getCurPM(list.get(holder.getAdapterPosition()).getMa(), new PhieuMuonDAO.IGetCurPM() {
                    @Override
                    public void onCallBack(PhieuMuon phieuMuon) {
                        Fragment fragment=new ClientPmFragment();
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("pm",phieuMuon);
                        fragment.setArguments(bundle);
                        FragmentManager fm=activity.getSupportFragmentManager();
                        if(!fm.isDestroyed()&&fm.findFragmentByTag("curPM")!=null&&phieuMuon.getMa().equals(activity.getCurPM().getMa())){
                            Log.d("OK","fragment da load len");
                            Toast.makeText(context, "fragment da load len", Toast.LENGTH_SHORT).show();
                            fm.popBackStack();
                            loadFragment(fragment,"curPM");
                        }
                    }
                });
                Fragment fragment=new ClientPmFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("pm",list.get(holder.getAdapterPosition()));
                fragment.setArguments(bundle);
                loadFragment(fragment,"curPM");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaPM,tvTrangThai,tvTongTien,tvNgay;
        private ImageView btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPM=itemView.findViewById(R.id.tvMa);
            tvTrangThai=itemView.findViewById(R.id.tvTT);
            tvTongTien=itemView.findViewById(R.id.tvTongTien);
            tvNgay=itemView.findViewById(R.id.tvNgay);
            btnEdit=itemView.findViewById(R.id.btnEdit);
        }
    }
    private void loadFragment(Fragment fragment,String tag)
    {
        FragmentManager fm=activity.getSupportFragmentManager();
        if(!fm.isDestroyed()){

            fm.beginTransaction().addToBackStack(null).replace(R.id.frag_main,fragment,tag).commit();
        }
    }
}
