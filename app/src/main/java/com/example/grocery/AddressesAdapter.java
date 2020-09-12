package com.example.grocery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.Viewholder>{

    private List<AddressesModel> addressesModelList;

    public AddressesAdapter(List<AddressesModel> addressesModelList) {
        this.addressesModelList = addressesModelList;
    }


    @NonNull
    @Override
    public AddressesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresses_item_layout,parent,false);

        return  new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.Viewholder holder, int position) {
      String name = addressesModelList.get(position).getFullname();
      String address= addressesModelList.get(position).getAddess();
      String pincode = addressesModelList.get(position).getPincode();
      holder.setData(name,address,pincode);
    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView fullname,address,pincode;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pincode);
        }
        private void setData(String username,String userAddress,String userPincode ){
            fullname.setText(username);
            address.setText(userAddress);
            pincode.setText(userPincode);
        }
    }
}
