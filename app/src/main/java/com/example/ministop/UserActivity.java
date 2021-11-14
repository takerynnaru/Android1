package com.example.ministop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    Button btnSave, btnLogout;
    NGUOIDUNG user;
    ImageView avatarUser;
    TextView tvNameAva, txtHoTen, txtSDT, txtEmail, txtNgaySinh , txtDiaChi, txtGioiTinh;
    RadioButton rdoNam, rdoNu;



    String url = "http://" + DEPRESS.ip + "/wsministop/nguoidung/";

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
        actionBar.setTitle("Thông tin người dùng"); //Thiết lập tiêu đề
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

            Picasso.with(this).load(url + user.getHinhanh()).placeholder(R.drawable.no_image_found).into(avatarUser);

            tvNameAva.setText(user.getHoten());
            txtHoTen.setText(user.getHoten());
            txtSDT.setText(user.getSdt());
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
                Toast.makeText(getApplicationContext(), "Lưu thông tin thành công", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DEPRESS.carts = new ArrayList<>();
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








}