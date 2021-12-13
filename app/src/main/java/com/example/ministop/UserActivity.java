package com.example.ministop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    Button btnSave, btnLogout;
    NGUOIDUNG user;
    ImageView avatarUser;
    TextView tvNameAva, txtHoTen, txtSDT, txtEmail, txtNgaySinh , txtDiaChi, txtGioiTinh;
    RadioButton rdoNam, rdoNu;
    String sdt, hoten, email, diachi;
    String getID;

    String url = "http://" + DEPRESS.ip + ":81/KhoaLuanTotNghiep/android/xemnhanvienkythuat";
    String url_update = "http://" + DEPRESS.ip + ":81/KhoaLuanTotNghiep/android/suanhanvien";
    String url_image = "http://" + DEPRESS.ip + ":81/KhoaLuanTotNghiep/public/img/user/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ActionBar actionBar = getSupportActionBar();
        //thanh tro ve home
        actionBar.setDisplayHomeAsUpEnabled(true);
        //doi mau thanh action bar
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#003894"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Thông tin nhân viên"); //Thiết lập tiêu đề
        //String title = actionBar.getTitle().toString();

        //anh xa
        btnSave = findViewById(R.id.btn_User_Save);
        btnLogout = findViewById(R.id.btn_User_LogOut);
        avatarUser = findViewById(R.id.imgAvatar);
        tvNameAva = findViewById(R.id.lbl_User_HoTenAva);
        txtHoTen = findViewById(R.id.txt_User_Hoten);
        txtSDT = findViewById(R.id.txt_User_Phone);
        txtEmail = findViewById(R.id.txt_User_Email);
//        txtGioiTinh = findViewById(R.id.txt_User_GioiTinh);
        rdoNam = findViewById(R.id.rdb_User_Nam);
        rdoNu = findViewById(R.id.rdb_User_Nu);

        txtNgaySinh = findViewById(R.id.txt_User_Birthday);
        txtDiaChi = findViewById(R.id.txt_User_Address);


//        Gui du lieu tu Login
        if(DEPRESS.USER != null)
        {
            user = DEPRESS.USER;
            //load hình từ url
            getID = user.getManv();
           Picasso.with(this).load(url_image + user.getHinhanh()).placeholder(R.drawable.no_image_found).into(avatarUser);
//
            tvNameAva.setText(user.getTennhanvien());
            txtHoTen.setText(user.getTennhanvien());
            txtSDT.setText(user.getSdtnhanvien());
            txtEmail.setText(user.getEmail());
            txtNgaySinh.setText(user.getNgaysinh());
            txtDiaChi.setText(user.getDiachi());
//            txtGioiTinh.setText(user.getGioitinh());
            if(user.getGioitinh().equals("Nam"))
            {
                rdoNam.setChecked(true);
            }
            else
                rdoNu.setChecked(true);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thayDoiThongTin();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Đăng xuất thành công", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
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
    public void thayDoiThongTin(){
         sdt = txtSDT.getText().toString().trim();
         hoten = txtHoTen.getText().toString().trim();
         email = txtEmail.getText().toString().trim();
         diachi = txtDiaChi.getText().toString().trim();


        StringRequest request = new StringRequest(Request.Method.POST, url_update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!sdt.isEmpty() && !hoten.isEmpty() && !email.isEmpty() && !diachi.isEmpty()){
                    if(response.contains("1")){
                        Toast.makeText(getApplicationContext(), "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                        Toast.makeText(UserActivity.this, "Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Các thông tin phải được nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("sdtnhanvien", sdt);
                data.put("tennhanvien", hoten);
                data.put("email", email);
                data.put("diachi", diachi);
                data.put("manv", getID);
                return data;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}