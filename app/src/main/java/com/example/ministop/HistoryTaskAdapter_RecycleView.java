package com.example.ministop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class HistoryTaskAdapter_RecycleView extends RecyclerView.Adapter<HistoryTaskAdapter_RecycleView.KHUNGNHIN>{

    Context context;
    ArrayList<HISTORYTASK> dulieu;

    public HistoryTaskAdapter_RecycleView(Context context, ArrayList<HISTORYTASK> dulieu) {
        this.context = context;
        this.dulieu = dulieu;
    }
    @NonNull

    @Override
    public KHUNGNHIN onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_1dong_lspc,null);
        return new KHUNGNHIN(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KHUNGNHIN holder, int position) {
        HISTORYTASK task = dulieu.get(position);


        holder.ngaygiopc.setText(task.ngaygiophancong);
        holder.tinhtrang.setText(task.tinhtranghd);
        holder.tenkhach.setText(task.tenkhachhang);
        holder.sdtkhach.setText(task.sdtkhachhang);
        holder.tongtien.setText(formatNumberCurrency(task.tongtien) + " VND");

        holder.historytask = dulieu.get(position);
    }

    @Override
    public int getItemCount() {
        return dulieu.size();
    }

    public class KHUNGNHIN extends RecyclerView.ViewHolder
    {
        HISTORYTASK historytask;
        TextView ngaygiopc;
        TextView tinhtrang;
        TextView tenkhach;
        TextView sdtkhach;
        TextView tongtien;

        public KHUNGNHIN(@NonNull View itemView) {
            super(itemView);

            ngaygiopc = itemView.findViewById(R.id.tv_ngaygiopc);
            tinhtrang = itemView.findViewById(R.id.tv_tinhtrang);
            tenkhach = itemView.findViewById(R.id.tv_tenkhach);
            sdtkhach = itemView.findViewById(R.id.tv_sdtkhachhang);
            tongtien = itemView.findViewById(R.id.tv_tongtien);
        }
    }


    //Tạo format tiền VND
    public static String formatNumberCurrency(String gia)
    {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(Double.parseDouble(gia));
    }
}
