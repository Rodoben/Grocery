package com.example.grocery;

import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {
   private RecyclerView categoryRecyclerView;
   private CategoryAdapter categoryAdapter;
  private RecyclerView testing;







    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
   categoryRecyclerView = view.findViewById(R.id.category_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
        categoryModelList.add(new CategoryModel("link","Home"));
        categoryModelList.add(new CategoryModel("link","Furniture"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));
        categoryModelList.add(new CategoryModel("link","Electronics"));



        categoryAdapter = new CategoryAdapter(categoryModelList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        ///////Banner Slider

         List<SliderModel> sliderModelList = new ArrayList<SliderModel>();

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

         testing =view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);
        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner1,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"Ronald",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2,"Deals of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner1,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"Ronald",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2,"Deals of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(0,sliderModelList));
        homePageModelList.add(new HomePageModel(1,R.drawable.banner1,"#000000"));
        homePageModelList.add(new HomePageModel(2,"Deals of The Day",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(3,"Ronald",horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2,"Deals of The Day",horizontalProductScrollModelList));



        HomePageAdapter adapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //////////////


      return  view;
    }

}