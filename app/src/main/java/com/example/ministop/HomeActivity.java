package com.example.ministop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements OnClickListener {
    //navigation handle
    private int mSelectedId;
    Animation ani;
    ImageView icon;
    CardView cardView, cardView2, cardView3;
    private static final String SELECTED_ITEM_ID = "selected"; //nguoi dung da select item
    //private static final String FRIST_TIME = "fist_time"; // nguoi dung select lan dau
    private boolean mUserSawDrawer = false; //neu nguoi dung mo thi sau do khong hien thi lai

    String url = "http://" + DEPRESS.ip + ":81/KhoaLuanTotNghiep/android/laytrangthaiCV";
    NGUOIDUNG user;
    String getID;


    RecyclerView recyclerView, recyclerView2;
//    String url2 = "http://" + DEPRESS.ip + "/wsministop/getsanpham.php";

    ViewFlipper viewFlipper;
    DrawerLayout drawerLayout;


    //Toolbar toolbar;
    //NavigationView navigationLeft;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        //Action bar
        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        actionBar.setTitle("Trang chủ");

        //Ánh xạ
        viewFlipper = findViewById(R.id.viewflipper);
        //icon = findViewById(R.id.imageView_logo);
        cardView = findViewById(R.id.cvTTcanhan);
        cardView2 = findViewById(R.id.cvTimKien);
        cardView3 = findViewById(R.id.cvNhiemVu);
        //animation
//        ani = AnimationUtils.loadAnimation(this, R.anim.rotate_logo_home);
//        icon.startAnimation(ani);

        drawerLayout = findViewById(R.id.drawerlayout);

        //set navigation vao icon menu
//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
        loadViewFlipper();

        //Su kien click card view
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UserActivity.class);
                //intent.putExtra();
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, NhanVienActivity.class);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DEPRESS.USER != null)
                {
                    user = DEPRESS.USER;
                    getID = user.getManv();
                    LoadGiaoDien();
                }

            }
        });
    }

//    public void LayTrangThaiCV() {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i < response.length(); i++) {
//                    try {
//                        JSONObject jsonObject = response.getJSONObject(i);
//                        data.add(new Products(jsonObject.getString("idsanpham"), jsonObject.getString("tensanpham"), jsonObject.getString("gia"),jsonObject.getString("hinhanh"),jsonObject.getString("mota"), jsonObject.getString("iddanhmucsp")));
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                productsAdapter_recycle.notifyDataSetChanged();
//            }
//        };
//
//        Response.ErrorListener thatbai = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        };
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, thanhcong, thatbai);
//        requestQueue.add(jsonArrayRequest);
//    }


    void loadViewFlipper() {
        // Y: 192.168.22.102    //Ru: 192.168.1.5
        String urlslide = "http://" + DEPRESS.ip + ":81/kltn/slide/";
        ArrayList<String> mangslide = new ArrayList<>();

        mangslide.add(urlslide + "1.jpg");
        mangslide.add(urlslide + "2.jpg");
        mangslide.add(urlslide + "3.jpg");
        mangslide.add(urlslide + "4.jpg");
        mangslide.add(urlslide + "5.jpg");

        for (int i = 0; i < mangslide.size(); i++) {
            ImageView imageView = new ImageView(this);
            Picasso.with(this).load(mangslide.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(2000);
        viewFlipper.startFlipping();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, mSelectedId);
    }

    //nut tro ve navigation
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    /*private  void showDrawer()
    {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    private  void hideDrawer()
    {
        drawerLayout.closeDrawer(GravityCompat.START);
    }*/



    //---------------------Xu ly su kien click item--------------------//
    // -------------------------------------------------------------------------//

    @Override
    public void itemClick(NGUOIDUNG nguoidung) {
        Intent intent = new Intent(this,DetailActivity.class);
//        intent.putExtra("key1",products);
          DEPRESS.USER = nguoidung;
        startActivity(intent);
    }

    //---------------------------------------------------------------------------//

    //------------------------------Xu ly search view----------------------------//
    // -------------------------------------------------------------------------//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search,menu);
//        //getMenuInflater().inflate(R.menu.menu_cart,menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                productsAdapter_recycle.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                productsAdapter_recycle.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//        return true;
//    }


    //----------Button Cong Viec------------//
    public void LoadGiaoDien(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("1")){
                    Intent intent = new Intent(HomeActivity.this, TaskJobActivity.class);
                    startActivity(intent);
                }
                else if(response.contains("0")){
                    Intent intent2 = new Intent(HomeActivity.this, HasTaskActivity.class);
                    startActivity(intent2);
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