package com.example.ministop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter_lst extends BaseAdapter {
    Context context;
    ArrayList<CART> order;

    String url = "http://" + DEPRESS.ip + "/wsministop/sanpham/";

    public OrderAdapter_lst(Context context, ArrayList<CART> carts)
    {
        this.context = context;
        this.order = carts;
    }


    @Override
    public int getCount() {
        return order.size();
    }

    @Override
    public Object getItem(int position) {
        return order.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        if(convertview == null)
        {
            convertview = LayoutInflater.from(context).inflate(R.layout.layout_1dong_lst_order, null);

            //goi
            TextView tvtensp, tvgiasp, tvsoluong;
            ImageView imghinh;

            //anh xa
            imghinh = convertview.findViewById(R.id.img_order_hinhsp);
            tvtensp = convertview.findViewById(R.id.tv_order_tensp);
            tvgiasp = convertview.findViewById(R.id.tv_order_giasp);
            tvsoluong = convertview.findViewById(R.id.tv_order_soluong);

            CART order = (CART)getItem(position);

            Picasso.with(context)
                    .load(url + order.getHinhsp())
                    .placeholder(R.drawable.no_image_found)
                    .into(imghinh);
            tvtensp.setText(order.getTensp());
            tvgiasp.setText(order.getGiasp());
            tvsoluong.setText(order.getSoluong()+"");
        }
        return convertview;
    }
}
