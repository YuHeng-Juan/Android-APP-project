package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantOrderActivity extends AppCompatActivity {


    private String UsernameStr;
    private String DishnameStr;
    private String PriceStr;
    private String[] StringSpiltToList;
    ActionBar ab;


    private ArrayList<String> mUsername = new ArrayList<>();
    private ArrayList<String> mDishname = new ArrayList<>();
    private ArrayList<Integer> mPrice = new ArrayList<>();
    private RecyclerView mRvRestorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ab = getSupportActionBar();
        ab.setTitle("");

        Intent intent = getIntent();

        UsernameStr = intent.getStringExtra("username");
        if(UsernameStr.length()!=0) {
            StringSpiltToList = UsernameStr.split(",");
            for (int i = 0; i < StringSpiltToList.length; i++) {
                mUsername.add(StringSpiltToList[i]);
            }
        }

        DishnameStr = intent.getStringExtra("dishname");//從上一頁取道ID
        if(DishnameStr.length()!=0) {
            StringSpiltToList = DishnameStr.split(",");
            for (int i = 0; i < StringSpiltToList.length; i++) {
                mDishname.add(StringSpiltToList[i]);
            }
        }
        PriceStr = intent.getStringExtra("price");
        if(PriceStr.length()!=0) {
            StringSpiltToList = PriceStr.split(",");
            for (int i = 0; i < StringSpiltToList.length; i++) {
                mPrice.add(Integer.valueOf(StringSpiltToList[i]));
            }
        }
//        for(int i = 0; i < 50; i++) {
//            mUsername.add("人名"+i);
//        }
//        for(int i = 0; i < 50; i++) {
//            mDishname.add("菜名"+i);
//        }
//        for(int i = 0; i < 50; i++) {
//            mPrice.add(i+100);
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_order);

        final Button mbtn_restorder_exit;
        mbtn_restorder_exit = findViewById(R.id.btn_restorder_exit);
        mbtn_restorder_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRvRestorder = (RecyclerView) findViewById(R.id.rv_restorder);
        mRvRestorder.setLayoutManager(new LinearLayoutManager(RestaurantOrderActivity.this));
        mRvRestorder.setAdapter(new RestaurantOrderAdapter(RestaurantOrderActivity.this,mUsername,mDishname,mPrice));

    }
}
