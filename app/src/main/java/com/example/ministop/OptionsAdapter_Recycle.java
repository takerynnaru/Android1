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

public class OptionsAdapter_Recycle extends RecyclerView.Adapter<OptionsAdapter_Recycle.KHUNGNHIN> {
    Context context;
    ArrayList<Options> dulieu;
    private OnClickListener listener;


    String url = "http://" + DEPRESS.ip + "/wsministop/danhmuc/";

    public OptionsAdapter_Recycle(Context context, ArrayList<Options> dulieu, OnClickListener listener) {
        this.context = context;
        this.dulieu = dulieu;
        this.listener = listener;
    }

    @NonNull
    @Override
    public KHUNGNHIN onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_1dong_options,null);
        return new KHUNGNHIN(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionsAdapter_Recycle.KHUNGNHIN holder, int position) {
        Options options = dulieu.get(position);

        holder.ten.setText(options.ten);

        Picasso.with(context)
                .load(url + options.hinh)
                .placeholder(R.drawable.no_image_found)
                .into(holder.hinh);

        holder.options = dulieu.get(position);
    }

    @Override
    public int getItemCount() {
        return dulieu.size();
    }

    public class KHUNGNHIN extends RecyclerView.ViewHolder
    {
        Options options;
        ImageView hinh;
        TextView ten;

        public KHUNGNHIN(@NonNull View itemView) {
            super(itemView);
            hinh = itemView.findViewById(R.id.img_option);
            ten = itemView.findViewById(R.id.tv_option);


            //Xu ly su kien click item cua recycle view
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.ItemClick(options);
//                }
//            });
        }
    }
}
