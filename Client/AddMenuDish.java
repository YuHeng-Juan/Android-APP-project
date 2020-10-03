package com.example.myapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.PrintWriter;
import java.net.Socket;



public class AddMenuDish extends AppCompatActivity {
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        ab = getSupportActionBar();
        ab.setTitle("");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_dish);
        final String Restname;
        final Button mbtn_addmenudish_exit;
        final Button mbtn_addmenudish_submit;
        final EditText mdishname;
        final EditText mprice;
        final TextView mrestname;
        mbtn_addmenudish_exit = findViewById(R.id.add_menu_btn_exit);
        mbtn_addmenudish_submit = findViewById(R.id.add_menu_btn_submit);
        mrestname = findViewById(R.id.add_menu_restname);
        mdishname = findViewById(R.id.add_menu_ET_dishname);
        mprice = findViewById(R.id.add_menu_ET_price);
        final Intent intent = getIntent();
        Restname = intent.getStringExtra("Restname");

        mrestname.setText("新增餐點至"+Restname);

        mbtn_addmenudish_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mbtn_addmenudish_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t = new AddMenuDish.thread(Restname,String.valueOf(mdishname.getText()),String.valueOf(mprice.getText()));
                t.start();
                finish();
            }
        });

    }
    class thread extends Thread{ //傳送的執行續
        String restame;
        String dishname;
        String price;
        private PrintWriter out;
        public thread(String restame,String dishname,String price){
            this.restame = restame;
            this.dishname = dishname;
            this.price = price;
        }

        public void run(){
            Socket socket = null;
            try{
                socket = ((MySocket)getApplication()).getSocket();//必須輸入區域網路的IP 不能輸入127.0.0.1
//                if(socket.isConnected()){
//                    //Log.d("Jimmy","Success");
//                }
                out = new PrintWriter(socket.getOutputStream(),true);
                out.println("addDish");
                out.println(restame);
                out.println(dishname);
                out.println(price);
            }catch (Exception e){
                Log.d("Jimmy","Error");
            }
        }
    }
}
