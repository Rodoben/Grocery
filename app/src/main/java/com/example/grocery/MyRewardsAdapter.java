package com.example.grocery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.Viewholder> {

    private List<RewardModel> rewardModelList;
    private Boolean useminiLayout = false;

    public MyRewardsAdapter(List<RewardModel> rewardModelList,Boolean useminiLayout) {
        this.rewardModelList = rewardModelList;
        this.useminiLayout = useminiLayout;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
      if (useminiLayout){
           view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout,parent,false);
      }else {

          view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout, parent, false);

      }
        return  new Viewholder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
       String title=rewardModelList.get(position).getTitle();
       String date = rewardModelList.get(position).getExpiryDate();
       String body = rewardModelList.get(position).getCoupenBody();
       holder.setDate(title,date,body);

    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView coupenTitle,coupenExpiryDate,coupenBody;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            coupenTitle = itemView.findViewById(R.id.coupen_title);
            coupenExpiryDate = itemView.findViewById(R.id.coupen_validity);
            coupenBody = itemView.findViewById(R.id.coupen_body);

        }

        private void setDate(final String title, final String date, final String body){
            coupenTitle.setText(title);
            coupenExpiryDate.setText(date);
            coupenBody.setText(body);

            if (useminiLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                ProductDetailsActivity.coupenTitle.setText(title);
                ProductDetailsActivity.coupenExpiryDate.setText(date);
                ProductDetailsActivity.coupenBody.setText(body);
                ProductDetailsActivity.showDialogRecyclerView();
                    }
                });
            }


        }
    }
}
