package com.example.ministop;

import android.content.Context;
import android.graphics.Path;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter_rclHTTT  extends RecyclerView.Adapter<OrderAdapter_rclHTTT.KHUNGNHIN> {
    Context context;
    ArrayList<HTTHANHTOAN> hinhthuc;
    OnClickListenerOrder listener;


    String url = "http://" + DEPRESS.ip + "/wsministop/htthanhtoan/";


    public OrderAdapter_rclHTTT(Context context, ArrayList<HTTHANHTOAN> hinhthuc, OnClickListenerOrder listener) {
        this.context = context;
        this.hinhthuc = hinhthuc;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KHUNGNHIN onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_1dong_order_rcl_htthanhtoan,null);
        return new KHUNGNHIN(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter_rclHTTT.KHUNGNHIN holder, int position) {
        HTTHANHTOAN htthanhtoan = hinhthuc.get(position);

        holder.ten.setText(htthanhtoan.tenhinhthuc);
        holder.mota.setText(htthanhtoan.mota);

        Picasso.with(context)
                .load(url + htthanhtoan.tinhtrang)
                .placeholder(R.drawable.no_image_found)
                .into(holder.hinh);

        holder.hinhthuctt = hinhthuc.get(position);
    }

    @Override
    public int getItemCount() {
        return hinhthuc.size();
    }

    public class KHUNGNHIN extends RecyclerView.ViewHolder
    {
        HTTHANHTOAN hinhthuctt;
        ImageView hinh;
        TextView ten, mota;

        public KHUNGNHIN(@NonNull View itemView) {
            super(itemView);
            hinh = itemView.findViewById(R.id.img_order_hinhthanhtoan);
            ten = itemView.findViewById(R.id.tv_order_tenht);
            mota = itemView.findViewById(R.id.tv_order_mota);


            //Xu ly su kien click item cua recycle view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.ItemClickHinhthuc(hinhthuctt);
                }
            });



        }
    }
}
