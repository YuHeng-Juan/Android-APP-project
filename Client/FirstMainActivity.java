package com.example.myapplication;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.net.Socket;

public class FirstMainActivity extends AppCompatActivity {

    public Socket socket;
    EditText et_serverIp;
    Button btn_A;
    ActionBar ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ab = getSupportActionBar();
        ab.setTitle("");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_main);
//        final Button btn_B = findViewById(R.id.btn_B);

        TextView sub_textView = (TextView) findViewById(R.id.subtext);
        sub_textView.setTypeface(Typeface.createFromAsset(getAssets(),"wt005.ttf"));
        TextView textView = findViewById(R.id.textView);
        textView.setTypeface(Typeface.createFromAsset(getAssets(),"方正粗倩繁体.ttf"));


        et_serverIp = (EditText) findViewById(R.id.et_serverIp);
        btn_A = (Button)findViewById(R.id.btn_A);

        btn_A.setTypeface(Typeface.createFromAsset(getAssets(),"PoiretOne-Regular_0.ttf"));

        btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverIp = et_serverIp.getText().toString();

                Thread t = new FirstMainActivity.thread(serverIp);
                t.start();

                Intent intent = new Intent(FirstMainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
//        btn_B.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(FirstMainActivity.this,RestaurantMenuActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    class thread extends Thread{
        String serverIp;
        public thread(String serverIp){
            this.serverIp = serverIp;
        }
        public void run(){
            try{
                ((MySocket)getApplication()).setSocket(serverIp,5678);//必須輸入區域網路的IP 不能輸入127.0.0.1
                socket = ((MySocket)getApplication()).getSocket();
                if(socket.isConnected()){
                    Log.d("Jimmy","Success");
                }
            }catch (Exception e){
                Log.d("Jimmy_Error","Error");
            }
        }
    }
}
