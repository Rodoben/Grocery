package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.grocery.MainActivity.showCart;
import static com.example.grocery.RegisterActivity.setSignUpFragment;


public class ProductDetailsActivity extends AppCompatActivity {
 private ViewPager productImagesViewPager;
 private TabLayout  viewpagerIndicator;
 private  ViewPager productDetailsViewpager;
 private TabLayout productDetailsTablayout;
 private LinearLayout coupenRedemptionlayout;
 private Button coupenRedeemBtn;

private TextView productOnlyDescriptionBody;
 private TextView rewardTitle;
 private TextView rewardBody;

 private TextView productTitle;
 private TextView averageRatingMiniView;
 private TextView totalRatingMiniView;
 private TextView productPrice;
 private  TextView cuttedPrice;
 private ImageView codIndicator;
 private TextView tvcodIndicator;
//////////product description

private ConstraintLayout productDetailsOnlyContainer;
private  ConstraintLayout productDetailsTabContainer;

//////////product description

 ///////////coupenDialog
    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static  TextView coupenBody;
    private static RecyclerView coupensRecyclerView;
    private static LinearLayout selectedCoupen;
    //////////coupen Dialog

    private  Dialog signInDialog;

    private Button  buyNowbtn;
    private LinearLayout addToCartBtn;

 ///////// rating layout
   private LinearLayout rateNowContainer;
private TextView totalRatingsFigure;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private LinearLayout ratingsProgressBarContainer;
    private TextView averageRating;
 /////////  rating layout

   private String productOtherDetails;
   private   String productDescription;
   //public static  int tabPosition =-1;

    //ProductSpecificationFragment.productSpecificationModelList = new ArrayList<>();
   private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();


 private boolean ALREADY_ADDED_TO_WISHLIST = false;
 private FloatingActionButton addToWishListBtn;

 private FirebaseFirestore firebaseFirestore;
 private FirebaseUser currentUser;


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
         productTitle = findViewById(R.id.product_title);
         averageRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
         totalRatingMiniView = findViewById(R.id.total_ratings_miniview);
           productPrice = findViewById(R.id.product_price);
           cuttedPrice = findViewById(R.id.cutted_price);
           tvcodIndicator = findViewById(R.id.tc_cod_indicator);
           codIndicator = findViewById(R.id.cod_indicator_imageView);
               rewardTitle = findViewById(R.id.reward_title);
               rewardBody = findViewById(R.id.reward_body);
                productDetailsTabContainer = findViewById(R.id.product_details_tabs_container);
               productDetailsOnlyContainer = findViewById(R.id.product_details_container);
              productOnlyDescriptionBody = findViewById(R.id.product_details_body);
             totalRatings = findViewById(R.id.total_ratings);
         ratingsNoContainer = findViewById(R.id.ratings_numbers_container);
   totalRatingsFigure = findViewById(R.id.total_ratings_figure);
   ratingsProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
   averageRating = findViewById(R.id.average_rating);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
         firebaseFirestore = FirebaseFirestore.getInstance();
  coupenRedemptionlayout = findViewById(R.id.coupen_redemption_layout);
          final List<String> productImages = new ArrayList<>();

