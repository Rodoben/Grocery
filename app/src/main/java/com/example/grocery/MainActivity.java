package com.example.grocery;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static com.example.grocery.RegisterActivity.setSignUpFragment;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private FrameLayout frameLayout;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT=1;
    private static final int ORDER_FRAGMENT=2;
    private static final  int MYWISHLIST_FRAGMENT=3;
    private static final  int MYREWARDS_FRAGMENT=4;
    private static final int MYACCOUNT_FRAGMENT=5;
    private Dialog signInDialog;
    public  static  DrawerLayout drawer;
    private FirebaseUser currentUser;
    private TextView badgeCount;

    public static Activity mainActivity;
    public  static  boolean resetMainActivity = false;

    public static boolean showCart=false;
    private int currentFragment=-1;
  private   NavigationView navigationView;
   private ImageView actionBarLogo;
    private Toolbar toolbar;
    private Window window;
    private int scrollFlags;
   private AppBarLayout.LayoutParams params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        actionBarLogo=findViewById(R.id.action_bar_logo);
        setSupportActionBar(toolbar);

            window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
              params = (AppBarLayout.LayoutParams)toolbar.getLayoutParams();
         scrollFlags = params.getScrollFlags();


             //frameLayout = findViewById(R.id.main_framelayout);
        drawer = findViewById(R.id.drawer_layout);


         navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

         navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout = findViewById(R.id.main_framelayout);

        if (showCart) {
            mainActivity  = this;
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart", new MyCartFragment(), -2);
        } else {

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }





        signInDialog = new Dialog(MainActivity.this);
        signInDialog.setContentView(R.layout.sign_in_dialog);
        signInDialog.setCancelable(true);
        signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Button dialogSignInBtn = signInDialog.findViewById(R.id.sign_in_Btn);
        Button dialogSignUpBtn=signInDialog.findViewById(R.id.sign_up_Btn);
        final Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);


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


    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null){
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(false);
        }else {
            navigationView.getMenu().getItem(navigationView.getMenu().size()-1).setEnabled(true);
        }
if (resetMainActivity){

    resetMainActivity = false;
    actionBarLogo.setVisibility(View.VISIBLE);
    setFragment(new HomeFragment(),HOME_FRAGMENT);
    navigationView.getMenu().getItem(0).setChecked(true);
}

        invalidateOptionsMenu();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (currentFragment == HOME_FRAGMENT){
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
            MenuItem cartItem = menu.findItem(R.id.main_cart_icon);


                cartItem.setActionView(R.layout.badge_layout);
                ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badge_icon);
                badgeIcon.setImageResource(R.drawable.ic_baseline_shopping_cart_24);


                badgeCount = cartItem.getActionView().findViewById(R.id.badge_count);
                  if (currentUser!=null){

                      if (DBqueries.cartList.size() == 0){
                          DBqueries.loadCartList(MainActivity.this,new Dialog(MainActivity.this),false,badgeCount,new TextView(MainActivity.this));
                      }else {
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
                            gotoFragment("My Cart", new MyCartFragment(),CART_FRAGMENT);
                        }
                    }
                });



        }

        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();

            } else {

                if (showCart) {
                    mainActivity = null;
                       showCart=false;
                       finish();
                } else {

                    actionBarLogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.main_cart_icon){
            if (currentUser == null){
                signInDialog.show();
            }else {
                gotoFragment("My Cart", new MyCartFragment(),CART_FRAGMENT);
            }

            return true;

        }else if (id == R.id.main_search_icon){
            return true;

        }else if(id == R.id.action_notfication_icon){
            return true;
        }else if (id == android.R.id.home){
            if (showCart){
                mainActivity = null;
                showCart=false;
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoFragment(String title,Fragment fragment,int fragmentNo) {
        actionBarLogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);


        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT || showCart) {
            navigationView.getMenu().getItem(3).setChecked(true);
        params.setScrollFlags(0);


        }else {
            params.setScrollFlags(scrollFlags);
        }
    }
MenuItem menuItemm;
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (currentUser != null) {

            drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                    menuItemm=menuItem;
                    int id = menuItem.getItemId();
                    if (id == R.id.nav_myorder) {
                        gotoFragment("My Orders", new MyOrdersFragment(), ORDER_FRAGMENT);

                    } else if (id == R.id.nav_mycart) {
                        gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);

                    } else if (id == R.id.nav_myreward) {
                        gotoFragment("My Rewards", new MyRewardsFragment(), MYREWARDS_FRAGMENT);

                    } else if (id == R.id.nav_myaccount) {
                        gotoFragment("My Account", new MyAccountFragment(), MYACCOUNT_FRAGMENT);
                    } else if (id == R.id.nav_mywishlist) {
                        gotoFragment("My WishList", new MyWishListFragment(), MYWISHLIST_FRAGMENT);

                    } else if (id == R.id.nav_rasan) {
                        getSupportActionBar().setTitle(null);

                        actionBarLogo.setVisibility(View.VISIBLE);
                        invalidateOptionsMenu();
                        setFragment(new HomeFragment(), HOME_FRAGMENT);

                    } else if (id == R.id.nav_signout) {

                        FirebaseAuth.getInstance().signOut();
                        DBqueries.clearData();
                        Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                        startActivity(registerIntent);
                        finish();


                    }

                }
            });


           //
            return true;
        }else {
            //drawer.closeDrawer(GravityCompat.START);
            signInDialog.show();
            return false;
        }



    }
    private void setFragment(Fragment fragment,int fragmentNo){

        if (fragmentNo != currentFragment){
            if (fragmentNo == MYREWARDS_FRAGMENT){
                 toolbar.setBackgroundColor(Color.parseColor("#7F00FF"));
                window.setStatusBarColor(Color.parseColor("#7F00FF"));

            }else {
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
            }
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(),fragment);
            fragmentTransaction.commit();
        }


    }
}