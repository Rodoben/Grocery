package com.example.grocery;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private FrameLayout frameLayout;
    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT=1;
    private static final int ORDER_FRAGMENT=2;
    private static final  int MYWISHLIST_FRAGMENT=3;
    private static final  int MYREWARDS_FRAGMENT=4;
    private static final int MYACCOUNT_FRAGMENT=5;
    private static int currentFragment=-1;
  private   NavigationView navigationView;
   private ImageView actionBarLogo;
    private Toolbar toolbar;
    private Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        actionBarLogo=findViewById(R.id.action_bar_logo);
        setSupportActionBar(toolbar);

            window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

      //frameLayout = findViewById(R.id.main_framelayout);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

       // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
         navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

         navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout = findViewById(R.id.main_framelayout);
        setFragment(new HomeFragment(),HOME_FRAGMENT);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (currentFragment == HOME_FRAGMENT){
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
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



                actionBarLogo.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setFragment(new HomeFragment(),HOME_FRAGMENT);
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.main_cart_icon){
            gotoFragment("My Cart", new MyCartFragment(),CART_FRAGMENT);
            return true;

        }else if (id == R.id.main_search_icon){
            return true;

        }else if(id == R.id.action_notfication_icon){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoFragment(String title,Fragment fragment,int fragmentNo) {
        actionBarLogo.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);


        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo == CART_FRAGMENT) {
            navigationView.getMenu().getItem(3).setChecked(true);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id=menuItem.getItemId();
        if(id == R.id.nav_myorder){
            gotoFragment("My Orders", new MyOrdersFragment(),ORDER_FRAGMENT);

        }else if(id == R.id.nav_mycart)
        {
            gotoFragment("My Cart", new MyCartFragment(),CART_FRAGMENT);

        }else if(id == R.id.nav_myreward){
            gotoFragment("My Rewards",new MyRewardsFragment(),MYREWARDS_FRAGMENT);

        }else if(id == R.id.nav_myaccount){
            gotoFragment("My Account",new MyAccountFragment(),MYACCOUNT_FRAGMENT);
        }else if(id == R.id.nav_mywishlist){
            gotoFragment("My WishList",new MyWishListFragment(),MYWISHLIST_FRAGMENT);

        }else if(id ==  R.id.nav_rasan){
            getSupportActionBar().setTitle(null);

            actionBarLogo.setVisibility(View.VISIBLE);
              invalidateOptionsMenu();
            setFragment(new HomeFragment(),HOME_FRAGMENT);

     }else if(id == R.id.nav_signout){


        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

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