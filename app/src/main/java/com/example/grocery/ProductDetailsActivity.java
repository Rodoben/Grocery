package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.grocery.MainActivity.showCart;
import static com.example.grocery.RegisterActivity.setSignUpFragment;


public class ProductDetailsActivity extends AppCompatActivity {
  public static  boolean running_wishlistquery =  false;
    public static  boolean running_ratingquery =  false;
    public static boolean running_cartQuery = false;
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
 private String productOriginalPrice;
 private  TextView cuttedPrice;
 private ImageView codIndicator;
 private TextView tvcodIndicator;
//////////product description

private ConstraintLayout productDetailsOnlyContainer;
private  ConstraintLayout productDetailsTabContainer;

//////////product description

 ///////////coupenDialog
    private TextView coupenTitle;
    private TextView coupenExpiryDate;
    private TextView coupenBody;
    private RecyclerView coupensRecyclerView;
    private LinearLayout selectedCoupen;
    private TextView discountedPrice;
    private TextView originalPrice;
    //////////coupen Dialog

    private  Dialog signInDialog;
    private Dialog loadingDialog;

    private Button  buyNowbtn;
    private LinearLayout addToCartBtn;


    private DocumentSnapshot documentSnapshot;
   public static MenuItem cartItem;
public static Activity productDetailsActivity;
 ///////// rating layout
   public static LinearLayout rateNowContainer;
private TextView totalRatingsFigure;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private LinearLayout ratingsProgressBarContainer;
    private TextView averageRating;
 /////////  rating layout

   private String productOtherDetails;
   private   String productDescription;
   private boolean inStock;

   private TextView badgeCount;
   //public static  int tabPosition =-1;

    //ProductSpecificationFragment.productSpecificationModelList = new ArrayList<>();
   private List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();


 public static boolean ALREADY_ADDED_TO_WISHLIST = false;
    public static boolean ALREADY_ADDED_TO_CARTLIST = false;
 public static FloatingActionButton addToWishListBtn;

 private FirebaseFirestore firebaseFirestore;
 private FirebaseUser currentUser;
 public static String productID;
 public  static int initialRating;


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
           productID  = getIntent().getStringExtra("PRODUCT_ID");

            initialRating = -1;




         ///////loading dialog
        loadingDialog = new Dialog(ProductDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        //////loading dialog


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

        originalPrice = checkCoupenPriceDalog.findViewById(R.id.original_price);
        discountedPrice = checkCoupenPriceDalog.findViewById(R.id.discounted_price);




        LinearLayoutManager layoutManager = new LinearLayoutManager(ProductDetailsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        coupensRecyclerView.setLayoutManager(layoutManager);




        toggleRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRecyclerView();
            }
        });


