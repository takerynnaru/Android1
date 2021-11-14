package com.example.ministop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class CartActivity extends AppCompatActivity {

    Button btnPay, btnContinue;
    ListView lvCart;
    ArrayList<Products> datasp = new ArrayList<>();
    TextView  tvNull;
    CartAdapterListView cartAdapter;
    public static TextView tvThanhtien;
    OnDeleteCart onDeleteCart;

    String url = "http://" + DEPRESS.ip + "/wsministop/getsanpham.php";

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Action Bar
        ActionBar actionBar = getSupportActionBar();
        //thanh tro ve home
        actionBar.setDisplayHomeAsUpEnabled(true);
        //doi mau thanh action bar
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#003894"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Giỏ hàng"); //Thiết lập tiêu đề


        //anh xa
        btnPay = findViewById(R.id.btn_Cart_Pay);
        btnContinue = findViewById(R.id.btn_Cart_Continue);
//        btnAdd = findViewById(R.id.btn_Cart_AddNumber);
//        btnMin = findViewById(R.id.btn_Cart_MinNumber);
        tvNull = findViewById(R.id.lbl_Cart_notificationcart);
        lvCart = findViewById(R.id.lst_Cart);
        tvThanhtien = findViewById(R.id.tv_Cart_Total);

        onDeleteCart = new OnDeleteCart() {
            @Override
            public void onDelete(CART c) {
                DEPRESS.carts.remove(c);
                cartAdapter = new CartAdapterListView(CartActivity.this, DEPRESS.carts, onDeleteCart );
                lvCart.setAdapter(cartAdapter);
                checkData();

            }
        };
        cartAdapter = new CartAdapterListView(CartActivity.this, DEPRESS.carts, onDeleteCart );
        lvCart.setAdapter(cartAdapter);



        //Kiem tra ListView
        checkData();

        //Load thanh tien
        xuLyThanhTien();




        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent1);
                // :)))

            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvThanhtien.getText().toString().equals(0+"  VND"))
                {
                    btnPay.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Giỏ hàng bạn chưa có gì để thanh toán", Toast.LENGTH_SHORT).show();
                }

                else
                {

                    Intent intent1 = new Intent(getApplicationContext(),OrderActivity.class);
                    startActivity(intent1);
                }
            }
        });


//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });






    }


//    private void catchOnItemListView() {
//        lvCart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
//                builder.setTitle("Thông báo");
//                builder.setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng");
//                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(DEPRESS.carts.size()<=0)
//                        {
//                            tvNull.setVisibility(View.VISIBLE);
//                        }
//                        else
//                        {
//                            DEPRESS.carts.remove(position);
//                            cartAdapter.notifyDataSetChanged();
//                            xuLyThanhTien();
//                            if(DEPRESS.carts.size()<=0)
//                            {
//                                tvNull.setVisibility(View.VISIBLE);
//                                cartAdapter.notifyDataSetChanged();
//                                xuLyThanhTien();
//                            }
//                        }
//                    }
//                });
//                builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        cartAdapter.notifyDataSetChanged();
//                        xuLyThanhTien();
//                    }
//                });
//                builder.show();
//                return true;
//            }
//        });
//    }

    private void checkData() {
        if(DEPRESS.carts.size() <=0)
        {
            cartAdapter.notifyDataSetChanged();
            tvNull.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.INVISIBLE);

        }
        else
        {
            cartAdapter.notifyDataSetChanged();
            tvNull.setVisibility(View.INVISIBLE);
            lvCart.setVisibility(View.VISIBLE);


        }
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

    public void xuLyThanhTien() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        datasp.add(new Products(jsonObject.getString("idsanpham"), jsonObject.getString("tensanpham"), jsonObject.getString("gia"),jsonObject.getString("hinhanh"),jsonObject.getString("mota"), jsonObject.getString("iddanhmucsp")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //Xu ly text view Total
                // t co xai cai nay dau ma biet huhu
                //th de doc debug
                if(DEPRESS.carts.size() <=0)
                {
                    tvThanhtien.setText(0+"  VND");
                }
                else
                {
                    int Tongtien = 0;
                    for (CART i : DEPRESS.carts)
                    {
                        for (Products sp : datasp)
                        {
                            if(i.idsp.equals(sp.ma))
                            {
                                int giasp = Integer.parseInt(sp.gia);
                                int soluongsp = i.soluong;
                                Tongtien += giasp * soluongsp;
                                tvThanhtien.setText(Tongtien + "  VND");
                                DEPRESS.ThanhToan = Tongtien;
                                // hai vong for thi nho break
                                // ok chua
                                break;

                            }
                        }

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

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
    }

//    public void handleAddCart_1dong(View view)
//    {
//        int Tongtien = 0;
//        TextView tvgiasp, tvsl1dong;
//        // anh xa
//        tvgiasp = findViewById(R.id.tv_Cart_giasp);
//        tvsl1dong = findViewById(R.id.txt_Cart_soluong_1dong);
//
//        int giasp = Integer.parseInt(tvgiasp.getText().toString());
//        int sl = Integer.parseInt(tvsl1dong.getText().toString()) + 1;
//        Tongtien = giasp * sl;
//        tvThanhtien.setText(Tongtien+"  VND");
//        tvsl1dong.setText(sl+"");
//    }
//    public void handleMinCart_1dong(View view)
//    {
//        int Tongtien = 0;
//        TextView tvgiasp, tvsl1dong;
//        // anh xa
//        tvgiasp = findViewById(R.id.tv_Cart_giasp);
//        tvsl1dong = findViewById(R.id.txt_Cart_soluong_1dong);
//
//        int giasp = Integer.parseInt(tvgiasp.getText().toString());
//        int sl = Integer.parseInt(tvsl1dong.getText().toString()) - 1;
//        if(sl <0)
//        {
//            sl = 0;
//            tvsl1dong.setText(sl+"");
//        }
//        else
//        {
//            Tongtien = giasp * sl;
//            tvThanhtien.setText(Tongtien+"  VND");
//            tvsl1dong.setText(sl+"");
//
//        }
//
//    }


//    private void xuLyThanhTien()
//    {
//        if(DEPRESS.carts.size() <=0)
//        {
//            tvThanhtien.setText(0+"");
//        }
//        else
//        {
//            int Tongtien = 0;
//            for (CART i : DEPRESS.carts)
//            {
//                for (Products sp : datasp)
//                {
//                    if(i.idsp.equals(sp.ma))
//                    {
//                        int giasp = Integer.parseInt(sp.gia);
//                        int soluongsp = i.soluong;
//                        Tongtien += giasp * soluongsp;
//                        tvThanhtien.setText(Tongtien + "");
//                    }
//                }
//
//            }
//        }

    }







