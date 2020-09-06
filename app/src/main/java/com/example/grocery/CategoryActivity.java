package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
  private RecyclerView categoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String  title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      categoryRecyclerView = findViewById(R.id.category_recycler_view);

        ///////Banner Slider

        List<SliderModel> sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#0000A0"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#0000A0"));



        ///////baner Slider

        /////strip add

        /////strip add

        //////horizontal product layout

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner1,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner1,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner1,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner1,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner1,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner1,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner1,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner1,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner1,"REDMI","SD 625 Processor","rs.6999/-"));


        ///////horizontal product layout

        ////grid product layout


        ////grid product layout

        //////////////


        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner1,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"Ronald",horizontalProductScrollModelList));




        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //////////////


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            return true;

        } else if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}