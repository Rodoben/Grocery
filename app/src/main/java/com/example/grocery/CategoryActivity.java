package com.example.grocery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.grocery.DBqueries.lists;
import static com.example.grocery.DBqueries.loadCategories;
import static com.example.grocery.DBqueries.loadFragmentData;
import static com.example.grocery.DBqueries.loadedCategoriesnames;

public class CategoryActivity extends AppCompatActivity {
  private RecyclerView categoryRecyclerView;
    HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String  title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      categoryRecyclerView = findViewById(R.id.category_recycler_view);



        LinearLayoutManager testingLayoutManager=new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);
           int listPosition =0;
        for(int x=0;x<loadedCategoriesnames.size();x++) {
            if (loadedCategoriesnames.get(x).equals(title.toUpperCase())) {
                listPosition = x;
            }
        }
                if (listPosition ==0){
                    loadedCategoriesnames.add("HOME");
                    lists.add(new ArrayList<HomePageModel>());
                    adapter = new HomePageAdapter(lists.get(loadedCategoriesnames.size()-1));
                    loadFragmentData(adapter,this,loadedCategoriesnames.size()-1,title);
                }else {

                    adapter = new HomePageAdapter(lists.get(listPosition));
                }



        categoryRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            return true;

        } else if(id == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}