//////////////////coupen dialog




          firebaseFirestore.collection("PRODUCTS").document(productID)
                  .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
              @Override
              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()){

                      documentSnapshot = task.getResult();
                      firebaseFirestore.collection("PRODUCTS").document(productID).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).get()
                              .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                  @Override
                                  public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                      if (task.isSuccessful()){



                                          for (long x=1;x<(long)documentSnapshot.get("no_of_product_images")+1;x++){
                                              productImages.add(documentSnapshot.get("product_image_"+x).toString());
                                          }
                                          ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImages);
                                          productImagesViewPager.setAdapter(productImagesAdapter);


                                          productTitle.setText(documentSnapshot.get("product_title").toString());
                                          averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                                          totalRatingMiniView.setText("("+(long)documentSnapshot.get("total_ratings")+"ratings)");
                                          productPrice.setText("Rs."+documentSnapshot.get("product_price").toString()+"/-");


                                             ////for coupen dialog
                                          originalPrice.setText(productPrice.getText());
                                          productOriginalPrice = documentSnapshot.get("product_price").toString();
                                          MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(DBqueries.rewardModelList,true,coupensRecyclerView,selectedCoupen,productOriginalPrice,coupenTitle,coupenExpiryDate,coupenBody,discountedPrice);
                                          coupensRecyclerView.setAdapter(myRewardsAdapter);
                                          myRewardsAdapter.notifyDataSetChanged();

                                          // for coupen dialog

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

                                          totalRatings.setText((long)documentSnapshot.get("total_ratings")+" ratings");

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

                                          if (currentUser !=null){
                                              if (DBqueries.myRating.size() == 0){
                                                  DBqueries.loadRatingList(ProductDetailsActivity.this);
                                              }
                                              if (DBqueries.cartList.size() == 0){
                                                  DBqueries.loadCartList(ProductDetailsActivity.this,loadingDialog,false,badgeCount,new TextView(ProductDetailsActivity.this));
                                              }
                                              if (DBqueries.wishList.size() == 0){
                                                  DBqueries.loadWishList(ProductDetailsActivity.this,loadingDialog,false);

                                              }
                                              if (DBqueries.rewardModelList.size()==0){
                                                  DBqueries.loadRewards(ProductDetailsActivity.this,loadingDialog,false);
                                              }
                                              if (DBqueries.cartList.size() != 0 && DBqueries.cartList.size() != 0 && DBqueries.wishList.size() != 0 && DBqueries.rewardModelList.size()!=0 ){
                                                  loadingDialog.dismiss();
                                              }
                                          }
                                          else {
                                              loadingDialog.dismiss();
                                          }

                                          if (DBqueries.myRatedIds.contains(productID)){
                                              int index = DBqueries.myRatedIds.indexOf(productID);
                                              initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index)))-1;
                                              setRating(initialRating);
                                          }
                                          if (DBqueries.cartList.contains(productID)){
                                              ALREADY_ADDED_TO_CARTLIST = true;

                                          }else {
                                              ALREADY_ADDED_TO_CARTLIST = false;
                                          }

                                          if (DBqueries.wishList.contains(productID)){
                                              ALREADY_ADDED_TO_WISHLIST = true;
                                              addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
                                              Toast.makeText(ProductDetailsActivity.this, "Product added to WishList", Toast.LENGTH_SHORT).show();
                                          }else {
                                              addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                                              ALREADY_ADDED_TO_WISHLIST = false;
                                          }



                                          if (task.getResult().getDocuments().size() < (long)documentSnapshot.get("stock_quantity")){
                                              inStock = true;

                                              addToCartBtn.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      if (currentUser == null){
                                                          signInDialog.show();
                                                      }else {
                                                          if (!running_cartQuery){
                                                              running_cartQuery = true;

                                                              if (ALREADY_ADDED_TO_CARTLIST){
                                                                  Toast.makeText(ProductDetailsActivity.this,"Already Added to Cart",Toast.LENGTH_SHORT).show();
                                                              }else {

                                                                  Map<String, Object> addProduct = new HashMap<>();
                                                                  addProduct.put("product_id_" + String.valueOf(DBqueries.cartList.size()), productID);
                                                                  addProduct.put("list_size", (long) DBqueries.cartList.size() + 1);

                                                                  firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_CART")
                                                                          .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                      @Override
                                                                      public void onComplete(@NonNull Task<Void> task) {

                                                                          if (task.isSuccessful()) {
                                                                              if (DBqueries.cartitemModelList.size() != 0) {
                                                                                  DBqueries.cartitemModelList.add(0,new CartitemModel(CartitemModel.CART_ITEM,productID,documentSnapshot.get("product_image_1").toString()
                                                                                          , documentSnapshot.get("product_title").toString()
                                                                                          , (long) documentSnapshot.get("free_coupens")
                                                                                          , documentSnapshot.get("product_price").toString()
                                                                                          , documentSnapshot.get("cutted_price").toString()
                                                                                          , (long) 1
                                                                                          , (long) 0
                                                                                          , (long) 0
                                                                                          ,inStock
                                                                                          ,(long)documentSnapshot.get("max_quantity")
                                                                                          ,(long)documentSnapshot.get("stock_quantity")));
                                                                              }

                                                                              ALREADY_ADDED_TO_CARTLIST = true;

                                                                              DBqueries.cartList.add(productID);

                                                                              Toast.makeText(ProductDetailsActivity.this, "Added to Cart successfully", Toast.LENGTH_LONG).show();
                                                                              // addToWishListBtn.setEnabled(true);
                                                                              invalidateOptionsMenu();
                                                                              running_cartQuery = false;

                                                                          }



                                                                          else {
                                                                              // addToWishListBtn.setEnabled(true);
                                                                              running_cartQuery = false;
                                                                              String error = task.getException().getMessage();
                                                                              Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                                                          }

                                                                      }
                                                                  });
                                                              }



                                                          }
                                                      }
                                                  }
                                              });


                                          }else {
                                              inStock = false;

                                              buyNowbtn.setVisibility(View.GONE);
                                              TextView outOfStock = (TextView)addToCartBtn.getChildAt(0);
                                              outOfStock.setText("OUT OF STOCK");
                                              outOfStock.setTextColor(getResources().getColor(R.color.red));
                                              outOfStock.setCompoundDrawables(null,null,null,null);

                                          }
                                      }else {

                                          String error = task.getException().getMessage();
                                          Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                                      }

                                  }
                              });



                  }else {
                      loadingDialog.dismiss();
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
                    //addToWishListBtn.setEnabled(false);
                    if (!running_wishlistquery){
                        running_wishlistquery = true;

                    if (ALREADY_ADDED_TO_WISHLIST){
                        int index= DBqueries.wishList.indexOf(productID);
                        DBqueries.removeFromWishList(index,ProductDetailsActivity.this);


                        addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));

                    }else {
                        addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
                        Map<String, Object> addProduct = new HashMap<>();
                        addProduct.put("product_id_" + String.valueOf(DBqueries.wishList.size()), productID);
                        addProduct.put("list_size", (long) DBqueries.wishList.size() + 1);
                        firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_WISHLIST")
                                .update(addProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    if (DBqueries.wishListModelList.size() != 0) {
                                                    DBqueries.wishListModelList.add(new WishListModel(productID, documentSnapshot.get("product_image_1").toString()
                                                            , documentSnapshot.get("product_title").toString()
                                                            , (long) documentSnapshot.get("free_coupens")
                                                            , documentSnapshot.get("average_rating").toString()
                                                            , (long) documentSnapshot.get("total_ratings")
                                                            , documentSnapshot.get("product_price").toString()
                                                            , documentSnapshot.get("cutted_price").toString()
                                                            , (boolean) documentSnapshot.get("COD")
                                                           ,inStock));
                                                }




                                                ALREADY_ADDED_TO_WISHLIST = true;
                                                addToWishListBtn.setSupportImageTintList(getResources().getColorStateList(R.color.red));
                                                DBqueries.wishList.add(productID);







                                } else {
                                   // addToWishListBtn.setEnabled(true);
                            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#000000")));

                                    String error = task.getException().getMessage();
                                    Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                }
                                running_wishlistquery = false;

                            }
                        });
                    }



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
                          if (starPosition!=initialRating){

                          if (!running_ratingquery) {
                              running_ratingquery = true;


                              setRating(starPosition);
                              Map<String, Object> updateRating = new HashMap<>();

                              if (DBqueries.myRatedIds.contains(productID)) {
                                  TextView oldrating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                  TextView finalrating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                  updateRating.put(initialRating + 1 + "_star", Long.parseLong(oldrating.getText().toString()) - 1);
                                  updateRating.put(starPosition + 1 + "_star", Long.parseLong(finalrating.getText().toString()) + 1);
                                  updateRating.put("average_rating", calculateAverageRating((long) starPosition - initialRating, true));


                              } else {


                                  updateRating.put(starPosition + 1 + "_star", (long) documentSnapshot.get(starPosition + 1 + "_star") + 1);
                                  updateRating.put("average_rating", calculateAverageRating((long) starPosition + 1, false));
                                  updateRating.put("total_ratings", (long) documentSnapshot.get("total_ratings") + 1);


                              }
                              firebaseFirestore.collection("PRODUCTS").document(productID)
                                      .update(updateRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {

                                      if (task.isSuccessful()) {
                                          Map<String, Object> myRating = new HashMap<>();
                                          if (DBqueries.myRatedIds.contains(productID)) {

                                              myRating.put("rating_" + DBqueries.myRatedIds.indexOf(productID), (long) starPosition + 1);


                                          } else {

                                              myRating.put("list_size", (long) DBqueries.myRatedIds.size() + 1);
                                              myRating.put("product_id_" + DBqueries.myRatedIds.size(), productID);
                                              myRating.put("rating_" + DBqueries.myRatedIds.size(), (long) starPosition + 1);

                                          }


                                          firebaseFirestore.collection("USERS").document(currentUser.getUid()).collection("USER_DATA").document("MY_RATINGS")
                                                  .update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                              @Override
                                              public void onComplete(@NonNull Task<Void> task) {
                                                  if (task.isSuccessful()) {

                                                      if (DBqueries.myRatedIds.contains(productID)) {

                                                          DBqueries.myRating.set(DBqueries.myRatedIds.indexOf(productID), (long) starPosition + 1);

                                                          TextView oldrating = (TextView) ratingsNoContainer.getChildAt(5 - initialRating - 1);
                                                          TextView finalrating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                          oldrating.setText(String.valueOf(Integer.parseInt(oldrating.getText().toString()) - 1));
                                                          finalrating.setText(String.valueOf(Integer.parseInt(finalrating.getText().toString()) + 1));

                                                      } else {

                                                          DBqueries.myRatedIds.add(productID);
                                                          DBqueries.myRating.add((long) starPosition + 1);
                                                          TextView rating = (TextView) ratingsNoContainer.getChildAt(5 - starPosition - 1);
                                                          rating.setText(String.valueOf(Integer.parseInt(rating.getText().toString()) + 1));

                                                          totalRatingMiniView.setText("(" + ((long) documentSnapshot.get("total_ratings") + 1) + "ratings)");
                                                          totalRatings.setText((long) documentSnapshot.get("total_ratings") + 1 + " ratings");
                                                          totalRatingsFigure.setText(String.valueOf((long) documentSnapshot.get("total_ratings") + 1));

                                                          Toast.makeText(ProductDetailsActivity.this, "Thank you ! for Rating", Toast.LENGTH_SHORT).show();
                                                      }


                                                      for (int x = 0; x < 5; x++) {
                                                          TextView ratingfigures = (TextView) ratingsNoContainer.getChildAt(x);
                                                          //ratingfigures.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));

                                                          ProgressBar progressBar = (ProgressBar) ratingsProgressBarContainer.getChildAt(x);

                                                          int maxProgress = Integer.parseInt(totalRatingsFigure.getText().toString());
                                                          progressBar.setMax(maxProgress);

                                                          progressBar.setProgress(Integer.parseInt(ratingfigures.getText().toString()));

                                                      }
                                                      initialRating = starPosition;
                                                      averageRating.setText(calculateAverageRating(0, true));
                                                      averageRatingMiniView.setText(calculateAverageRating(0, true));

                                                      if (DBqueries.wishList.contains(productID) && DBqueries.wishListModelList.size() != 0) {


                                                          int index = DBqueries.wishList.indexOf(productID);
                                                          DBqueries.wishListModelList.get(index).setRating(averageRating.getText().toString());
                                                          DBqueries.wishListModelList.get(index).setTotalRatings(Long.parseLong((totalRatingsFigure.getText().toString())));

                                                      }

                                                  } else {
                                                      setRating(initialRating);
                                                      String error = task.getException().getMessage();
                                                      Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();
                                                  }
                                                  running_ratingquery = false;

                                              }
                                          });

                                      } else {
                                          running_ratingquery = false;
                                          setRating(initialRating);
                                          String error = task.getException().getMessage();
                                          Toast.makeText(ProductDetailsActivity.this, error, Toast.LENGTH_LONG).show();

                                      }

                                  }
                              });


                          }
                          }
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
                    DeliveryActivity.fromCart=false;
                    loadingDialog.show();
                    productDetailsActivity = ProductDetailsActivity.this;
                    DeliveryActivity.cartitemModelList = new ArrayList<>();
                    DeliveryActivity.cartitemModelList.add(new CartitemModel(CartitemModel.CART_ITEM,productID,documentSnapshot.get("product_image_1").toString()
                            , documentSnapshot.get("product_title").toString()
                            , (long) documentSnapshot.get("free_coupens")
                            , documentSnapshot.get("product_price").toString()
                            , documentSnapshot.get("cutted_price").toString()
                            , (long) 1
                            , (long) 0
                            , (long) 0
                            ,inStock
                            ,(long)documentSnapshot.get("max_quantity")
                            ,(long)documentSnapshot.get("stock_quantity")));

                    DeliveryActivity.cartitemModelList.add(new CartitemModel(CartitemModel.TOTAL_AMOUNT));

                    if (DBqueries.addressesModelList.size() == 0) {
                        DBqueries.loadAddresses(ProductDetailsActivity.this, loadingDialog);
                    }else {
                        loadingDialog.dismiss();
                        Intent deliveryIntent = new Intent(ProductDetailsActivity.this,DeliveryActivity.class);
                        startActivity(deliveryIntent);
                    }
                }

            }
        });






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

    private void  showDialogRecyclerView(){
        if (coupensRecyclerView.getVisibility() == View.GONE)
        {
            coupensRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupen.setVisibility(View.GONE);
        }else {
            coupensRecyclerView.setVisibility(View.GONE);
            selectedCoupen.setVisibility(View.VISIBLE);
        }

    }

    public static void setRating(int starPosition) {


            for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
                ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
                if (x <= starPosition) {
                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
                }

        }
    }


    private String calculateAverageRating(long CurrentUserRating,boolean update) {
        Double totalStars = Double.valueOf(0);
        for (int x = 1; x < 6; x++) {
            TextView ratingno = (TextView) ratingsNoContainer.getChildAt(5 - x);
            totalStars = totalStars + (Long.parseLong(ratingno.getText().toString()) * x);
        }
        totalStars = totalStars + CurrentUserRating;

        if (update) {
            return String.valueOf(totalStars / Long.parseLong(totalRatingsFigure.getText().toString())).substring(0,3);
        } else {
            return String.valueOf(totalStars / (Long.parseLong(totalRatingsFigure.getText().toString()) + 1)).substring(0,3);
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


        if (currentUser !=null){

            if (DBqueries.myRating.size() == 0){
                DBqueries.loadRatingList(ProductDetailsActivity.this);
            }

            if (DBqueries.wishList.size() == 0){
                DBqueries.loadWishList(ProductDetailsActivity.this,loadingDialog,false);

            }
            else {
                loadingDialog.dismiss();
            }}


        else {
            loadingDialog.dismiss();
        }

        if (DBqueries.myRatedIds.contains(productID)){
            int index = DBqueries.myRatedIds.indexOf(productID);
            initialRating = Integer.parseInt(String.valueOf(DBqueries.myRating.get(index)))-1;
            setRating(initialRating);

        } if (DBqueries.cartList.contains(productID)){
            ALREADY_ADDED_TO_CARTLIST = true;

        }else {
            ALREADY_ADDED_TO_CARTLIST = false;
        }

        if (DBqueries.wishList.contains(productID)){
            ALREADY_ADDED_TO_WISHLIST = true;
            addToWishListBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#ff0000")));
            Toast.makeText(ProductDetailsActivity.this, "Product added to WishList", Toast.LENGTH_SHORT).show();
        }else {
            ALREADY_ADDED_TO_WISHLIST = false;
        }
        invalidateOptionsMenu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);

         cartItem = menu.findItem(R.id.main_cart_icon);


            cartItem.setActionView(R.layout.badge_layout);
            ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
            badgeIcon.setImageResource(R.drawable.ic_baseline_shopping_cart_24);


             badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
        if (currentUser!=null){
            if (DBqueries.cartList.size() == 0){
                DBqueries.loadCartList(ProductDetailsActivity.this,loadingDialog,false,badgeCount,new TextView(ProductDetailsActivity.this));
            }
            else {
                badgeCount.setVisibility(View.VISIBLE);
                if (DBqueries.cartList.size()<99){
                    badgeCount.setText(String.valueOf(DBqueries.cartList.size()));
                }else {
                    badgeCount.setText("99");
                }
            }
        }
            cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentUser == null){
                        signInDialog.show();
                    }else {


                        Intent cartIntent = new Intent(ProductDetailsActivity.this, MainActivity.class);
                        showCart = true;
                        startActivity(cartIntent);

                    }

                }
            });


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
            productDetailsActivity = null;
           finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        productDetailsActivity = null;
        super.onBackPressed();
    }
}