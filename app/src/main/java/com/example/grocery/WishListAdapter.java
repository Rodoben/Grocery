package com.example.grocery;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {

    private List<WishListModel> wishListModelList;
    private boolean wishlist;

    public WishListAdapter(List<WishListModel> wishListModelList,boolean wishlist) {
        this.wishListModelList = wishListModelList;
        this.wishlist=wishlist;
    }


    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item_layout,parent,false);
       return  new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
      String resource = wishListModelList.get(position).getProductImage();
      String title = wishListModelList.get(position).getProductTitle();
      long freeCoupens = wishListModelList.get(position).getFreeCoupens();
      String rating = wishListModelList.get(position).getRating();
      long totalRatings = wishListModelList.get(position).getTotalRatings();
      String productPrice = wishListModelList.get(position).getProductPrice();
      String cuttedPrice = wishListModelList.get(position).getCuttedPrice();
      boolean COD = wishListModelList.get(position).getCOD();

        holder.setData(resource,title,freeCoupens,rating,totalRatings,productPrice,cuttedPrice,COD);

    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupens;
        private ImageView coupenIcon;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private TextView rating;
        private TextView totalRaings;
        private  View priceCut;
        private ImageButton deleteBtn;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            freeCoupens = itemView.findViewById(R.id.free_coupen);
            coupenIcon = itemView.findViewById(R.id.coupen_icon);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRaings = itemView.findViewById(R.id.total_ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            paymentMethod = itemView.findViewById(R.id.payment_method);
            deleteBtn = itemView.findViewById(R.id.delete_button);
        }
        private void setData(String resource, String title, long freeCoupensNo, String averageRate, long totalRatingsNo, String price, String cuttedPriceValue, boolean cod){


            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.banner1)).into(productImage);
            productTitle.setText(title);
            if (freeCoupensNo!=0) {
          coupenIcon.setVisibility(View.VISIBLE);
                if (freeCoupensNo == 1) {

                    freeCoupens.setText("free" + freeCoupensNo + "coupen");
                } else {
                    freeCoupens.setText("free" + freeCoupensNo + "coupens");
                }
            }else{
                coupenIcon.setVisibility(View.INVISIBLE);
                freeCoupens.setVisibility(View.INVISIBLE);

            }
            rating.setText(averageRate);
            totalRaings.setText("("+totalRatingsNo+"ratings)");
            productPrice.setText("Rs."+price+"/-");
            cuttedPrice.setText("Rs."+cuttedPriceValue+"/-");
            if (cod){
                paymentMethod.setVisibility(View.VISIBLE);

            }else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }

            if (wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(),"delete",Toast.LENGTH_LONG).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ProductDetailsIntent =  new Intent(itemView.getContext(),ProductDetailsActivity.class);
                    itemView.getContext().startActivity(ProductDetailsIntent);
                }
            });

        }
    }
}
