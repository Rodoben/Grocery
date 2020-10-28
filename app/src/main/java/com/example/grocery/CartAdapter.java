package com.example.grocery;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartAdapter extends RecyclerView.Adapter {
private List<CartitemModel> cartitemModelList;
private int lastPosition = -1;
private TextView cartTotalAmount;
private boolean showDeleteBtn;

    public CartAdapter(List<CartitemModel> cartitemModelList,TextView cartTotalAmount,boolean showDeleteBtn) {
        this.cartitemModelList = cartitemModelList;
        this.cartTotalAmount=cartTotalAmount;
        this.showDeleteBtn=showDeleteBtn;
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
                      boolean inStock = cartitemModelList.get(position).isInStock();
                      long productQunatity = cartitemModelList.get(position).getProductQuantity();
                      long maxQuantity = cartitemModelList.get(position).getMaxQuantity();
                      boolean quantityError= cartitemModelList.get(position).isQtyError();
                      List<String> qtyIds =cartitemModelList.get(position).getQtyIDs();
                     long stockQty = cartitemModelList.get(position).getStockQuantity();
                      ((CartItemViewholder)holder).setItemDetails(productID,resource,title,freeCoupens,productPrice,cuttedPrice,offersApplied,position,inStock,String.valueOf(productQunatity),maxQuantity,quantityError,qtyIds,stockQty);

                      break;
                      case CartitemModel.TOTAL_AMOUNT:
                          String deliveryPrice;
                           int totalAmount;
                           int savedAmount=0;
                      int totalItems=0;
                      int totalItemPrice = 0;


                          for (int x =0;x<cartitemModelList.size();x++){
                              if (cartitemModelList.get(x).getType() == CartitemModel.CART_ITEM && cartitemModelList.get(x).isInStock()){



                                  totalItems++;
                                  totalItemPrice= totalItemPrice+Integer.parseInt(cartitemModelList.get(x).getProductPrice());
                              }

                          }
                          if (totalItemPrice> 500){
                              deliveryPrice="FREE";
                              totalAmount=totalItemPrice;
                          }else {
                              deliveryPrice="30";
                              totalAmount=totalItemPrice + 30;
                          }

                      //  String totalItems = cartitemModelList.get(position).getTotalItems();
                         // String totalItemPrice = cartitemModelList.get(position).getTotalItemPrice();

                         // t = cartitemModelList.get(position).getTotalAmount();
                         // String savedAmount = cartitemModelList.get(position).getSavedAmount();
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
        private LinearLayout coupenRedemtionLayout;

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

          coupenRedemtionLayout = itemView.findViewById(R.id.coupen_redemption_layout);
            deleteBtn = itemView.findViewById(R.id.remove_item_btn);
        }
        private void setItemDetails(final String productID, String resource, String title, long freeCoupensNo, String productPriceText, String cuttedPriceText, long offersAppliedNo, final int position, final boolean inStock, final String quantity, final long maxQuantity, boolean quantityError, final List<String> qtyIds, final long stockQty) {
           // productImage.setImageResource(resource);

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImage);
            productTitle.setText(title);




            if (inStock) {


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



                productPrice.setText("Rs."+productPriceText+"/-");
                productPrice.setTextColor(Color.parseColor("#000000"));

                cuttedPrice.setText("Rs."+cuttedPriceText+"/-");
                coupenRedemtionLayout.setVisibility(View.VISIBLE);

              productQuantity.setText("Qty: "+quantity);
              if (!showDeleteBtn) {
                  if (quantityError) {
                      productQuantity.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
                      productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.red)));

                  } else {
                      productQuantity.setTextColor(itemView.getContext().getResources().getColor(android.R.color.black));
                      productQuantity.setBackgroundTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(android.R.color.black)));

                  }
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
                        quantityNo.setHint("Max "+String.valueOf(maxQuantity));
                        cancelBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                quantityDialog.dismiss();
                            }
                        });

                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                               if (!TextUtils.isEmpty(quantityNo.getText())){
                                if (Long.valueOf(quantityNo.getText().toString()) <= maxQuantity && Long.valueOf(quantityNo.getText().toString()) != 0) {

                                     if (itemView.getContext() instanceof MainActivity){
                                         DBqueries.cartitemModelList.get(position).setProductQuantity(Long.valueOf(quantityNo.getText().toString()));

                                     }else {


                                         if (DeliveryActivity.fromCart) {
                                             DBqueries.cartitemModelList.get(position).setProductQuantity(Long.valueOf(quantityNo.getText().toString()));

                                         } else {
                                             DeliveryActivity.cartitemModelList.get(position).setProductQuantity(Long.valueOf(quantityNo.getText().toString()));
                                         }
                                     }

                                         productQuantity.setText("Qty: " + quantityNo.getText());

                                         if (!showDeleteBtn) {
                                             DeliveryActivity.cartitemModelList.get(position).setQtyError(false);
                                             final int initalQty = Integer.parseInt(quantity);
                                             final int finalQty = Integer.parseInt(quantityNo.getText().toString());
                                             final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                                             if (finalQty > initalQty) {

                                                 for (int y = 0; y < finalQty - initalQty; y++) {

                                                     final String quantityDocumentName = UUID.randomUUID().toString().substring(0, 20);
                                                     Map<String, Object> timeStamp = new HashMap<>();
                                                     timeStamp.put("time", FieldValue.serverTimestamp());

                                                     final int finalY = y;
                                                     firebaseFirestore.collection("PRODUCTS").document(productID).collection("QUANTITY").document(quantityDocumentName).set(timeStamp)
                                                             .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                 @Override
                                                                 public void onSuccess(Void aVoid) {

                                                                    qtyIds.add(quantityDocumentName);
                                                                     if (finalY + 1 == finalQty - initalQty) {

                                                                         firebaseFirestore.collection("PRODUCTS").document(productID).collection("QUANTITY").orderBy("time", Query.Direction.ASCENDING).limit(stockQty).get()
                                                                                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                     @Override
                                                                                     public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                         if (task.isSuccessful()) {

                                                                                             List<String> serverQuantity = new ArrayList<>();
                                                                                             for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {

                                                                                                 serverQuantity.add(queryDocumentSnapshot.getId());

                                                                                             }

                                                                                             long availableQty = 0;

                                                                                             for (String qtyId :qtyIds) {

                                                                                                 if (!serverQuantity.contains(qtyId)) {


                                                                                                        DeliveryActivity.cartitemModelList.get(position).setQtyError(true);
                                                                                                         DeliveryActivity.cartitemModelList.get(position).setMaxQuantity(availableQty);
                                                                                                         Toast.makeText(itemView.getContext(), "All Product may not be available in required quantity....Sorry!", Toast.LENGTH_LONG).show();
                                                                                                         DeliveryActivity.allProductAvailable = false;


                                                                                                 }else {
                                                                                                     availableQty++;
                                                                                                 }


                                                                                             }
                                                                                            DeliveryActivity.cartAdapter.notifyDataSetChanged();
                                                                                         } else {

                                                                                             String error = task.getException().getMessage();
                                                                                             Toast.makeText(itemView.getContext(), error, Toast.LENGTH_LONG).show();
                                                                                         }
                                                                                     }
                                                                                 });

                                                                     }
                                                                 }
                                                             });

                                                 }
                                             }else if (initalQty>finalQty){

                                                 for (int x = 0 ;x< initalQty-finalQty;x++) {
                                                   final String qtyId = qtyIds.get(qtyIds.size() -1 - x);

                                                     firebaseFirestore.collection("PRODUCTS").document(productID).collection("QUANTITY").document(qtyId).delete()
                                                             .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                 @Override
                                                                 public void onSuccess(Void aVoid) {

                                                                     qtyIds.remove(qtyId);
                                                              DeliveryActivity.cartAdapter.notifyDataSetChanged();
                                                                 }
                                                             });


                                                 }
                                             }
                                         }

                                }else {
                                    Toast.makeText(itemView.getContext(),"Maximum quantity :"+maxQuantity,Toast.LENGTH_LONG).show();

                                }

                               }
                                quantityDialog.dismiss();


                            }
                        });
                        quantityDialog.show();
                    }
                });
                if (offersAppliedNo>0){
                    offersApplied.setVisibility(View.VISIBLE);
                    offersApplied.setText(offersAppliedNo+" Offers Applied");
                }else {
                    offersApplied.setVisibility(View.INVISIBLE);
                }


            }else {
                productPrice.setText("OUT OF STOCK");
                productPrice.setTextColor(itemView.getResources().getColor(R.color.red));
                cuttedPrice.setText("");
                coupenRedemtionLayout.setVisibility(View.GONE);
                freeCoupens.setVisibility(View.INVISIBLE);
               productQuantity.setVisibility(View.INVISIBLE);
               coupensApplied.setVisibility(View.GONE);
               offersApplied.setVisibility(View.GONE);
               freeCoupensIcon.setVisibility(View.INVISIBLE);
            }




         if (showDeleteBtn){
             deleteBtn.setVisibility(View.VISIBLE);
         }else {
             deleteBtn.setVisibility(View.GONE);
         }

         deleteBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if (!ProductDetailsActivity.running_cartQuery){

                     ProductDetailsActivity.running_cartQuery=true;
                     DBqueries.removeFromCart(position,itemView.getContext(),cartTotalAmount);
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

         private void setTotalAmount(int totalItemText,int totalItemPriceText,String deliveryPriceText,int totalAmountText,int savedAmountText){

             totalItems.setText("Price("+totalItemText+"items");
             totalItemPrice.setText("Rs."+totalItemPriceText+"/-");
            if (deliveryPriceText.equals("FREE")){
                deliveryPrice.setText(deliveryPriceText);
            }else {
                deliveryPrice.setText("Rs."+deliveryPriceText+"/-");
            }

            // deliveryPrice.setText(deliveryPriceText);
             totalAmount.setText("Rs."+totalAmountText+"/-");
            cartTotalAmount.setText("Rs."+totalAmountText+"/-");
             savedAmount.setText("You saved Rs. "+savedAmountText+" on this order");
             LinearLayout parent = (LinearLayout)cartTotalAmount.getParent().getParent();
             if (totalItemPriceText == 0){
                 if (DeliveryActivity.fromCart) {
                     DBqueries.cartitemModelList.remove(DBqueries.cartitemModelList.size() - 1);
                     DeliveryActivity.cartitemModelList.remove(DBqueries.cartitemModelList.size() - 1);
                 }
                 if (showDeleteBtn){
                     DBqueries.cartitemModelList.remove(DBqueries.cartitemModelList.size() - 1);
                 }
                 parent.setVisibility(View.GONE);
             }else {
                 parent.setVisibility(View.VISIBLE);
             }

         }
     }

}
