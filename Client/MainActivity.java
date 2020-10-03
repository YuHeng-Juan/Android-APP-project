package com.example.myapplication;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public Integer stopthread = 0;
    static Socket socket;
    private PrintWriter out;
    public BufferedReader in;
    public static String passWord;
    public static String userName;

    Handler UIHandler;
    Thread Thread1 = null;
    public String MyID_str;

    EditText et_name;
    EditText et_passW;
    Button btn_A;
    ActionBar ab;


    public int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ab = getSupportActionBar();
        ab.setTitle("");
        TextView sub_textView = (TextView) findViewById(R.id.subtext);
        sub_textView.setTypeface(Typeface.createFromAsset(getAssets(),"wt005.ttf"));
        TextView textView2 = findViewById(R.id.textView2);
        textView2.setTypeface(Typeface.createFromAsset(getAssets(),"方正粗倩繁体.ttf"));

        //搜尋輸入框和按鈕元件
        et_name =(EditText)findViewById(R.id.et_name);
        et_passW =(EditText)findViewById(R.id.et_passW);
        btn_A = (Button)findViewById(R.id.btn_A);

        //按鈕事件，把帳密傳送給server
        btn_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passWord = et_passW.getText().toString();
                userName = et_name.getText().toString();
                stopthread=0;
                Thread t = new MainActivity.thread(passWord, userName);
                t.start();
            }
        });

        //建立UIHandler
        UIHandler = new Handler();
        Thread1 = new Thread(new Thread1());
        Thread1.start();

    }

    //傳送功能的thread把帳密傳過去
    class thread extends Thread{
        String passWord;
        String userName;
        String login = "login";//等下傳給Server，表示我們在做登入功能

        //參數接入口
        public thread(String passWord, String userName){
            this.passWord = passWord;
            this.userName = userName;
        }

        //執行程式
        public void run(){
            try{
                socket = ((MySocket)getApplication()).getSocket();

                //檢查連線Socket是否正常
                if(socket.isConnected()){
                    Log.d("Jimmy","Success");
                }
                out = new PrintWriter(socket.getOutputStream(),true);//建立傳送元件
                out.println(login);//傳給Server，表示我們在做登入功能
                out.println(userName);//傳給Server帳號
                out.println(passWord);//傳給Server密碼
            }catch (Exception e){
                Log.d("Jimmy_Error","Error");
            }
        }
    }

    //從MySocket裡面拿取與server通訊的socket，再給thread2做下一步動作
    class Thread1 implements Runnable{ //連接socket 開啟第二個thread
        @Override
        public void run() {
            Socket socket = null;
            try {
                //接取Socket
                socket = ((MySocket)getApplication()).getSocket();

                //檢查連線Socket是否正常
                if(socket.isConnected()){
                    Log.d("Jimmy2","Connect");
                }
                Thread2 commThread = new Thread2(socket);
                new Thread(commThread).start();
                return;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //從Thread1拿到的socket拿來做聆聽，如果有東西，傳給Handler更新介面
    //沒有就跳回Thread1循環
    class Thread2 implements Runnable{ //讀取server給的資料 並把更新畫面的任務交給Handler去執行
        private Socket clientSocket;

        private BufferedReader input;
        private String Y_or_N;

        //把client socket的讀取元件建立好
        public Thread2(Socket clientSocket){
            this.clientSocket = clientSocket;
            try{
                this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream())); //建立輸入流
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try{
                    if(stopthread==0) {
                        System.out.println("stopthread=0");
                        String read = input.readLine();
                        if(read.charAt(1)=='1')stopthread=1;
                        Log.d("Jimmy","read+++:"+read);
                        if(read != null){
                            //把讀取到的資料傳給UIHandler，讓其作判斷
                            UIHandler.post(new updataUIThread(read));
                        }else{
                            Thread1 = new Thread(new Thread1());///////////////////////////////////////////////////////////////
                            Thread1.start();///////////////////////////////////////////////////////////////
                            return;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    //接收Thread2的資料，然後對資料做判定再來更新畫面，更新畫面呼叫Th3來用
    class updataUIThread implements Runnable{  //更新UI
        private String msg;
        private String sub;
        public updataUIThread(String str){
            this.msg = str;
        }
        @Override
        public void run() {
            sub = msg.substring(1);//從第二個字開始，因為第一個字是給ServerCP拿來判定東西的，這裡
                                   //用不到
            if(sub.equals("Yes")){
                Toast.makeText(MainActivity.this,"Login",Toast.LENGTH_SHORT).show();
                Thread th3 = new thread3();
                th3.start();
            }else if(sub.equals("No")){
                et_name.setText("");
                et_passW.setText(""); //清空填入格
                Toast.makeText(MainActivity.this,"Username or Password mistake!",Toast.LENGTH_SHORT).show();
            }else if(sub.charAt(0)=='1'){
                MyID_str = sub;
            }
        }
    }
    class thread3 extends Thread{

        //等下告訴server我要的功能圍login_next
        String login = "login_next";
        public void run(){
            try{

                //接取Socket
                socket = ((MySocket)getApplication()).getSocket();

                //判斷socket連線正常否
                if(socket.isConnected()){
                    Log.d("Jimmy","thread3_Success");
                }
                out = new PrintWriter(socket.getOutputStream(),true);
                //告訴server我要的功能
                out.println(login);
                sleep(1000);
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("MyID",MyID_str);//傳遞MyID
                intent.putExtra("Username",userName);//傳遞MyID
                Log.d("Jimmy","MyID"+MyID_str);
                stopthread=1;
                startActivity(intent);
                Log.d("Jimmy","Send");
            }catch (Exception e){
                Log.d("Jimmy","Error");
            }
        }
    }
}
