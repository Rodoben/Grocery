package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
   private RecyclerView deliveryRecyclerView;
   private Button changeOrAddnewAdressBtn;
   public static final int SELECT_ADDRESS = 0;
   private TextView totalAmount;
   private TextView fullname;
   private TextView fullAddress;
   private TextView pincode;
   private Button continueBtn;
   private Dialog loadingDialog;
   private ImageButton paytm;
    private Dialog paymentmethodDialog;
   public static List<CartitemModel> cartitemModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

       Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        // lloading dialog
        loadingDialog = new Dialog(DeliveryActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //loadingDialog.show();

        // lloading dialog
        paymentmethodDialog = new Dialog(DeliveryActivity.this);
        paymentmethodDialog.setContentView(R.layout.payment_method);
        paymentmethodDialog.setCancelable(true);
        paymentmethodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        paymentmethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //loadingDialog.show();

        deliveryRecyclerView =findViewById(R.id.delivery_recycler_view);
        changeOrAddnewAdressBtn=findViewById(R.id.change_or_add_address_btn);
       totalAmount= findViewById(R.id.total_cart_amount);
       fullname = findViewById(R.id.fullname);
       fullAddress=findViewById(R.id.address);
       pincode = findViewById(R.id.pincode);
       continueBtn = findViewById(R.id.cart_continue_btn);



       LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);

      //  List<CartitemModel> cartitemModelList = new ArrayList<>();

       // cartitemModelList.add(new CartitemModel(1,"Price (3 items)","Rs.356654/-","free","Rs550000/-","Rs.500/-"));

        CartAdapter cartAdapter = new CartAdapter(cartitemModelList,totalAmount,false);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddnewAdressBtn.setVisibility(View.VISIBLE);
        changeOrAddnewAdressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAddressesIntent = new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                  myAddressesIntent.putExtra("MODE",SELECT_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            paymentmethodDialog.show();
                paytm = paymentmethodDialog.findViewById(R.id.paytm_btn);


                paytm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        paymentmethodDialog.dismiss();
                       // loadingDialog.show();

                        if (ContextCompat.checkSelfPermission(DeliveryActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(DeliveryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);


                        }

                    }
                });

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        fullname.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getFullname());
        fullAddress.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getAddess());
        pincode.setText(DBqueries.addressesModelList.get(DBqueries.selectedAddress).getPincode());

        super.onStart();
    }
}