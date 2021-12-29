package com.example.ministop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class FinishedFixingActivity extends AppCompatActivity {

    GifImageView gifImageView;
    Button btnReturn, btnFinished;
    NGUOIDUNG user;
    TASK task;
    String getID, getMaHD;
    String url = "http://" + DEPRESS.ip + "/KhoaLuanTotNghiep/android/suaxongnhanvien";
    String url2 = "http://" + DEPRESS.ip + "/KhoaLuanTotNghiep/android/suaxongphancong";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixing_then_done);

        //Anh xa
        gifImageView = findViewById(R.id.gifFixing);
        btnReturn = findViewById(R.id.btnbackHome);
        btnFinished = findViewById(R.id.btnFinished);


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FinishedFixingActivity.this, HomeActivity.class);
                startActivity(intent1);
            }
        });

        if(DEPRESS.USER != null)
        {
            user = DEPRESS.USER;
            getID = user.getManv();
        }

        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateHD();
            }
        });

    }

    public void LoadGiaoDien(){
        //PopUpYESNO();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("1")){
                    Toast.makeText(getApplicationContext(), "Đã hoàn thành sửa chữa", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(FinishedFixingActivity.this, HomeActivity.class);
                    startActivity(intent1);
                }
                else if (response.contains("0")){
                    Toast.makeText(getApplicationContext(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("manv",getID);
                return data;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
    public void UpdateHD(){
        //PopUpYESNO();
        StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("1")){
                    LoadGiaoDien();
                }
                else if (response.contains("0")){
                    Toast.makeText(getApplicationContext(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("manv",getID);
                return data;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

}
