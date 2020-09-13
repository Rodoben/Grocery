package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.grocery.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {

    private RecyclerView myAddresesRecyclerView;
    private Button deliverHereBtn;
    private static AddressesAdapter addressesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        myAddresesRecyclerView = findViewById(R.id.addresses_recyclerView);
       deliverHereBtn = findViewById(R.id.deliver_here_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddresesRecyclerView.setLayoutManager(layoutManager);
        List <AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",true));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",false));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",false));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",false));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",false));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",false));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",false));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",false));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",false));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112",false));
        int mode = getIntent().getIntExtra("MODE",-1);

        if (mode == SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else {
            deliverHereBtn.setVisibility(View.GONE);
        }

        addressesAdapter = new AddressesAdapter(addressesModelList,mode);
        myAddresesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddresesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();



    }
    public static void refreshItem(int deSelect,int select){
        addressesAdapter.notifyItemChanged(deSelect);
        addressesAdapter.notifyItemChanged(select);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}