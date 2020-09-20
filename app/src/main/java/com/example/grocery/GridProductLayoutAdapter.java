package com.example.grocery;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {
    List<HorizontalProductScrollModel> horizontalProductScrollModelList ;

    public GridProductLayoutAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        View view1;
        if (view == null){
            view1= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
           view1.setElevation(0);
           view1.setBackgroundColor(Color.parseColor("#FFFFFF"));
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent = new Intent(viewGroup.getContext(),ProductDetailsActivity.class);
                    productDetailsIntent.putExtra("PRODUCT_ID",horizontalProductScrollModelList.get(i).getProductId());
                viewGroup.getContext().startActivity(productDetailsIntent);
                }
            });
            ImageView productImage=view1.findViewById(R.id.imageView4);
            TextView productTitle = view1.findViewById(R.id.textView7);
            TextView productDesc = view1.findViewById(R.id.textView8);
            TextView productPrice = view1.findViewById(R.id.textView9);

            //Glide.with(horizontalProductScrollModelList.get(i).getProduct_Image());
            Glide.with(viewGroup.getContext()).load(horizontalProductScrollModelList.get(i).getProduct_Image()).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImage);
            productTitle.setText(horizontalProductScrollModelList.get(i).getProductTitle());
            productDesc.setText(horizontalProductScrollModelList.get(i).getProductDesc());
            productPrice.setText("Rs."+horizontalProductScrollModelList.get(i).getProductPrice()+"/-");


        }else {
          view1 = view;
        }
        return view1;
    }
}
