package com.example.ministop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btnSaveRegist, btnCancel;
    EditText txtphone, txtpassword, txtrepass, txtemail, txtname, txtaddress, txtbirthday, txtgender;
    RequestQueue requestQueue;
    ArrayList<NGUOIDUNG> user = new ArrayList<>();
    ArrayList<NGUOIDUNG> dangky = new ArrayList<>();
    String url = "http://" + DEPRESS.ip + "/wsministop/register.php";
    String urlNguoiDung = "http://" + DEPRESS.ip + "/wsministop/getnguoidung.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
//        ColorDrawable colorDrawable
//                = new ColorDrawable(Color.parseColor("#fdcc32"));
//
//        // Set BackgroundDrawable
//        actionBar.setBackgroundDrawable(colorDrawable);
//        actionBar.setTitle(""); //Thiết lập tiêu đề
//        String title = actionBar.getTitle().toString();

        //anh xa
        //button
        btnSaveRegist = findViewById(R.id.btnSaveRegister);
        btnCancel = findViewById(R.id.btnCancel);
        //edit text
        txtphone = findViewById(R.id.txt_phone);
        txtpassword = findViewById(R.id.txt_password);
        txtrepass = findViewById(R.id.txt_repass);
        txtemail = findViewById(R.id.txt_email);
        txtname = findViewById(R.id.txt_nameuser);
        txtaddress = findViewById(R.id.txt_address);
        txtbirthday = findViewById(R.id.txt_ngaysinh);
        txtgender = findViewById(R.id.txt_gioitinh);
        requestQueue = Volley.newRequestQueue(this);

        LaydulieuNguoiDung();

//        kiemTraDuLieuTrung();


        btnSaveRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (txtphone.getText().toString().equals("") || txtpassword.getText().toString().equals("") || txtrepass.getText().toString().equals("") || txtemail.getText().toString().equals("") || txtname.getText().toString().equals("") || txtaddress.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
                    return;
                }
//                for (NGUOIDUNG i : user) {
//                    if(txtphone.getText().toString().equals(i.sdt))
//                    {
//                        Toast.makeText(getApplicationContext(), "Số điện thoại đã được đăng ký", Toast.LENGTH_LONG).show();
//                        txtphone.setText("");
//                        break;
//                    }
//                }
                if(kiemTraDuLieuTrung(txtphone.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(), "Số điện thoại đã được đăng ký", Toast.LENGTH_LONG).show();
                        txtphone.setText("");
                        return;
                }
                if (!txtrepass.getText().toString().equals(txtpassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không khớp", Toast.LENGTH_LONG).show();
                    return;
                }
                else {

                    volleyPost();
                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent1);
                }


//                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
//                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent1);
            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent2);
            }
        });


    }

    private boolean kiemTraDuLieuTrung(String a) {
        for (NGUOIDUNG i : user) {

            if (a.equals(i.sdt))
            {
                return true; //co trung
            }
            else
                return  false; //khong trung
        }
        return false;
    }


//    public void HandleRegister(View view)
//    {
//        int id = view.getId();
//        switch (id)
//        {
////            case  R.id.btnSaveRegister:
////
////                else
////                {
////                    Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show();
////                    Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
////                    startActivity(intent1);
////                    break;
////                }
//
//            case  R.id.btnCancel:
//                Intent intent2 = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent2);
//                break;
//        }
//
//    }

    public void volleyPost() {

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);

        JSONObject postData = new JSONObject();
        try {

            String sdt = txtphone.getText().toString();
            String matkhau = txtpassword.getText().toString();
            String email = txtemail.getText().toString();
            String hoten = txtname.getText().toString();
            String diachi = txtaddress.getText().toString();
//            String ngaysinh = txtbirthday.getText().toString();
            String gioitinh = txtgender.getText().toString();
//            postData.put("IDNguoiDung", "");
            postData.put("SDT", sdt);
            postData.put("HoTen", hoten);
            postData.put("MatKhau", matkhau);
            postData.put("Email", email);
//            postData.put("NgaySinh", ngaysinh);
            postData.put("GioiTinh", gioitinh);
            postData.put("DiaChi", diachi);
//            postData.put("HinhAnh", "");
//            dangky.add(new NGUOIDUNG(postData.getString("idnguoidung"), postData.getString("sdt"), postData.getString("hoten"), postData.getString("matkhau"), postData.getString("email"), postData.getString("ngaysinh"), postData.getString("gioitinh"), postData.getString("diachi"), postData.getString("hinhanh")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }


    public void LaydulieuNguoiDung() {
        //Lay mang user
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        user.add(new NGUOIDUNG(jsonObject.getString("idnguoidung"), jsonObject.getString("sdt"), jsonObject.getString("hoten"), jsonObject.getString("matkhau"), jsonObject.getString("email"), jsonObject.getString("ngaysinh"), jsonObject.getString("gioitinh"), jsonObject.getString("diachi"), jsonObject.getString("hinhanh")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlNguoiDung, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
    }


}