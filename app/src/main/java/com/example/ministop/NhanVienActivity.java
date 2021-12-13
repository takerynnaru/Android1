package com.example.ministop;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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

public class NhanVienActivity extends AppCompatActivity implements OnClickListener{
    RecyclerView recyclerView;
    ArrayList<NGUOIDUNG> data = new ArrayList<>();
    StaffAdapter_RecycleView staffAdapter_recycleView;

    String url = "http://" + DEPRESS.ip +":81/KhoaLuanTotNghiep/android/xemnhanvien";

    SearchView searchView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthi_nhanvien);

        //Hide action bar
        ActionBar actionBar = getSupportActionBar();
        //thanh tro ve home
        actionBar.setDisplayHomeAsUpEnabled(true);
        //doi mau thanh action bar
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#003894"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Nhân Viên"); //Thiết lập tiêu đề

        recyclerView = findViewById(R.id.rv_user);


        staffAdapter_recycleView = new StaffAdapter_RecycleView(NhanVienActivity.this, data, this);
        recyclerView.setAdapter(staffAdapter_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        LayNhanVien();
    }

    public void LayNhanVien() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        data.add(new NGUOIDUNG(jsonObject.getString("manv"), jsonObject.getString("tennhanvien"), jsonObject.getString("sdtnhanvien"), jsonObject.getString("gioitinh"), jsonObject.getString("ngaysinh"), jsonObject.getString("email"), jsonObject.getString("diachi"), jsonObject.getString("macv"), jsonObject.getString("tendangnhap"), jsonObject.getString("matkhau"), jsonObject.getString("trangthai"), jsonObject.getString("motacongviec"), jsonObject.getString("hinhanh")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                staffAdapter_recycleView.notifyDataSetChanged();
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

//Xu ly su kien click item cua Recycle view de xem thong tin chi tiet cua mot nhan vien
    public void itemClick(NGUOIDUNG nguoidung) {
        Intent intent = new Intent(this,DetailActivity.class);
//        intent.putExtra("key1",products);
        DEPRESS.USER = nguoidung ;
        startActivity(intent);
    }
//Xu ly search view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                staffAdapter_recycleView.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                staffAdapter_recycleView.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}


