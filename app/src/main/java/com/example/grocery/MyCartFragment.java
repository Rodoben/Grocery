package com.example.grocery;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MyCartFragment extends Fragment {

    private RecyclerView cartItemsRecyclerView;
    private Button continueBtn;
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_cart, container, false);

       cartItemsRecyclerView =view.findViewById(R.id.cart_items_recyclerView);
       continueBtn = view.findViewById(R.id.cart_continue_btn);


        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();



       LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);

        if (DBqueries.cartitemModelList.size() == 0){
            DBqueries.cartList.clear();
            DBqueries.loadCartList(getContext(),loadingDialog,true);
        }
        else {
            loadingDialog.dismiss();
        }

        //List<CartitemModel> cartitemModelList = new ArrayList<>();

       // cartitemModelList.add(new CartitemModel(1,"Price (3 items)","Rs.356654/-","free","Rs550000/-","Rs.500/-"));

        CartAdapter cartAdapter = new CartAdapter(DBqueries.cartitemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();





          continueBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent deliveryIntent = new Intent(getContext(),AddAddressActivity.class);
                  getContext().startActivity(deliveryIntent);

              }
          });
       return view;

    }
}