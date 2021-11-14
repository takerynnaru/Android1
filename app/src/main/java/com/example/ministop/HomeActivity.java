package com.example.ministop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnClickListener {
    //navigation handle
    private int mSelectedId;
    Animation ani;
    ImageView icon;
    private static final String SELECTED_ITEM_ID = "selected"; //nguoi dung da select item
    //private static final String FRIST_TIME = "fist_time"; // nguoi dung select lan dau
    private boolean mUserSawDrawer = false; //neu nguoi dung mo thi sau do khong hien thi lai



    RecyclerView recyclerView, recyclerView2;

    ArrayList<Options> dulieu = new ArrayList<>();
    OptionsAdapter_Recycle optionsAdapter_recycle;

    ArrayList<Products> data = new ArrayList<>();
    ProductsAdapter_Recycle productsAdapter_recycle;

    //Y: 192.168.22.102     //Ru: 192.168.1.7
    String url = "http://" + DEPRESS.ip +"/wsministop/getdanhmuc.php";
    String url2 = "http://" + DEPRESS.ip + "/wsministop/getsanpham.php";

    ViewFlipper viewFlipper;
    DrawerLayout drawerLayout;

    Toolbar toolbar;
    NavigationView navigationLeft;

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
        recyclerView = findViewById(R.id.recycleView_option);
        recyclerView2 = findViewById(R.id.recycleView_product);
        viewFlipper = findViewById(R.id.viewflipper);
        icon = findViewById(R.id.imageView_logo);

        //animation
        ani = AnimationUtils.loadAnimation(this, R.anim.rotate_logo_home);
        icon.startAnimation(ani);

        drawerLayout = findViewById(R.id.drawerlayout);
        //set navigation vao icon menu
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationLeft = findViewById(R.id.nagivationviewLeft);
        //Xu ly Navigation Left
        //tao su kien click navigationLeft
        navigationLeft.setNavigationItemSelectedListener(this);
        //Xu ly khong lap Activity khi chon item Navigation
        //showDrawer();
//        if(!didUserSeeDrawer()) //first time
//        {
//            showDrawer();
//            markDrawerSeen();//khong duoc hien thi vi nguoi dung da chon
//        }
//        else
//        {
//            hideDrawer();
//        }
        //Luu su kien tu acvivity da chon
        mSelectedId = savedInstanceState == null ? R.id.nagivationviewLeft : savedInstanceState.getInt(SELECTED_ITEM_ID);
        navigation(mSelectedId);

        //Load du lieu danh muc sp
        optionsAdapter_recycle = new OptionsAdapter_Recycle(this, dulieu, this);
        recyclerView.setAdapter(optionsAdapter_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        LayDanhMucSP();
        loadViewFlipper();
        //Load du lieu list san pham
        productsAdapter_recycle = new ProductsAdapter_Recycle(this, data,this);
        recyclerView2.setAdapter(productsAdapter_recycle);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        LaySanPham();

    }

    public void LaySanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        data.add(new Products(jsonObject.getString("idsanpham"), jsonObject.getString("tensanpham"), jsonObject.getString("gia"),jsonObject.getString("hinhanh"),jsonObject.getString("mota"), jsonObject.getString("iddanhmucsp")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                productsAdapter_recycle.notifyDataSetChanged();
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url2, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
    }


    void loadViewFlipper() {
        // Y: 192.168.22.102    //Ru: 192.168.1.5
        String urlslide = "http://" + DEPRESS.ip + "/wsministop/slide/";
        ArrayList<String> mangslide = new ArrayList<>();

        mangslide.add(urlslide + "1.jpg");
        mangslide.add(urlslide + "2.jpg");
        mangslide.add(urlslide + "3.jpg");
        mangslide.add(urlslide + "4.jpg");
        mangslide.add(urlslide + "5.jpg");
        mangslide.add(urlslide + "6.jpg");
        mangslide.add(urlslide + "7.jpg");
        mangslide.add(urlslide + "8.jpg");
        mangslide.add(urlslide + "9.jpg");
        mangslide.add(urlslide + "10.jpg");

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


    public void LayDanhMucSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        dulieu.add(new Options(jsonObject.getString("iddanhmucsp"), jsonObject.getString("tendanhmucsp"), jsonObject.getString("hinhanh")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                optionsAdapter_recycle.notifyDataSetChanged();
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

    //dieu huong navigation
    private void navigation(int mSelectedId) {
        Intent intent = null;
        if (mSelectedId == R.id.mnu_user) {
            intent = new Intent(HomeActivity.this, UserActivity.class);
            startActivity(intent);

            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (mSelectedId == R.id.mnu_cart) {
            intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);

            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (mSelectedId == R.id.mnu_address) {
            intent = new Intent(HomeActivity.this, LocationActivity.class);
            startActivity(intent);

            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(mSelectedId == R.id.mnu_contact)
        {
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:028 3510 6870"));
            startActivity(intent);
        }
        if(mSelectedId == R.id.mnu_help)
        {
            intent = new Intent(this,SupportChatActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        //Intent intent = null;
        menuItem.setChecked(true);
        mSelectedId = menuItem.getItemId();
        navigation(mSelectedId);
        return true;
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



    //---------------------Xu ly su kien click item san pham--------------------//
    // -------------------------------------------------------------------------//

    @Override
    public void itemClick(Products products) {
        Intent intent = new Intent(this,DetailActivity.class);
//        intent.putExtra("key1",products);
          DEPRESS.PRODUCT = products;
        startActivity(intent);
    }


    //---------------------------------------------------------------------------//
    //---------------------Xu ly su kien click item danh muc sp--------------------//
    @Override
    public void ItemClick(Options options) {
        Intent intent = new Intent(this, SPTheoDanhMucActivity.class);
        DEPRESS.OPTIONdata = options; //null
        startActivity(intent);
    }

    //---------------------------------------------------------------------------//

    //------------------------------Xu ly search view----------------------------//
    // -------------------------------------------------------------------------//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        getMenuInflater().inflate(R.menu.menu_cart,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productsAdapter_recycle.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                productsAdapter_recycle.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_cart)
        {
            Intent intent = new Intent(this,CartActivity.class);
            startActivity(intent);
        }

        return true;
    }
    // -------------------------------------------------------------------------//

//    private boolean didUserSeeDrawer()
//    {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mUserSawDrawer = sharedPreferences.getBoolean(FRIST_TIME, false);
//        return mUserSawDrawer;
//    }
//    private void markDrawerSeen()
//    {
//        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mUserSawDrawer = true;
//        sharedPreferences.edit().putBoolean(FRIST_TIME, mUserSawDrawer).apply();
//    }

}