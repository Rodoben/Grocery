package com.example.grocery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MyWishListFragment extends Fragment {

    private RecyclerView wishlistRecyclerView;
    public MyWishListFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_wish_list, container, false);
         wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishListModel> wishListModelList = new ArrayList<>();
        wishListModelList.add(new WishListModel(R.drawable.banner1,"Pixel 2",1,"3",145,"50098/-","233645/-","Cash on delivery"));
        wishListModelList.add(new WishListModel(R.drawable.banner1,"Pixel 2",0,"3.2",145,"50098/-","233645/-","Cash on delivery"));
        wishListModelList.add(new WishListModel(R.drawable.banner1,"Pixel 2",1,"3.5",15,"50098/-","233645/-","Cash on delivery"));
        wishListModelList.add(new WishListModel(R.drawable.banner1,"Pixel 2",2,"3.6",14,"50098/-","233645/-","Cash on delivery"));
        wishListModelList.add(new WishListModel(R.drawable.banner1,"Pixel 2",1,"3",5,"50098/-","233645/-","Cash on delivery"));
        wishListModelList.add(new WishListModel(R.drawable.banner1,"Pixel 2",4,"3",15,"50098/-","233645/-","Cash on delivery"));

        WishListAdapter wishListAdapter = new WishListAdapter(wishListModelList);
        wishlistRecyclerView.setAdapter(wishListAdapter);
        wishListAdapter.notifyDataSetChanged();

       return view;
    }
}