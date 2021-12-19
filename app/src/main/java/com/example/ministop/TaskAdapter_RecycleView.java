package com.example.ministop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class TaskAdapter_RecycleView extends RecyclerView.Adapter<TaskAdapter_RecycleView.KHUNGNHIN>{

    Context context;
    ArrayList<TASK> dulieu;


    String url = "http://" + DEPRESS.ip + ":81/KhoaLuanTotNghiep/public/img/";

    public TaskAdapter_RecycleView(Context context, ArrayList<TASK> dulieu) {
        this.context = context;
        this.dulieu = dulieu;
    }
    @NonNull

    @Override
    public KHUNGNHIN onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_1dong_task,null);
        return new KHUNGNHIN(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KHUNGNHIN holder, int position) {
        TASK task = dulieu.get(position);

        Picasso.with(context)
                .load(url + task.hinhanh)
                .placeholder(R.drawable.no_image_found)
                .into(holder.hinh);

        holder.ten.setText(task.tensanpham);
        holder.id.setText(task.masp);
        holder.soluong.setText(task.soluong);
        holder.dongia.setText(formatNumberCurrency(task.dongia) + " VND");
        holder.thanhtien.setText(formatNumberCurrency(task.thanhtien) + " VND");

        holder.task = dulieu.get(position);
    }

    @Override
    public int getItemCount() {
        return dulieu.size();
    }

    public class KHUNGNHIN extends RecyclerView.ViewHolder
    {
        TASK task;
        ImageView hinh;
        TextView ten;
        TextView id;
        TextView dongia;
        TextView soluong;
        TextView thanhtien;

        public KHUNGNHIN(@NonNull View itemView) {
            super(itemView);

            hinh = itemView.findViewById(R.id.img_sanpham);
            ten = itemView.findViewById(R.id.tv_TenSP);
            id = itemView.findViewById(R.id.tv_maSP);
            dongia = itemView.findViewById(R.id.tv_donGia);
            soluong = itemView.findViewById(R.id.tv_soluongSP);
            thanhtien = itemView.findViewById(R.id.tv_thanhTien);
        }
    }


    //Tạo format tiền VND
    public static String formatNumberCurrency(String gia)
    {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(Double.parseDouble(gia));
    }
}
