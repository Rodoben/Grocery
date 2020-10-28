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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MyCartFragment extends Fragment {

    private RecyclerView cartItemsRecyclerView;
    private Button continueBtn;
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;

    private TextView totalAmount;
    //public static LinearLayout proceedlayout;

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
         totalAmount = view.findViewById(R.id.total_cart_amount);
        // proceedlayout = view.findViewById(R.id.linearLayout6);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();



       LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemsRecyclerView.setLayoutManager(layoutManager);



        //List<CartitemModel> cartitemModelList = new ArrayList<>();

      // cartitemModelList.add(new CartitemModel(1,"Price (3 items)","Rs.356654/-","free","Rs550000/-","Rs.500/-"));

         cartAdapter = new CartAdapter(DBqueries.cartitemModelList,totalAmount,true);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();






          continueBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

              DeliveryActivity.cartitemModelList = new ArrayList<>();

             DeliveryActivity.fromCart = true;
              for (int x= 0;x<DBqueries.cartitemModelList.size();x++){
                  CartitemModel cartitemModel  = DBqueries.cartitemModelList.get(x);
                  if (cartitemModel.isInStock()){

                      DeliveryActivity.cartitemModelList.add(cartitemModel);

                  }
              }
              DeliveryActivity.cartitemModelList.add(new CartitemModel(CartitemModel.TOTAL_AMOUNT));


                  loadingDialog.show();

                  if (DBqueries.addressesModelList.size() == 0) {
                      DBqueries.loadAddresses(getContext(), loadingDialog);
                  }else {
                      loadingDialog.dismiss();
                      Intent deliveryIntent = new Intent(getContext(),DeliveryActivity.class);
                      startActivity(deliveryIntent);
                  }
              }
          });
       return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        cartAdapter.notifyDataSetChanged();
        if (DBqueries.cartitemModelList.size() == 0){

            DBqueries.cartList.clear();

            DBqueries.loadCartList(getContext(),loadingDialog,true, new TextView(getContext()),totalAmount);
        }
        else {
            if (DBqueries.cartitemModelList.get(DBqueries.cartitemModelList.size()-1).getType() == CartitemModel.TOTAL_AMOUNT){
                LinearLayout parent = (LinearLayout)totalAmount.getParent().getParent();
                parent.setVisibility(View.VISIBLE);
            }


            loadingDialog.dismiss();

        }

    }
}