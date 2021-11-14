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

public class SPDMAdapter extends RecyclerView.Adapter<SPDMAdapter.KHUNGNHIN> {
    Context context;
    ArrayList<Products> dulieu;
    private OnClickListener listener;

    String url = "http://" + DEPRESS.ip + "/wsministop/sanpham/";

    public SPDMAdapter(Context context, ArrayList<Products> dulieu, OnClickListener listener) {
        this.context = context;
        this.dulieu = dulieu;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KHUNGNHIN onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_1dong_sanpham,null);
        return new KHUNGNHIN(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SPDMAdapter.KHUNGNHIN holder, int position) {
        Products products = dulieu.get(position);

        holder.ten.setText(products.ten);

        Picasso.with(context)
                .load(url + products.hinh)
                .placeholder(R.drawable.no_image_found)
                .into(holder.hinh);

        holder.gia.setText(formatNumberCurrency(products.gia) + " đồng");

        holder.options = dulieu.get(position);
    }

    @Override
    public int getItemCount() {
        return dulieu.size();
    }

    public class KHUNGNHIN extends RecyclerView.ViewHolder
    {
        Products options;
        ImageView hinh;
        TextView ten, gia;

        public KHUNGNHIN(@NonNull View itemView) {
            super(itemView);
            hinh = itemView.findViewById(R.id.img_product);
            ten = itemView.findViewById(R.id.tv_product1);
            gia = itemView.findViewById(R.id.tv_product3);

            //Xu ly su kien click item cua recycle view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick(options);
                }
            });
        }

    }

    //Tạo format tiền VND
    public static String formatNumberCurrency(String gia)
    {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(Double.parseDouble(gia));
    }
}
