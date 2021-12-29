package com.example.ministop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryTaskActivity extends AppCompatActivity {
    Button button;
    RecyclerView recyclerView;

    ArrayList<HISTORYTASK> data = new ArrayList<>();
    HistoryTaskAdapter_RecycleView historyTaskAdapter_recycleView;
    String id = DEPRESS.USER.getManv();
    String url = "http://" + DEPRESS.ip + "/KhoaLuanTotNghiep/public/lichsuphancong.php?id=" + id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsuphancong);

        //Anh xa
        button = findViewById(R.id.btn_lspc_returnHome);
        recyclerView = findViewById(R.id.rv_lspc);

        historyTaskAdapter_recycleView = new HistoryTaskAdapter_RecycleView(this, data);
        recyclerView.setAdapter(historyTaskAdapter_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        LayLSPhanCong();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(HistoryTaskActivity.this, HomeActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void LayLSPhanCong() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        data.add(new HISTORYTASK(jsonObject.getString("malspc"), jsonObject.getString("mahd"),
                                jsonObject.getString("manv"), jsonObject.getString("ngaygiophancong"), jsonObject.getString("makh"),
                                jsonObject.getString("ngaylap"),jsonObject.getString("tongtien"),
                                jsonObject.getString("tinhtranghd"),jsonObject.getString("ngaygiodieuphoi"),
                                jsonObject.getString("tennhanvien"),jsonObject.getString("sdtnhanvien"), jsonObject.getString("gioitinh"),
                                jsonObject.getString("ngaysinh"),jsonObject.getString("email"),jsonObject.getString("diachi"),
                                jsonObject.getString("macv"),jsonObject.getString("tendangnhap"),jsonObject.getString("matkhau"),
                                jsonObject.getString("trangthai"),jsonObject.getString("motacongviec"),
                                jsonObject.getString("hinhanh"),jsonObject.getString("tenkhachhang"),
                                jsonObject.getString("sdtkhachhang")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                historyTaskAdapter_recycleView.notifyDataSetChanged();
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
