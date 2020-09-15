package com.example.grocery;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {

  private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder holder, int position) {
     String resource = horizontalProductScrollModelList.get(position).getProduct_Image();
     String title = horizontalProductScrollModelList.get(position).getProductTitle();
        String desc = horizontalProductScrollModelList.get(position).getProductDesc();
        String price = horizontalProductScrollModelList.get(position).getProductPrice();
        holder.setProductImage(resource);
        holder.setProductTitle(title);
        holder.setProductDesc(desc);
        holder.setProductPrice(price);
    }

    @Override
    public int getItemCount() {

        if(horizontalProductScrollModelList.size()>8){
            return  8;
        }else{
            return horizontalProductScrollModelList.size();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle,productDesc,productPrice;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage= itemView.findViewById(R.id.imageView4);
            productTitle= itemView.findViewById(R.id.textView7);
            productDesc=itemView.findViewById(R.id.textView8);
            productPrice = itemView.findViewById(R.id.textView9);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent  = new Intent (itemView.getContext(),ProductDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });

        }
        private void setProductImage(String resource){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.banner1)).into(productImage);
        }
        private void setProductTitle(String title){
            productTitle.setText(title);
        }
        private  void setProductDesc(String desc){
            productDesc.setText(desc);
        }
        private void setProductPrice(String price){
            productPrice.setText(price);
        }
    }
}
