package com.example.grocery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.channels.SelectableChannel;
import java.util.List;

import static com.example.grocery.DeliveryActivity.SELECT_ADDRESS;
import static com.example.grocery.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.grocery.MyAddressesActivity.refreshItem;


public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.Viewholder>{

    private List<AddressesModel> addressesModelList;
    private int MODE;
    private int preSelectedPosition;

    public AddressesAdapter(List<AddressesModel> addressesModelList,int MODE) {
        this.addressesModelList = addressesModelList;
        this.MODE=MODE;
        preSelectedPosition =DBqueries.selectedAddress;
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
        Boolean selected = addressesModelList.get(position).getSelected();
      holder.setData(name,address,pincode,selected,position);
    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView fullname,address,pincode;
       private ImageView icon;
       private LinearLayout optionContainer;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            pincode = itemView.findViewById(R.id.pincode);
            icon=itemView.findViewById(R.id.icon_view);
            optionContainer = itemView.findViewById(R.id.option_container);
        }
        private void setData(String username, String userAddress, String userPincode, boolean selected , final int position){
            fullname.setText(username);
            address.setText(userAddress);
            pincode.setText(userPincode);

            if (MODE == SELECT_ADDRESS){

                icon.setImageResource(R.drawable.ic_baseline_check_24);
                  if (selected){
                      icon.setVisibility(View.VISIBLE);
                      preSelectedPosition=position;
                  }else {
                      icon.setVisibility(View.GONE);
                  }
                  itemView.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {

                          if (preSelectedPosition != position) {
                              addressesModelList.get(position).setSelected(true);
                              addressesModelList.get(preSelectedPosition).setSelected(false);
                              refreshItem(preSelectedPosition, position);
                              preSelectedPosition = position;
                              DBqueries.selectedAddress = position;
                          }
                      }
                  });

            }else if(MODE == MANAGE_ADDRESS){

                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.drawable.ic_baseline_more_vert_24);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition = position;
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshItem(preSelectedPosition,preSelectedPosition);
                        preSelectedPosition=-1;
                    }
                });

            }

        }
    }
}
