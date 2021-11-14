package com.example.ministop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class SPTheoDanhMucActivity extends AppCompatActivity implements OnClickListener{
    ArrayList<Products> sp = new ArrayList<>();
    //ArrayList<Products> mangcacsp = new ArrayList<>();
    RecyclerView rcl_sptheoloai;
    RecyclerView.Adapter adaptersp;
    TextView tvNull;
    //lay id tu su kien Item click
    String id = DEPRESS.OPTIONdata.getMa();
    //String id = "1";
    String url = "http://" + DEPRESS.ip + "/wsministop/get1sanpham.php?id=" + id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sptheo_danh_muc);
        //Action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //doi mau thanh action bar
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#003894"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Sản phẩm theo loại");

        //anh xa
        rcl_sptheoloai = findViewById(R.id.rcl_SPtheoloai);
        tvNull = findViewById(R.id.tv_SPDM_Notification);

        //set adapter
        adaptersp = new SPDMAdapter(SPTheoDanhMucActivity.this, sp,this);
        rcl_sptheoloai.setAdapter(adaptersp);
        rcl_sptheoloai.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        xuLyDataTrongRCL();
        checkDataInRecycle();


    }

    public void xuLyDataTrongRCL()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        sp.add(new Products(jsonObject.getString("idsanpham"), jsonObject.getString("tensanpham"), jsonObject.getString("gia"),jsonObject.getString("hinhanh"),jsonObject.getString("mota"), jsonObject.getString("iddanhmucsp")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //xu ly
//                    for (int x = 0; x < mangcacsp.size(); x++)
//                    {
//
//                        if(DEPRESS.OPTIONdata.getMa().equals(mangcacsp.get(x).getMadm()))
//                        {
//                            sp.add(new Products(mangcacsp.get(x).getMa(),mangcacsp.get(x).getTen(), mangcacsp.get(x).getGia(), mangcacsp.get(x).getHinh(),mangcacsp.get(x).getMota(),mangcacsp.get(x).getMadm()));
//                            adaptersp.notifyDataSetChanged();
//                        }
//                }
                }
                adaptersp.notifyDataSetChanged();
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





    private void checkDataInRecycle()
    {
        if (sp.size() <0)
        {
            adaptersp.notifyDataSetChanged();
            tvNull.setVisibility(View.VISIBLE);
            rcl_sptheoloai.setVisibility(View.INVISIBLE);
        }
        else
        {
            adaptersp.notifyDataSetChanged();
            tvNull.setVisibility(View.INVISIBLE);
            rcl_sptheoloai.setVisibility(View.VISIBLE);
        }

    }


    @Override
    public void itemClick(Products products) {
        Intent intent = new Intent(this,DetailActivity.class);
//        intent.putExtra("key1",products);
        DEPRESS.PRODUCT = products;
        startActivity(intent);
    }

    @Override
    public void ItemClick(Options options) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_cart:
                Intent intent = new Intent(this,CartActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return true;

    }
}