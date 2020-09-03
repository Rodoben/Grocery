package com.example.grocery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }


    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
     String icon = categoryModelList.get(position).getCategoryIconLink();
     String name = categoryModelList.get(position).getCategoryName();
      holder.setCategoryname(name);

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView categoryIcon;
        private TextView categoryname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryIcon=itemView.findViewById(R.id.imageView3);
            categoryname=itemView.findViewById(R.id.textView6);
        }

        private void setCategoryIcon(){
            //todo: set category icons here
        }
        private void setCategoryname(String name){
            categoryname.setText(name);
        }
    }
}
