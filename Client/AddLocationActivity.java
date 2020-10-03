package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class AddLocationActivity extends AppCompatActivity {
    Integer SelectMode = 0;
    ActionBar ab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);
        Intent intent = getIntent();
        final Button mbtn_addlocation_exit;
        final Button mbtn_addlocation_submit;

        final EditText mname;
        final EditText mlatitude;
        final EditText mlongitude;
        RadioButton mRBrestaurant;
        ab = getSupportActionBar();
        ab.setTitle("");
        RadioButton mRBlocation;
//        add_location_radio_button_restaurant
//        add_location_radio_button_location
        double Lon=0;
        double Lat=0;
        mname = findViewById(R.id.add_location_name);
        mlatitude = (EditText)findViewById(R.id.add_location_latitude);
        mlongitude =(EditText)findViewById(R.id.add_location_longitude);
        mbtn_addlocation_exit = findViewById(R.id.add_location_btn_exit);
        mbtn_addlocation_submit = findViewById(R.id.add_location_btn_submit);
        mRBrestaurant = findViewById(R.id.add_location_radio_button_restaurant);
        mRBlocation = findViewById(R.id.add_location_radio_button_location);

        Lat = intent.getDoubleExtra("Lat",0);
        Lon = intent.getDoubleExtra("Lon",0);//從上一頁取道ID

        mlatitude.setText(String.valueOf(Lat));
        mlongitude.setText(String.valueOf(Lon));
        mRBrestaurant.setOnClickListener(new View.OnClickListener() {//按下餐廳
            @Override
            public void onClick(View v) {
                SelectMode = 1;
            }
        });
        mRBlocation.setOnClickListener(new View.OnClickListener() {//按下集合點
            @Override
            public void onClick(View v) {
                SelectMode = 2;
            }
        });
        mbtn_addlocation_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mbtn_addlocation_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SelectMode!=0){
                    Thread t = new AddLocationActivity.thread(SelectMode,String.valueOf(mname.getText()),String.valueOf(mlatitude.getText()),String.valueOf(mlongitude.getText()));
                    t.start();
                    finish();
                }
            }
        });

    }
    class thread extends Thread{ //傳送的執行續
        Integer mode;
        String name;
        String Lat;
        String Lon;
        private PrintWriter out;
        public thread(Integer mode,String name,String Lat,String Lon){
            this.mode = mode;
            this.name = name;
            this.Lat = Lat;
            this.Lon = Lon;
        }

        public void run(){
            Socket socket = null;
            try{
                socket = ((MySocket)getApplication()).getSocket();//必須輸入區域網路的IP 不能輸入127.0.0.1
//                if(socket.isConnected()){
//                    //Log.d("Jimmy","Success");
//                }
                out = new PrintWriter(socket.getOutputStream(),true);
                if(mode==1){//新增餐廳
                    out.println("addRest");
                    out.println(name);
                    out.println(Lat);
                    out.println(Lon);
                }
                else if(mode==2){//新增集合點
                    out.println("addLoc");
                    out.println(name);
                    out.println(Lat);
                    out.println(Lon);
                }

            }catch (Exception e){
                Log.d("Jimmy","Error");
            }
        }
    }
}
