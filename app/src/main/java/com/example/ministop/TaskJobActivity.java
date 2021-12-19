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
    Button btnReturn;
    String getID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_successful);

        //Anh xa
        txtShow = findViewById(R.id.tv_TaskShow);
        btnReturn = findViewById(R.id.btnReturnToHome);
        imageView = findViewById(R.id.imgTask);

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

}
