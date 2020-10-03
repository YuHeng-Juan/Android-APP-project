package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;

public class RestaurantMenuActivity extends AppCompatActivity {

    SparseBooleanArray SelectedPositions = new SparseBooleanArray();
    private ArrayList<String> mDishname = new ArrayList<>();
    private ArrayList<Integer> mPrice = new ArrayList<>();
    private ArrayList<String> OrderDishname = new ArrayList<>();
    private ArrayList<String> OrderPrice = new ArrayList<>();
    private String DishnameStr;
    private String PriceStr;
    private String Username;
    private String Restname;
    private RestaurantMenuAdapter adapter;
    private String[] StringSpiltToList;
    private ArrayList<String> boolStringList = new ArrayList<>();
    private String boolString;
    Integer i;
    private RecyclerView mRvRestmenu;
    private ArrayList<String>OrderUsernameUse;
    private ArrayList<String>OrderDishnameUse;
    private ArrayList<String>OrderPriceUse;
    ActionBar ab;



    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    //呼叫將選擇的Checkbox設為被被選中會不選中
    private void setItemChecked(int position, boolean isChecked) {
        SelectedPositions.put(position, isChecked);
    }
    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return SelectedPositions.get(position);
    }
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent intent = getIntent();
        OrderUsernameUse = RestaurantMenuAdapter.OrderUsername;
        OrderDishnameUse = RestaurantMenuAdapter.OrderDishname;
        OrderPriceUse = RestaurantMenuAdapter.OrderPrice;
        OrderUsernameUse.clear();
        OrderDishnameUse.clear();
        OrderPriceUse.clear();
        ab = getSupportActionBar();
        ab.setTitle("");


        Username = intent.getStringExtra("Username");
        DishnameStr = intent.getStringExtra("dishname");//從上一頁取道ID
        Restname = intent.getStringExtra("restname");//從上一頁取道ID
        PriceStr = intent.getStringExtra("price");

        if(DishnameStr.length()!=0) {
            StringSpiltToList = DishnameStr.split(",");
            for (int i = 0; i < StringSpiltToList.length; i++) {
                mDishname.add(StringSpiltToList[i]);
            }
        }
        if(PriceStr.length()!=0) {
            StringSpiltToList = PriceStr.split(",");
            for (int i = 0; i < StringSpiltToList.length; i++) {
                mPrice.add(Integer.valueOf(StringSpiltToList[i]));
            }
        }


//        for(int i = 0; i < 50; i++) {
//            mDishname.add("菜名"+i);
//        }
//        for(int i = 0; i < 50; i++) {
//            mPrice.add(i+100);
//        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);
        final Button mbtn_restmenu_exit;
        final Button mbtn_restmenu_add;
        final Button mbtn_restmenu_accept;
        final TextView title;
        mbtn_restmenu_exit = findViewById(R.id.btn_restmenu_exit);
        mbtn_restmenu_add = findViewById(R.id.btn_restmenu_add);
        mbtn_restmenu_accept = findViewById(R.id.btn_restmenu_accept);
        title = findViewById(R.id.tv_restmenu_title);
        title.setText(Restname+"的菜單");
        mbtn_restmenu_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderUsernameUse.clear();
                OrderDishnameUse.clear();
                OrderPriceUse.clear();
                finish();
            }
        });
        mbtn_restmenu_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_intent = new Intent(RestaurantMenuActivity.this,AddMenuDish.class);
                add_intent.putExtra("Restname",Restname);
                startActivity(add_intent);
                finish();
            }
        });
        mbtn_restmenu_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("1"+OrderUsernameUse);
                System.out.println("2"+OrderDishnameUse);
                System.out.println("3"+OrderPriceUse);

                Thread t = new RestaurantMenuActivity.thread(OrderUsernameUse,OrderDishnameUse,OrderPriceUse);//傳送位置訊息
                t.start();
                finish();
            }
        });
        mRvRestmenu = (RecyclerView) findViewById(R.id.rv_restmenu);
        mRvRestmenu.setLayoutManager(new LinearLayoutManager(RestaurantMenuActivity.this));
        mRvRestmenu.setAdapter(new RestaurantMenuAdapter(RestaurantMenuActivity.this,mDishname,mPrice));
    }
    class thread extends Thread{ //傳送的執行續
        private ArrayList<String> OrderUsernameUse;
        private ArrayList<String> OrderDishnameUse;
        private ArrayList<String> OrderPriceUse;
        private PrintWriter out;
        public thread(ArrayList<String> OrderUsernameUse,ArrayList<String> OrderDishnameUse,ArrayList<String> OrderPriceUse){
            this.OrderUsernameUse = OrderUsernameUse;
            this.OrderDishnameUse = OrderDishnameUse;
            this.OrderPriceUse = OrderPriceUse;
        }

        public void run(){
            Socket socket = null;
            try{
                socket = ((MySocket)getApplication()).getSocket();//必須輸入區域網路的IP 不能輸入127.0.0.1
//                if(socket.isConnected()){
//                    //Log.d("Jimmy","Success");
//                }
                out = new PrintWriter(socket.getOutputStream(),true);
                out.println("orderadd");
                String msg;
                msg = String.join(",", OrderUsernameUse);
                out.println(msg);
                msg = String.join(",", OrderDishnameUse);
                out.println(msg);
                msg = String.join(",", OrderPriceUse);
                out.println(msg);

            }catch (Exception e){
                Log.d("Jimmy","Error");
            }
        }
    }
}
