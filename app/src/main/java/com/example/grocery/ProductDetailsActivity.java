package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.grocery.MainActivity.showCart;

public class ProductDetailsActivity extends AppCompatActivity {
 private ViewPager productImagesViewPager;
 private TabLayout  viewpagerIndicator;
 private  ViewPager productDetailsViewpager;
 private TabLayout productDetailsTablayout;
 private Button coupenRedeemBtn;


 ///////////coupenDialog
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static  TextView coupenBody;
    private static RecyclerView coupensRecyclerView;
    private static LinearLayout selectedCoupen;
    //////////coupen Dialog

 ///////// rating layout
    LinearLayout rateNowContainer;
 /////////  rating layout

    private Button  buyNowbtn;

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
          buyNowbtn = findViewById(R.id.buy_now_btn);
       productDetailsViewpager = findViewById(R.id.product_details_viewpager);
       productDetailsTablayout = findViewById(R.id.product_details_tabLayout);
        coupenRedeemBtn = findViewById(R.id.coupen_redemption_btn);

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

        productDetailsViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTablayout.getTabCount()));
        productDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productDetailsViewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        /////////rating layout
         rateNowContainer = findViewById(R.id.rate_now_container);
          for (int x =0;x<rateNowContainer.getChildCount();x++){
              final int starPosition = x;
              rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      setRating(starPosition);
                  }
              });
          }



        ///////////rating layout

        buyNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
              startActivity(deliveryIntent);
            }
        });


          ////////////////coupen dialog
        final Dialog checkCoupenPriceDalog = new Dialog(ProductDetailsActivity.this);
        checkCoupenPriceDalog.setContentView(R.layout.coupen_redeem_dialog);
        checkCoupenPriceDalog.setCancelable(true);
        checkCoupenPriceDalog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        ImageView toggleRecyclerView = checkCoupenPriceDalog.findViewById(R.id.toogle_recyclerView);
        coupensRecyclerView = checkCoupenPriceDalog.findViewById(R.id.coupen_recyclerView);
        selectedCoupen = checkCoupenPriceDalog.findViewById(R.id.selected_coupen);
        coupenTitle = checkCoupenPriceDalog.findViewById(R.id.coupen_title);
        coupenExpiryDate = checkCoupenPriceDalog.findViewById(R.id.coupen_validity);
        coupenBody = checkCoupenPriceDalog.findViewById(R.id.coupen_body);

        TextView originalPrice = checkCoupenPriceDalog.findViewById(R.id.original_price);
        TextView discountedPrice = checkCoupenPriceDalog.findViewById(R.id.discounted_price);

        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coupensRecyclerView.setLayoutManager(layoutManager);
        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("cashback","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("cashback","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("Buy one Get 1 free","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("cashback","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("Discount","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("cashback","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList,true);
        coupensRecyclerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();


        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRecyclerView();
            }
        });


//////////////////coupen dialog

          coupenRedeemBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                   checkCoupenPriceDalog.show();
              }
          });


    }

    public static  void  showDialogRecyclerView(){
        if (coupensRecyclerView.getVisibility() == View.GONE)
        {
            coupensRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);
        }else {
            coupensRecyclerView.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);
        }

    }

    private void setRating(int starPosition) {
        for (int x =0;x<rateNowContainer.getChildCount();x++){
            ImageView starBtn = (ImageView)rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x<=starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
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

            Intent cartIntent= new Intent(ProductDetailsActivity.this,MainActivity.class);
            showCart= true;
            startActivity(cartIntent);
            return true;

        }else if (id == R.id.main_search_icon){
            return true;

        }else if(id == android.R.id.home){
           finish();
        }

        return super.onOptionsItemSelected(item);
    }
}