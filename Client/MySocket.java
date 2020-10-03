package com.example.myapplication;

import android.app.Application;

import java.io.IOException;
import java.net.Socket;

public class MySocket extends Application {
    Socket socket = null;
    private static String IP = null;
    private static int port = 5678;
    public Socket getSocket(){
        return socket;
    }
    public void setSocket(String IP, int port) throws IOException {
        if(socket==null){
            this.socket = new Socket(IP,port);
        }
    }
}
