package com.example.ministop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class TaskJobActivity extends AppCompatActivity {

    NGUOIDUNG user;
    String url = "http://" + DEPRESS.ip + ":81/KhoaLuanTotNghiep/android/laytrangthaiCV";

    ImageView imageView;
    TextView txtShow;
    Button btnReturn, btnUpdate;
    String getID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successful);

        //Anh xa
        txtShow = findViewById(R.id.tv_TaskShow);
        btnReturn = findViewById(R.id.btnReturnToHome);
        btnUpdate = findViewById(R.id.btn_ReceiveTask);
        imageView = findViewById(R.id.imgTask);

        if(DEPRESS.USER != null)
        {
            user = DEPRESS.USER;
            getID = user.getManv();
            LoadGiaoDien();
        }
        //
        //txtID.setText(user.get(sessionManager.ID));
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TaskJobActivity.this, HomeActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void LoadGiaoDien(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    if(response.contains("1")){
                        btnUpdate.setVisibility(View.GONE);
                        txtShow.setText("Thật tuyệt vời! Hiện tại chưa có công việc nào được giao");
                    }
                    else if (response.contains("0")){
                        btnUpdate.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.INVISIBLE);
                        txtShow.setText("Bạn đang có công việc cần sửa chữa! Hãy nhanh chóng nhận việc được giao nào!");
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
                data.put("manvien",getID);
                return data;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
