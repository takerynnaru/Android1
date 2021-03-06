package com.example.ministop;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    Button btnLogin, btnDangky;
    ImageView imgLogo;
    CheckBox chkSave;
    Animation animation;
    EditText txtusername, txtpassword;
    SharedPreferences luutru;
    //NGUOIDUNG user;
    ArrayList<NGUOIDUNG> user_array = new ArrayList<>();
    String username, password;

    String url = "http://" + DEPRESS.ip + "/KhoaLuanTotNghiep/android/dangnhap";
    String url_user = "http://" + DEPRESS.ip + "/KhoaLuanTotNghiep/android/xemnhanvienkythuat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //anh xa
        //username = password = "";
        txtusername = findViewById(R.id.txtusername);
        txtpassword = findViewById(R.id.txtpassword);
        chkSave = findViewById(R.id.chkSave);
        btnLogin = findViewById(R.id.btnLogin);
        imgLogo = findViewById(R.id.imageView);

        //tao anmiation cho logo
        animation = AnimationUtils.loadAnimation(this, R.anim.combine_logo);
        imgLogo.startAnimation(animation);


        LayUser();
        luutru = getSharedPreferences("data", MODE_PRIVATE);
        //nap du lieu
        if (luutru.getBoolean("saveinfo", false) == true) {
            //load du lieu len editText
            txtusername.setText(luutru.getString("username", ""));
            txtpassword.setText(luutru.getString("password", ""));
            chkSave.setChecked(true);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtusername.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui l??ng nh???p t??n ????ng nh???p", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (txtpassword.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Vui l??ng nh???p m???t kh???u", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    //Duyet mang
                    for (NGUOIDUNG i : user_array) {
                        if(txtusername.getText().toString().equals(i.tendangnhap))
                        {
                            LaydulieuDangNhap();
                            DEPRESS.USER = i;
                        }
                    }

                }
            }
        });
    }

    public void LaydulieuDangNhap() {
        username = txtusername.getText().toString().trim();
        password = txtpassword.getText().toString().trim();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.contains("1")) {
                    Toast.makeText(getApplicationContext(), "????ng nh???p th??nh c??ng!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Sai th??ng tin ????ng nh???p, vui l??ng ki???m tra l???i!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("tendangnhap", username);
                data.put("matkhau_nhap", password);
                return data;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    //Lay du lieu user
    public void LayUser() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        user_array.add(new NGUOIDUNG(jsonObject.getString("manv"), jsonObject.getString("tennhanvien"), jsonObject.getString("sdtnhanvien"), jsonObject.getString("gioitinh"), jsonObject.getString("ngaysinh"), jsonObject.getString("email"), jsonObject.getString("diachi"), jsonObject.getString("macv"), jsonObject.getString("tendangnhap"), jsonObject.getString("matkhau"), jsonObject.getString("trangthai"), jsonObject.getString("motacongviec"), jsonObject.getString("hinhanh")));
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_user, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
    }


    public void HandleLogin(View view) {
        int id = view.getId();
        switch (id) {
//            case R.id.btnRegister:
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//                break;
            case R.id.btnfb:
                FbClick();
                break;
            case R.id.btngg:
                GgClick();
                break;
//            case R.id.btnLogin:
//                Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent1);
//                break;

        }
    }


    //Fuction
    private void GgClick() {
        String text = "https://accounts.google.com/signin/v2/identifier?hl=vi&passive=true&continue=https%3A%2F%2Fwww.google.com%2F&ec=GAZAAQ&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
        Uri uri = Uri.parse(text);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void FbClick() {
        String text = "https://www.facebook.com/";
        Uri uri = Uri.parse(text);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}