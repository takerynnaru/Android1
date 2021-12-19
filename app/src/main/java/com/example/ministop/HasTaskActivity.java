package com.example.ministop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HasTaskActivity extends AppCompatActivity {

    ImageView imageView;
    Button btnReturn, btnContinue;
    TextView txtMoTa, txtMaSP, txtTenSP, txtSoLuong, txtDonGia, txtThanhTien;

    RecyclerView recyclerView;
    ArrayList<TASK> data = new ArrayList<>();
    TaskAdapter_RecycleView taskAdapter_recycleView;
    String id = DEPRESS.USER.getManv();
    String url = "http://" + DEPRESS.ip + ":81/KhoaLuanTotNghiep/public/xemviecphancong.php?id=" + id;
    NGUOIDUNG user;
    String getID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co_taskl);

        //Anh xa
        recyclerView = findViewById(R.id.rv_task);

        txtMoTa = findViewById(R.id.tv_motaCV);
        btnContinue = findViewById(R.id.btn_nhanTask);
        btnReturn = findViewById(R.id.btn_coTask_Return);
        imageView = findViewById(R.id.img_sanpham);
        //
        //ds = new ArrayList<>();

        if (DEPRESS.USER != null) {
            user = DEPRESS.USER;
            getID = user.getManv();
            txtMoTa.setText(user.getMotacongviec());


            taskAdapter_recycleView = new TaskAdapter_RecycleView(this, data);
            recyclerView.setAdapter(taskAdapter_recycleView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
            LayPhanCong();
        }

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HasTaskActivity.this, HomeActivity.class);
                startActivity(intent1);
            }
        });
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HasTaskActivity.this, FinishedFixingActivity.class);
                startActivity(intent2);
            }
        });
    }

    public void LayPhanCong() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        data.add(new TASK(jsonObject.getString("mahd"), jsonObject.getString("manv"),
                                jsonObject.getString("makh"), jsonObject.getString("ngaylap"), jsonObject.getString("tongtien"),
                                jsonObject.getString("tinhtranghd"),jsonObject.getString("ngaygiodieuphoi"),
                                jsonObject.getString("tenkhachhang"),jsonObject.getString("sdtkhachhang"),
                                jsonObject.getString("email"),jsonObject.getString("diachi"),jsonObject.getString("ngaysinh"),
                                jsonObject.getString("matkhau"),jsonObject.getString("macthd"),jsonObject.getString("masp"),
                                jsonObject.getString("soluong"),jsonObject.getString("dongia"),jsonObject.getString("thanhtien"),
                                jsonObject.getString("tensanpham"),jsonObject.getString("hinhanh"),
                                jsonObject.getString("soluongton"),jsonObject.getString("thoihanbaohanh"),
                                jsonObject.getString("maloai"),jsonObject.getString("madm"),jsonObject.getString("mansx"),
                                jsonObject.getString("mancc"),jsonObject.getString("makhuyenmai")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                taskAdapter_recycleView.notifyDataSetChanged();
            }
        };
        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
    }


 }