          firebaseFirestore.collection("PRODUCTS").document(getIntent().getStringExtra("PRODUCT_ID"))
                  .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()){

                      DocumentSnapshot documentSnapshot = task.getResult();

                      for (long x=1;x<(long)documentSnapshot.get("no_of_product_images")+1;x++){
                       productImages.add(documentSnapshot.get("product_image_"+x).toString());
                      }
                      ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                      productImagesViewPager.setAdapter(productImagesAdapter);
                      productTitle.setText(documentSnapshot.get("product_title").toString());
                     averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                     totalRatingMiniView.setText("("+(long)documentSnapshot.get("total_ratings")+"ratings)");
                     productPrice.setText("Rs."+documentSnapshot.get("product_price").toString()+"/-");
                     cuttedPrice.setText("Rs."+documentSnapshot.get("cutted_price").toString()+"/-");
                          if ((boolean)documentSnapshot.get("COD")){
                              codIndicator.setVisibility(View.VISIBLE);
                              tvcodIndicator.setVisibility(View.VISIBLE);
                          }else {
                              codIndicator.setVisibility(View.INVISIBLE);
                              tvcodIndicator.setVisibility(View.INVISIBLE);
                          }
                          rewardTitle.setText((long)documentSnapshot.get("free_coupens") + documentSnapshot.get("free_coupen_title").toString());
                          rewardBody.setText(documentSnapshot.get("free_coupen_body").toString());

                           if ((boolean)documentSnapshot.get("use_tab_layout")){

                               productDetailsTabContainer.setVisibility(View.VISIBLE);
                               productDetailsOnlyContainer.setVisibility(View.GONE);
                               productDescription=documentSnapshot.get("product_description").toString();


                              productOtherDetails = documentSnapshot.get("product_other_details").toString();


                             //ProductSpecificationFragment.productSpecificationModelList.add();
                       for (long x =1;x< (long)documentSnapshot.get("total_spec_titles")+1;x++){
                           productSpecificationModelList.add(new ProductSpecificationModel(0,documentSnapshot.get("spec_title_"+x).toString()));

                           for (long y =1;y<(long)documentSnapshot.get("spec_title_"+x+"_total_fields")+1;y++){

                             productSpecificationModelList.add(new ProductSpecificationModel(1,documentSnapshot.get("spec_title_"+x+"_field_"+y+"_name").toString(),documentSnapshot.get("spec_title_"+x+"_field_"+y+"_value").toString()));

                           }
                       }

                           }else {
                               productDetailsTabContainer.setVisibility(View.GONE);
                               productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                               productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());

                           }

                           totalRatings.setText((long)documentSnapshot.get("total_ratings")+"ratings");

                                   for (int x=0;x<5;x++){
                                   TextView rating  =(TextView) ratingsNoContainer.getChildAt(x);
                                  rating.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));

                                       ProgressBar progressBar = (ProgressBar)ratingsProgressBarContainer.getChildAt(x);
                                      int maxProgress= Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings")));
                                       progressBar.setMax(maxProgress);
                                       progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x)+"_star"))));

                                   }

                                   totalRatingsFigure.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));


                          averageRating.setText(documentSnapshot.get("average_rating").toString());
                      productDetailsViewpager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(),productDetailsTablayout.getTabCount(),productDescription,productOtherDetails,productSpecificationModelList));



                  }else {
                      String error = task.getException().getMessage();
                      Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                  }
              }
          });

        viewpagerIndicator.setupWithViewPager(productImagesViewPager,true);

        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentUser == null){
                    signInDialog.show();
                }
                else {
                    if (ALREADY_ADDED_TO_WISHLIST){
                        ALREADY_ADDED_TO_WISHLIST=false;
                        addToWishListBtn.setSupportBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff00")));

                    }else {
                        ALREADY_ADDED_TO_WISHLIST =true;
                        addToWishListBtn.setSupportBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                    }
                }


            }
        });


        productDetailsViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTablayout));
        productDetailsTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //tabPosition = tab.getPosition();
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
                      if (currentUser == null){
                          signInDialog.show();
                      }
                      else {
                          setRating(starPosition);
                      }
                  }
              });
          }



        ///////////rating layout

        buyNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser == null){
                    signInDialog.show();
                }else {
                    Intent deliveryIntent = new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                    startActivity(deliveryIntent);
                }

            }
        });

          addToCartBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (currentUser == null){
                      signInDialog.show();
                  }else {
                   //// todo add to cart
                  }
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
      //////////sign in dialog
        signInDialog = new Dialog(ProductDetailsActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_Btn);
        Button dialogSignUpBtn=signInDialog.findViewById(R.id.sign_up_Btn);
        final Intent registerIntent = new Intent(ProductDetailsActivity.this,RegisterActivity.class);


        dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment =false;
                startActivity(registerIntent);
            }
        });

        dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignInFragment.disableCloseBtn = true;
                SignUpFragment.disableCloseBtn = true;
                signInDialog.dismiss();
                setSignUpFragment =true;
                startActivity(registerIntent);
            }
        });




      /////////sign in dialog

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
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            coupenRedemptionlayout.setVisibility(View.GONE);
        }else {
            coupenRedemptionlayout.setVisibility(View.VISIBLE);
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
         if (currentUser == null){
             signInDialog.show();
         }else {


             Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
             showCart = true;
             startActivity(cartIntent);
             return true;
         }

        }else if (id == R.id.main_search_icon){
            return true;

        }else if(id == android.R.id.home){
           finish();
        }

        return super.onOptionsItemSelected(item);
    }
}