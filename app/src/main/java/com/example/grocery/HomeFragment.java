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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment {
   private RecyclerView categoryRecyclerView;
   private CategoryAdapter categoryAdapter;
   /////Banner Slider
    private ViewPager bannerSliderViewPager;
    private  List<SliderModel> sliderModelList;
    private  int currentPage = 2;
    private Timer timer;
    final private  long DELAY_TIME=3000;
    final  private  long PERIOD_TIME = 3000;

    /////bannr slide

    /////stripadd
    private ImageView stripAddImage;
    private ConstraintLayout stripAddContainer;
    ////strip add


    /////horizontal product layout
    private TextView horizontalLayoutTitle;
    private Button horizontalViewAllBtn;
    private RecyclerView horizontalRecyclerView;


    //////horizontal product layout


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
        bannerSliderViewPager=view.findViewById(R.id.bannerSlider_viewPager);
          sliderModelList = new ArrayList<SliderModel>();
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#0000A0"));

          sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#0000A0"));

        sliderModelList.add(new SliderModel(R.mipmap.ic_launcher,"#0000A0"));
        sliderModelList.add(new SliderModel(R.mipmap.logo,"#0000A0"));
        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
       bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);
        bannerSliderViewPager.setCurrentItem(currentPage);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
              currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
              if (state == ViewPager.SCROLL_STATE_IDLE){
                  pageLooper();
              }
            }
        };
       bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
       startbannerSlideShow();
       bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View view, MotionEvent motionEvent) {

              pageLooper();
              stopbannerSlideShow();
              if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                  startbannerSlideShow();
              }
               return false;
           }
       });


        ///////baner Slider

        /////strip add
        stripAddImage = view.findViewById(R.id.strip_add_image);
        stripAddContainer= view.findViewById(R.id.strip_add_container);
         stripAddImage.setImageResource(R.drawable.banner);
         stripAddContainer.setBackgroundColor(Color.parseColor("#000000"));
        /////strip add

        //////horizontal product layout
        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalViewAllBtn = view.findViewById(R.id.horizontal_scroll_viewall_button);
        horizontalRecyclerView=view.findViewById(R.id.horizontal_layout_recycler_view);
     List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
     horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner,"REDMI","SD 625 Processor","rs.6999/-"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.drawable.banner,"REDMI","SD 625 Processor","rs.6999/-"));


        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
         LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
         linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
         horizontalRecyclerView.setLayoutManager(linearLayoutManager);
         horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
         horizontalProductScrollAdapter.notifyDataSetChanged();

        ///////horizontal product layout

      return  view;
    }
    /////////banner slider

    private void pageLooper(){
        if(currentPage == sliderModelList.size() - 2){
            currentPage = 2;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
        if(currentPage == 1){
            currentPage = sliderModelList.size()-3;
            bannerSliderViewPager.setCurrentItem(currentPage,false);
        }
    }
    private  void startbannerSlideShow(){
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderModelList.size()){
                    currentPage=1;
                }
                bannerSliderViewPager.setCurrentItem(currentPage++);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_TIME,PERIOD_TIME);
    }

    private void stopbannerSlideShow(){
        timer.cancel();
    }
    /////////banner slider
}