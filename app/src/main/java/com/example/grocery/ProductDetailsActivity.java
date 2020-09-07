package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
 private ViewPager productImagesViewPager;
 private TabLayout  viewpagerIndicator;
 private boolean ALREADY_ADDED_TO_WISHLIST = false;
 private FloatingActionButton addToWishListBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productImagesViewPager = findViewById(R.id.product_images_viewPager);
        viewpagerIndicator  =findViewById(R.id.viewpager_indicator);
       addToWishListBtn= findViewById(R.id.add_to_wishlist);
        List<Integer> productImages = new ArrayList<>();
        productImages.add(R.drawable.banner);
        productImages.add(R.drawable.banner1);
        productImages.add(R.drawable.banner);
        productImages.add(R.drawable.banner1);
        productImages.add(R.drawable.banner);

        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
        productImagesViewPager.setAdapter(productImagesAdapter);
        viewpagerIndicator.setupWithViewPager(productImagesViewPager,true);

        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ALREADY_ADDED_TO_WISHLIST){
                    ALREADY_ADDED_TO_WISHLIST=false;
                    addToWishListBtn.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff00")));

                }else {
                    ALREADY_ADDED_TO_WISHLIST =true;
                    addToWishListBtn.setSupportBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.main_cart_icon){
            Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_SHORT).show();
            return true;

        }else if (id == R.id.main_search_icon){
            return true;

        }else if(id == android.R.id.home){
           finish();
        }

        return super.onOptionsItemSelected(item);
    }
}