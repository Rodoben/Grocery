package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MyAddressesActivity extends AppCompatActivity {

    private RecyclerView myAddresesRecyclerView;

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddresesRecyclerView.setLayoutManager(layoutManager);
        List <AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));
        addressesModelList.add(new AddressesModel("Astam","jasidih bijli office fotokix","814112"));

        AddressesAdapter addressesAdapter = new AddressesAdapter(addressesModelList);
        myAddresesRecyclerView.setAdapter(addressesAdapter);
        addressesAdapter.notifyDataSetChanged();



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