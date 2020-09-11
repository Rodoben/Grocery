package com.example.grocery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MyRewardsFragment extends Fragment {


    private RecyclerView rewardsRecyclerview;
    public MyRewardsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_rewards, container, false);
        rewardsRecyclerview = view.findViewById(R.id.my_rewards_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardsRecyclerview.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("cashback","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("cashback","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("Buy one Get 1 free","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("cashback","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("Discount","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));
        rewardModelList.add(new RewardModel("cashback","till 2nd,June 2020","Get 20% off on mohanbhog sweets above rs 200"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList);
        rewardsRecyclerview.setAdapter(myRewardsAdapter);
         myRewardsAdapter.notifyDataSetChanged();
     return view;
    }
}