package com.example.grocery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.Viewholder> {

    private List<RewardModel> rewardModelList;
    private Boolean useminiLayout = false;
    private RecyclerView coupensRecyclerView;
    private LinearLayout selectedCoupen;
    private String productOriginalPrice;
   private TextView coupenTitle;
   private  TextView coupenExpiryDate;
   private TextView coupenBody;
   private TextView discountedPrice;

    public MyRewardsAdapter(List<RewardModel> rewardModelList, Boolean useminiLayout) {
        this.rewardModelList = rewardModelList;
        this.useminiLayout = useminiLayout;
    }

    public MyRewardsAdapter(List<RewardModel> rewardModelList, Boolean useminiLayout, RecyclerView coupensRecyclerView, LinearLayout selectedCoupen, String productOriginalPrice, TextView coupenTitle, TextView coupenExpiryDate, TextView coupenBody,TextView discountedPrice) {
        this.rewardModelList = rewardModelList;
        this.useminiLayout = useminiLayout;
        this.coupensRecyclerView = coupensRecyclerView;
        this.selectedCoupen = selectedCoupen;
        this.productOriginalPrice = productOriginalPrice;
        this.coupenTitle = coupenTitle;
        this.coupenExpiryDate = coupenExpiryDate;
        this.coupenBody = coupenBody;
        this.discountedPrice = discountedPrice;
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
       String type=rewardModelList.get(position).getType();
       Date validity = rewardModelList.get(position).getTimestamp();
       String body = rewardModelList.get(position).getCoupenBody();
       String lowerLimit = rewardModelList.get(position).getLowerLimit();
       String upperLimit = rewardModelList.get(position).getUpperLimit();
       String discOramt = rewardModelList.get(position).getdiscORamt();

       holder.setDate(type,validity,body,lowerLimit,upperLimit,discOramt);

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

        private void setDate(final String type, final Date validity, final String body,  final String lowerLimit, final String upperLimit,final String  discORamt){
            if (type.equals("Discount")){
                coupenTitle.setText(type);

            }else {
                coupenTitle.setText("FLAT Rs."+discORamt+" OFF");
            }
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM/YYYY");
            coupenExpiryDate.setText(simpleDateFormat.format(validity));
            coupenBody.setText(body);

            if (useminiLayout){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
              coupenTitle.setText(type);
                coupenExpiryDate.setText(simpleDateFormat.format(validity));
                coupenBody.setText(body);

                if (Long.valueOf(productOriginalPrice)>Long.valueOf(lowerLimit) && Long.valueOf(productOriginalPrice)<Long.valueOf(upperLimit)){

                     if (type.equals("Discount")){
                         Long discountAmount = Long.valueOf(productOriginalPrice)*Long.valueOf(discORamt)/100;
                         discountedPrice.setText("Rs."+String.valueOf(Long.valueOf(productOriginalPrice)-discountAmount)+"/-");
                     }else {
                         discountedPrice.setText("Rs."+String.valueOf(Long.valueOf(productOriginalPrice)-Long.valueOf(discORamt))+"/-");

                     }

                }else {

                    discountedPrice.setText("Invalid");
                    Toast.makeText(itemView.getContext(),"Sorry! Product does not matches the coupen terms.",Toast.LENGTH_LONG).show();

                }

                        if (coupensRecyclerView.getVisibility() == View.GONE)
                        {
                            coupensRecyclerView.setVisibility(View.VISIBLE);
                            selectedCoupen.setVisibility(View.GONE);
                        }else {
                            coupensRecyclerView.setVisibility(View.GONE);
                            selectedCoupen.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }


        }
    }
}
