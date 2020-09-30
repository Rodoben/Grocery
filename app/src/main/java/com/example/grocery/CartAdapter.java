package com.example.grocery;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {
private List<CartitemModel> cartitemModelList;
private int lastPosition = -1;

    public CartAdapter(List<CartitemModel> cartitemModelList) {
        this.cartitemModelList = cartitemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartitemModelList.get(position).getType()){
            case 0:
                return CartitemModel.CART_ITEM;
            case 1:
                return CartitemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       switch (viewType){

           case CartitemModel.CART_ITEM:
               View cartitemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
               return new CartItemViewholder(cartitemView);

               case CartitemModel.TOTAL_AMOUNT:
                   View cartTotalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout,parent,false);
                   return new CartTotalAmountViewholder(cartTotalView);
           default:
               return null;
       }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
              switch (cartitemModelList.get(position).getType()){

                  case CartitemModel.CART_ITEM:
                      String productID=cartitemModelList.get(position).getProductID();
                      String resource = cartitemModelList.get(position).getProductImage();
                      String title= cartitemModelList.get(position).getProductTitle();
                      long freeCoupens = cartitemModelList.get(position).getFreecoupens();
                      String productPrice = cartitemModelList.get(position).getProductPrice();
                      String cuttedPrice= cartitemModelList.get(position).getCuttedPrice();
                      long offersApplied = cartitemModelList.get(position).getOffersApplied();
                      ((CartItemViewholder)holder).setItemDetails(productID,resource,title,freeCoupens,productPrice,cuttedPrice,offersApplied,position);

                      break;
                      case CartitemModel.TOTAL_AMOUNT:
                        String totalItems = cartitemModelList.get(position).getTotalItems();
                          String totalItemPrice = cartitemModelList.get(position).getTotalItemPrice();
                          String deliveryPrice = cartitemModelList.get(position).getDeliveryPrice();
                          String totalAmount = cartitemModelList.get(position).getTotalAmount();
                          String savedAmount = cartitemModelList.get(position).getSavedAmount();
                          ((CartTotalAmountViewholder)holder).setTotalAmount(totalItems,totalItemPrice,deliveryPrice,totalAmount,savedAmount);
                          break;
                  default:
                      return;
              }
        if (lastPosition < position) {

            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition=position;

        }
    }

    @Override
    public int getItemCount() {
        return cartitemModelList.size();
    }
    class CartItemViewholder extends RecyclerView.ViewHolder{

        private ImageView productImage,freeCoupensIcon;
        private TextView productTitle,freeCoupens,productPrice,cuttedPrice,offersApplied,coupensApplied,productQuantity;
        private LinearLayout deleteBtn;

        public CartItemViewholder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupensIcon = itemView.findViewById(R.id.free_coupen_icon);
            freeCoupens = itemView.findViewById(R.id.tv_free_coupen);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            offersApplied = itemView.findViewById(R.id.offers_applied);
            coupensApplied = itemView.findViewById(R.id.coupens_applied);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
        }
        private void setItemDetails(String productID, String resource, String title, long freeCoupensNo, String productPriceText, String cuttedPriceText, long offersAppliedNo, final int position) {
           // productImage.setImageResource(resource);

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImage);
            productTitle.setText(title);
            if (freeCoupensNo > 0) {
                if (freeCoupensNo == 1) {
                    freeCoupensIcon.setVisibility(View.VISIBLE);
                    freeCoupens.setVisibility(View.VISIBLE);
                    freeCoupens.setText("free " + freeCoupensNo + " coupen");
                } else {
                    freeCoupens.setText("free " + freeCoupensNo + " coupens");
                }
            } else {
                freeCoupensIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);
            }

            productPrice.setText(productPriceText);
            cuttedPrice.setText(cuttedPriceText);
         if (offersAppliedNo>0){
             offersApplied.setVisibility(View.VISIBLE);
             offersApplied.setText(offersAppliedNo+" Offers Applied");
         }else {
             offersApplied.setVisibility(View.INVISIBLE);
         }
         productQuantity.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 final Dialog quantityDialog = new Dialog(itemView.getContext());
                 quantityDialog.setContentView(R.layout.quantity_dialog);

                 quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                 quantityDialog.setCancelable(false);

                 final EditText quantityNo= quantityDialog.findViewById(R.id.quantity_no);
                 Button cancelBtn=quantityDialog.findViewById(R.id.cancel_Btn);
                 Button okBtn = quantityDialog.findViewById(R.id.ok_Btn);

                 cancelBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                             quantityDialog.dismiss();
                     }
                 });

                 okBtn.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         productQuantity.setText("Qty: "+quantityNo.getText());
                          quantityDialog.dismiss();

                     }
                 });
                         quantityDialog.show();
             }
         });

         deleteBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (!ProductDetailsActivity.running_cartQuery){

                     ProductDetailsActivity.running_cartQuery=true;
                     DBqueries.removeFromCart(position,itemView.getContext());
                 }
             }
         });

        }
    }


     class CartTotalAmountViewholder extends RecyclerView.ViewHolder{
     private TextView totalItems,totalItemPrice,deliveryPrice,totalAmount,savedAmount;

         public CartTotalAmountViewholder(@NonNull View itemView) {
             super(itemView);

             totalItems = itemView.findViewById(R.id.total_items);
             totalItemPrice= itemView.findViewById(R.id.total_items_price);
             deliveryPrice = itemView.findViewById(R.id.delivery_price);
             totalAmount = itemView.findViewById(R.id.total_price);
             savedAmount= itemView.findViewById(R.id.saved_amount);

         }

         private void setTotalAmount(String totalItemText,String totalItemPriceText,String deliveryPriceText,String totalAmountText,String savedAmountText){

             totalItems.setText(totalItemText);
             totalItemPrice.setText(totalItemPriceText);
             deliveryPrice.setText(deliveryPriceText);
             totalAmount.setText(totalAmountText);
             savedAmount.setText(savedAmountText);
         }
     }

}
