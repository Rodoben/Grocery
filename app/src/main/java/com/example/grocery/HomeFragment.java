package com.example.grocery;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.grocery.DBqueries.categoryModelList;
import static com.example.grocery.DBqueries.firebaseFirestore;

import static com.example.grocery.DBqueries.lists;
import static com.example.grocery.DBqueries.loadCategories;
import static com.example.grocery.DBqueries.loadFragmentData;
import static com.example.grocery.DBqueries.loadedCategoriesnames;


public class HomeFragment extends Fragment {
    private HomePageAdapter adapter;

   private RecyclerView categoryRecyclerView;
   private CategoryAdapter categoryAdapter;
  private RecyclerView homepageRecyclerView;
  private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

private ImageView noInternetConnection;
   private ConnectivityManager connectivityManager;
    private  NetworkInfo networkInfo;
    public static SwipeRefreshLayout swipeRefreshLayout;

    private List<CategoryModel> categoryModelFakeList = new ArrayList<>();



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
         noInternetConnection = view.findViewById(R.id.no_internet_connection);
          swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.red), getContext().getResources().getColor(R.color.green),getContext().getResources().getColor(R.color.colorAccent));


        categoryRecyclerView = view.findViewById(R.id.category_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        homepageRecyclerView =view.findViewById(R.id.home_page_recyclerview);
        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homepageRecyclerView.setLayoutManager(testingLayoutManager);



        ////////////////homepage fake list

        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));
        sliderModelFakeList.add(new SliderModel("null","#ffffff"));


        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));

        homePageModelFakeList.add(new HomePageModel(0,sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1,"","#ffffff"));
        homePageModelFakeList.add(new HomePageModel(2,"","#ffffff",horizontalProductScrollModelFakeList,new ArrayList<WishListModel>()));
        homePageModelFakeList.add(new HomePageModel(3,"","#ffffff",horizontalProductScrollModelFakeList));


        /////////////////homepage fake list






//////////////////category fake list
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("null",""));
/////////////////category fake list
        categoryAdapter = new CategoryAdapter(categoryModelFakeList);
        categoryRecyclerView.setAdapter(categoryAdapter);
        adapter = new HomePageAdapter(homePageModelFakeList);
        homepageRecyclerView.setAdapter(adapter);

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()==true) {
            noInternetConnection.setVisibility(View.GONE);

            if (categoryModelList.size() == 0){
                loadCategories(categoryRecyclerView,getContext());

            }else {
                categoryAdapter.notifyDataSetChanged();
            }

            if (lists.size() == 0){
                loadedCategoriesnames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());


                loadFragmentData(homepageRecyclerView,getContext(),0,"HOME");

            }else {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }




        }else {
            Glide.with(this).load(R.drawable.noconnection).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);

        }

/////////////refresh Layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                swipeRefreshLayout.setRefreshing(true);
                categoryModelList.clear();
                lists.clear();
                loadedCategoriesnames.clear();
                if (networkInfo != null && networkInfo.isConnected()==true) {
                    noInternetConnection.setVisibility(View.GONE);

                    categoryRecyclerView.setAdapter(categoryAdapter);
                   homepageRecyclerView.setAdapter(adapter);

                    loadCategories(categoryRecyclerView,getContext());
                    loadedCategoriesnames.add("HOME");
                    lists.add(new ArrayList<HomePageModel>());
                    loadFragmentData(homepageRecyclerView,getContext(),0,"HOME");

                }else {
                    Glide.with(getContext()).load(R.drawable.noconnection).into(noInternetConnection);
                    noInternetConnection.setVisibility(View.VISIBLE);
                }

            }
        });


/////////////refresh layout




      return  view;
    }

}