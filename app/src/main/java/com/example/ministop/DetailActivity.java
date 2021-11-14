package com.example.ministop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView txtTen, txtGia, txtMoTa;
    Button btnAddCart;
    //Products products;


    String url = "http://" + DEPRESS.ip + "/wsministop/sanpham/";
    private MenuItem item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chitietsanpham);

        //Hide action bar
        ActionBar actionBar = getSupportActionBar();
        //thanh tro ve home
        actionBar.setDisplayHomeAsUpEnabled(true);
        //doi mau thanh action bar
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#003894"));
        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle("Chi tiết sản phẩm"); //Thiết lập tiêu đề
        //String title = actionBar.getTitle().toString();

        //Anh xa
        imageView = findViewById(R.id.img_CTSP);
        txtTen = findViewById(R.id.tv_CTSP1);
        txtGia = findViewById(R.id.tv_CTSP2);
        txtMoTa = findViewById(R.id.tv_CTSP3);
        btnAddCart = findViewById(R.id.btn_addCart);

        //Gui du lieu tu home qua
        Intent intent = getIntent();
        if(DEPRESS.PRODUCT != null)
        {
//            products = (Products) intent.getSerializableExtra("key1");

            //load hình từ url
            Picasso.with(this).load(url + DEPRESS.PRODUCT.getHinh()).placeholder(R.drawable.no_image_found).into(imageView);
            //Set lại id để load dữ liệu từ HomeActivity qua
            txtTen.setText(DEPRESS.PRODUCT.getTen());
            txtGia.setText(formatNumberCurrency(DEPRESS.PRODUCT.getGia()));
            txtMoTa.setText(DEPRESS.PRODUCT.getMota());
        }

        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cart ton tai phan tu
                if(DEPRESS.carts.size() > 0)//Chưa khai báo r
                {
                    boolean exist = false;
                   for(int i =0; i < DEPRESS.carts.size(); i++)
                   {
                       if(DEPRESS.carts.get(i).getIdsp().equals(DEPRESS.PRODUCT.getMa()))//OK so sánh String phải dùng equal
                       {
                           DEPRESS.carts.get(i).setSoluong(DEPRESS.carts.get(i).getSoluong()+1);
                            exist =true;
                       }
                   }
                   if(exist == false)
                   {
                       DEPRESS.carts.add(new CART(DEPRESS.PRODUCT.getMa(),DEPRESS.PRODUCT.getHinh(), DEPRESS.PRODUCT.getTen(), DEPRESS.PRODUCT.getGia(), 1));
                   }
                }
                //Cart null
                else
                {
                    DEPRESS.carts.add(new CART(DEPRESS.PRODUCT.getMa(),DEPRESS.PRODUCT.getHinh(), DEPRESS.PRODUCT.getTen(), DEPRESS.PRODUCT.getGia(), 1));

                }
                Intent intent1 = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent1);
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
            case R.id.action_cart:
                Intent intent = new Intent(this,CartActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Tạo format tiền VND
    public static String formatNumberCurrency(String gia)
    {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(Double.parseDouble(gia));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
            return true;

    }



}
