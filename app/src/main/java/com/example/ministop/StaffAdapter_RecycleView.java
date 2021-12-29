package com.example.ministop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class StaffAdapter_RecycleView extends RecyclerView.Adapter<StaffAdapter_RecycleView.KHUNGNHIN> implements Filterable{

    Context context;
    ArrayList<NGUOIDUNG> dulieu;
    ArrayList<NGUOIDUNG> dulieusearch;
    OnClickListener listener;


    String url = "http://" + DEPRESS.ip + "/KhoaLuanTotNghiep/public/img/user/";

    public StaffAdapter_RecycleView(Context context, ArrayList<NGUOIDUNG> dulieu, OnClickListener listener) {
        this.context = context;
        this.dulieu = dulieu;
        this.listener = listener;
        this.dulieusearch = dulieu;
    }
    @NonNull

    @Override
    public KHUNGNHIN onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_1dong_nhanvienkt,null);
        return new KHUNGNHIN(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KHUNGNHIN holder, int position) {
        NGUOIDUNG nguoidung = dulieu.get(position);

        Picasso.with(context)
                .load(url + nguoidung.hinhanh)
                .placeholder(R.drawable.no_image_found)
                .into(holder.hinh);

        holder.ten.setText(nguoidung.tennhanvien);
        //holder.mota.setText(products.mota);
        //set format cho giá
        holder.id.setText("ID: "+ nguoidung.manv);
        holder.nguoidung = dulieu.get(position);
    }

    @Override
    public int getItemCount() {
        return dulieu.size();
    }

    public class KHUNGNHIN extends RecyclerView.ViewHolder
    {
        NGUOIDUNG nguoidung;
        CircleImageView hinh;
        TextView ten;
        TextView id;
        //TextView mota;

        public KHUNGNHIN(@NonNull View itemView) {
            super(itemView);

            hinh = itemView.findViewById(R.id.img_staff);
            ten = itemView.findViewById(R.id.tv_staffName);
            //mota = itemView.findViewById(R.id.tv_product2);
            id = itemView.findViewById(R.id.tv_staffID);


            //Xu ly su kien click item cua recycle view
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick(nguoidung);
                }
            });
        }
    }

    //Search view cho san pham
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty())
                {
                    dulieu = dulieusearch;
                }
                else
                {
                    ArrayList<NGUOIDUNG> list = new ArrayList<>();
                    for(NGUOIDUNG nguoidung : dulieusearch)
                    {
                        if(nguoidung.getTennhanvien().toLowerCase().contains(strSearch.toLowerCase()))
                        {
                            list.add(nguoidung);
                        }
                    }
                    dulieu = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dulieu;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dulieu = (ArrayList<NGUOIDUNG>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
