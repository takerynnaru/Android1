package com.example.ministop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements OnClickListenerOrder{
    public static final String CHANNEL_ID = "Ministop";
    public static final int NOTIFICATION_ID = 1;
    Button btndathang, btngiamgia;
    NGUOIDUNG user;
    ListView lvsporder;

    RecyclerView rclhinhthuctt;
    ArrayList<HTTHANHTOAN> hinhthuctt = new ArrayList<>();
    OrderAdapter_rclHTTT orderAdapter_rclHTTT;

    ArrayList<String> sngiamgia = new ArrayList<>();


    OrderAdapter_lst OrderAdapter_lst;
    TextView lblten, lblsdt, lbldiachi, lbltiengiam, lblthanhtien, lbltongcong;
//    String url = "http://" + DEPRESS.ip + "/wsministop/getsanpham.php";
    String urlht = "http://" + DEPRESS.ip + "/wsministop/gethtthanhtoan.php";
    String urlgg = "http://" + DEPRESS.ip + "/wsministop/getgiamgia.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        //thanh tro ve home
        actionBar.setDisplayHomeAsUpEnabled(true);
        //doi mau thanh action bar
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#003894"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Đặt hàng"); //Thiết lập tiêu đề

        //anh xa
        btndathang = findViewById(R.id.btn_Order_Dathang);
        btngiamgia = findViewById(R.id.btn_Order_ApdungGiamGia);
        lbldiachi = findViewById(R.id.lbl_order_diachi);
        lblsdt = findViewById(R.id.lbl_order_sdt);
        lblten = findViewById(R.id.lbl_order_ten);
        lbltiengiam = findViewById(R.id.lbl_order_tiengiam);
        lbltongcong = findViewById(R.id.lbl_order_tongcong);
        lblthanhtien = findViewById(R.id.lbl_order_thanhtien);
        //load du lieu listview
        lvsporder = findViewById(R.id.lst_Order);
        OrderAdapter_lst = new OrderAdapter_lst(OrderActivity.this, DEPRESS.carts);
        lvsporder.setAdapter(OrderAdapter_lst);

        //recycle
        LayHinhThuc();
        rclhinhthuctt = findViewById(R.id.rcl_PhuongThucTT);
//        orderAdapter_rclHTTT = new OptionsAdapter_Recycle(this, hinhthuc, this);
        orderAdapter_rclHTTT = new OrderAdapter_rclHTTT(this,hinhthuctt ,this);
        rclhinhthuctt.setAdapter(orderAdapter_rclHTTT);
        rclhinhthuctt.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        //Truyen du lieu user
        if(DEPRESS.USER != null)
        {
            user = DEPRESS.USER;
            lblten.setText(user.getHoten());
            lblsdt.setText(user.getSdt());
            lbldiachi.setText(user.getDiachi());
        }
        //truyen du lieu tien giam
        lbltiengiam.setText("- "+0 +" VND");


        //truyen du lieu tong cong
        lbltongcong.setText(DEPRESS.ThanhToan + " VND");
        lblthanhtien.setText(DEPRESS.ThanhToan + " VND");




        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();

                DEPRESS.carts = new ArrayList<>();
                Intent intent = new Intent(getApplicationContext(), Order_SuccessfulActivity.class);

                startActivity(intent);

            }
        });

    }

    private void sendNotification() {
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.logo);

        Notification notification = new NotificationCompat.Builder(this, Notification_Application.CHANNEL_ID)
                .setContentTitle("Thông báo đặt hàng thành công")
                .setContentText("Bạn đã đặt hàng thành công, đơn hàng sẽ được vận chuyển đến bạn sớm nhất")
                .setSmallIcon(R.drawable.ic_notification)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(notificationManager != null)
        {
            notificationManager.notify(NOTIFICATION_ID, notification);
        }

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(OrderActivity.this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Thông báo đặt hàng thành công")
                .setContentText("Bạn đã đặt hàng thành công, đơn hàng sẽ được vận chuyển đến bạn sớm nhất")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());*/
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void LayHinhThuc() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        hinhthuctt.add(new HTTHANHTOAN(jsonObject.getString("idhtthanhtoan"), jsonObject.getString("tenhinhthuc"), jsonObject.getString("mota"),jsonObject.getString("tinhtrang")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                orderAdapter_rclHTTT.notifyDataSetChanged();
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlht, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
    }


//    public void LayGiamGia() {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        hinhthuctt.add(new HTTHANHTOAN(jsonObject.getString("idhtthanhtoan"), jsonObject.getString("tenhinhthuc"), jsonObject.getString("mota"),jsonObject.getString("tinhtrang")));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                orderAdapter_rclHTTT.notifyDataSetChanged();
//            }
//        };
//
//        Response.ErrorListener thatbai = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        };
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlht, thanhcong, thatbai);
//        requestQueue.add(jsonArrayRequest);
//    }

    //Tạo format tiền VND
    public static String formatNumberCurrency(String gia)
    {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(Double.parseDouble(gia));
    }

    @Override
    public void ItemClickHinhthuc(HTTHANHTOAN htthanhtoan) {

    }
}