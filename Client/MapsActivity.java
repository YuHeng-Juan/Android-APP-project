package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMyLocationButtonClickListener {
    Integer Buttonmode=0;
    Integer choose_mark_id=-1;

    Integer wait_for_menu_data=0;
    Integer wait_for_order_data=0;
    public String read_menu_dishname;
    public String read_menu_price;

    public String read_order_username;
    public String read_order_dishname;
    public String read_order_price;



    public String menu_order_dishname_string;//菜單回傳的字串
    public String menu_order_price_string;//菜單回傳的字串
    public static String Username;//使用者名稱

    private ArrayList<String> assemble_name_list = new ArrayList<String>();
    private ArrayList<Double> assemble_lon_list = new ArrayList<Double>();
    private ArrayList<Double> assemble_lat_list = new ArrayList<Double>();
    private ArrayList<String> restaurant_name_list = new ArrayList<String>();
    private ArrayList<Double> restaurant_lon_list = new ArrayList<Double>();
    private ArrayList<Double> restaurant_lat_list = new ArrayList<Double>();
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Polyline polyline;
    ArrayList<LatLng> listPoints;//google地圖存放經緯度的格式




    public double locLat=0.0;
    public double locLong=0.0;
    LatLng choose_mark;//google地圖存放經緯度的格式
    public int flag = 0;

    Handler UIHandler;
    Thread Thread1 = null;

    public int time = 0;
    public ArrayList<String> msg_Array;
    public char MyID;
    public double[] old_locLat;
    public double[] old_locLong;
    public ArrayList<String> default_mark_name_Array;
    public ArrayList<String> default_mark_lat_Array;
    public ArrayList<String> default_mark_long_Array;

    public ArrayList<String> rest_name_Array;
    public ArrayList<String> rest_lat_Array;
    public ArrayList<String> rest_long_Array;

    public static double mapClickLocationLat=0;
    public static double mapClickLocationLon=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        String MyID_str = intent.getStringExtra("MyID");//從上一頁取道ID
        Username = intent.getStringExtra(Username);
        Log.d("Jimmy","ID"+MyID_str);
        MyID = MyID_str.charAt(2);//把1[個位數]裡面的個位數轉換成數字
        Log.d("Jimmy","sendMyID:"+MyID);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1, 0, this);
//        Location locationts = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        lat = location.getLatitude();  // 取得經度
//        lng = location.getLongitude();
        listPoints = new ArrayList<>();

        msg_Array = new ArrayList<String>(); //初始化Arraylist
        for(int i=0;i<5;i++) {
            msg_Array.add(i,null);
        }
        default_mark_name_Array = new ArrayList<String>(); //初始化Arraylist
        for(int i=0;i<5;i++) {
            default_mark_name_Array.add(i,null);
        }
        default_mark_lat_Array = new ArrayList<String>(); //初始化Arraylist
        for(int i=0;i<5;i++) {
            default_mark_lat_Array.add(i,null);
        }
        default_mark_long_Array = new ArrayList<String>(); //初始化Arraylist
        for(int i=0;i<5;i++) {
            default_mark_long_Array.add(i,null);
        }

        rest_name_Array = new ArrayList<String>(); //初始化Arraylist
        for(int i=0;i<5;i++) {
            rest_name_Array.add(i,null);
        }
        rest_lat_Array = new ArrayList<String>(); //初始化Arraylist
        for(int i=0;i<5;i++) {
            rest_lat_Array.add(i,null);
        }
        rest_long_Array = new ArrayList<String>(); //初始化Arraylist
        for(int i=0;i<5;i++) {
            rest_long_Array.add(i,null);
        }

        old_locLat = new double[5]; //初始化double陣列
        for (int i=0;i<5; i++) {
            old_locLat[i] = 0.0;
        }
        old_locLong = new double[5];
        for (int i=0;i<5; i++) {
            old_locLong[i] = 0.0;
        }

        UIHandler = new Handler();  //接收資料庫的座標位置
        Thread1 = new Thread(new Thread1());
        Thread1.start();

    }
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    menu_order_dishname_string = data.getExtras().getString("OrderDishnameString");//根据传值的类型，data.getFloatExtra可以是data.getStringExtra
                    menu_order_price_string = data.getExtras().getString("OrderPriceString");
                    break;
                }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        final Button bun_mapAddLocation = findViewById(R.id.btn_mapAdd);
        final Button btn_mapAssemble = findViewById(R.id.btn_mapAssemble);
        final Button btn_mapRest = findViewById(R.id.btn_mapRest);
        bun_mapAddLocation.setTypeface(Typeface.createFromAsset(getAssets(),"wt005.ttf"));
        btn_mapAssemble.setTypeface(Typeface.createFromAsset(getAssets(),"wt005.ttf"));
        btn_mapRest.setTypeface(Typeface.createFromAsset(getAssets(),"wt005.ttf"));

        mMap = googleMap;
//        final Button btn_B = findViewById(R.id.btn_B);
//        btn_B.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MapsActivity.this,RestaurantMenuActivity.class);
//                startActivity(intent);
//            }
//        });


                // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(24.178824, 120.646751);
        MapInfowindowAdapter CustomInfowindowAdapter = new MapInfowindowAdapter(MapsActivity.this);
        mMap.setInfoWindowAdapter(CustomInfowindowAdapter);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("My school"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(24.178824, 120.646751), 15));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            Log.d("Jimmy","false");
            // Show rationale and request permission.
        }
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("Jimmy","click");

                //如果mark已經有兩個
//                        if(listPoints.size()==1){
//                            listPoints.clear();
//                            mMap.clear();
//                        }
                //儲存第一個選擇的

                System.out.println("ccccccccccccccccccccc"+flag);
                if(flag==0) {
                    System.out.println("DDDDDDDDDDDDDDDDD");
                    System.out.println(latLng.latitude+ "-" + latLng.longitude);

                    mapClickLocationLat=latLng.latitude;
                    mapClickLocationLon=latLng.longitude;

                    if(listPoints.size()==1){
                        listPoints.clear();
                        mMap.clear();
                    }
                    listPoints.add(latLng);
                    //creat marker
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);

                    if (listPoints.size() == 1) {
                        //add first marker to the map
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                        //mMap.addMarker(markerOptions.title("Assemble point"));
                    }/*else{
                            //add second marker to the map
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            //mMap.addMarker(markerOptions);
                        }*/
                    mMap.addMarker(markerOptions.title("Assemble point"));

                    //畫線

//                                mMap.addMarker(new MarkerOptions().position(Feng).title("My school"));
//                                if(locLat!=location.getLatitude() || locLong!=location.getLongitude()){ //如果位置改變，則清除地圖
//                                    locLat = location.getLatitude();
//                                    locLong = location.getLongitude();
//                                }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }

            }
        });

        bun_mapAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, AddLocationActivity.class);
                intent.putExtra("Lat",mapClickLocationLat);
                intent.putExtra("Lon",mapClickLocationLon);
                startActivity(intent);
            }
        });
        btn_mapRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Buttonmode == 0) {//呼叫餐廳
                    Thread t = new thread("resturant");//要求server端的資料庫座標資料
                    t.start();

                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() { //點擊標記
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            return false;
                        }
                    });
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Log.d("Jimmy", "Markclick");
                            //Log.d("Jimmy",String.valueOf(locLat)+String.valueOf(locLong));
                            Log.d("Jimmy", marker.getPosition().toString());
                            choose_mark = marker.getPosition(); //存集合點座標
                            Thread t = new thread("markdata", choose_mark);//傳送位置訊息
                            t.start();
                            //flag = 1; //如果成功集合則開始傳送位置訊息
                        }
                    });
                }
                else if(Buttonmode==1) {//清單功能

                    wait_for_order_data=0;

                    //要資料
                    Thread t = new thread("orderget", choose_mark);//傳送位置訊息
                    t.start();


                    while (wait_for_order_data==0);
                    Intent intent = new Intent(MapsActivity.this, RestaurantOrderActivity.class);
                    intent.putExtra("username",read_order_username);
                    intent.putExtra("dishname",read_order_dishname);
                    intent.putExtra("price",read_order_price);
                    startActivity(intent);

                }
            }

        });


        btn_mapAssemble.setOnClickListener(new View.OnClickListener() { //點擊Assemble建
            @Override
            public void onClick(View v) {
                if(Buttonmode==0) {
                    Thread t = new thread("assemble");//要求server端的資料庫座標資料
                    t.start();
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() { //點擊標記
                        @Override
                        public boolean onMarkerClick(Marker marker) {

                            return false;
                        }
                    });
                    mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            Log.d("Jimmy", "Markclick");
                            //Log.d("Jimmy",String.valueOf(locLat)+String.valueOf(locLong));
                            Log.d("Jimmy", marker.getPosition().toString());
                            choose_mark = marker.getPosition(); //存集合點座標
                            //error1
                            Thread t = new thread("markdata", choose_mark);//傳送位置訊息
                            t.start();
                            //flag = 1; //如果成功集合則開始傳送位置訊息
                        }
                    });
                }
                else if(Buttonmode==1){
                    System.out.println("DDDD");
                    System.out.println(rest_name_Array);
                    System.out.println(choose_mark_id);
                    System.out.println(rest_name_Array.get(choose_mark_id));
                    rest_name_Array.get(choose_mark_id);

                    wait_for_menu_data=0;
                    Thread t = new thread("menu", choose_mark);//傳送位置訊息
                    t.start();
                    while (wait_for_menu_data==0);
                    Intent intent = new Intent(MapsActivity.this, RestaurantMenuActivity.class);
                    intent.putExtra("dishname",read_menu_dishname);
                    intent.putExtra("price",read_menu_price);
                    intent.putExtra("restname",String.valueOf(rest_name_Array.get(choose_mark_id)));
                    startActivity(intent);
                }





//                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                    @Override
//                    public void onMapClick(LatLng latLng) {
//                        Log.d("Jimmy","click");
//
//                        //如果mark已經有兩個
//                        if(listPoints.size()==1){
//                            listPoints.clear();
//                            mMap.clear();
//                        }
//                        //儲存第一個選擇的
//                        listPoints.add(latLng);
//                        //creat marker
//                        MarkerOptions markerOptions = new MarkerOptions();
//                        markerOptions.position(latLng);
//
//                        if(listPoints.size()==1){
//                            //add first marker to the map
//                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                            //mMap.addMarker(markerOptions.title("Assemble point"));
//                        }/*else{
//                            //add second marker to the map
//                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                            //mMap.addMarker(markerOptions);
//                        }*/
//                        mMap.addMarker(markerOptions.title("Assemble point"));
//
//                        //畫線
//
//                        mMap.addMarker(new MarkerOptions().position(Feng).title("My school"));
//                        if(locLat!=location.getLatitude() || locLong!=location.getLongitude()){ //如果位置改變，則清除地圖
//                            locLat = location.getLatitude();
//                            locLong = location.getLongitude();
//                        }
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(24.178824, 120.646751), 15));
//
//                    }
//                });
            }
        });
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onLocationChanged(Location location) { //回傳目前位置
        final Button mbtn_mapAssemble = findViewById(R.id.btn_mapAssemble);
        final Button mbtn_mapRest = findViewById(R.id.btn_mapRest);
        //Log.d("Jimmy","onLocationChanged - " + location.toString());
//        Toast.makeText(this, "MyLocation :"+location.getLatitude()+location.getLongitude(), Toast.LENGTH_SHORT).show();
//        System.out.println("MyLocation :"+location.getLatitude()+location.getLongitude());
        if(flag==1){ //點擊集合點並畫線
            polyline = mMap.addPolyline(new PolylineOptions().clickable(true).add(
                    new LatLng(location.getLatitude(),location.getLongitude()),choose_mark));
            polyline.setColor(Color.RED);
            if(locLat!=location.getLatitude() || locLong!=location.getLongitude()){ //如果位置改變，則清除地圖
                locLat = location.getLatitude();
                locLong = location.getLongitude();
                mMap.clear();
            }
            mMap.addMarker(new MarkerOptions().position(choose_mark).title("Assemble point"));
//            choose_mark.latitude;
//            choose_mark.longitude;

//            rest_name_Array;
//            rest_lat_Array;
//            rest_long_Array;
            Log.d("Jimmy","菜單2");
            System.out.println("Eeee");
            System.out.println(rest_lat_Array);
            System.out.println(choose_mark.latitude);
            System.out.println(rest_lat_Array.contains(String.valueOf(choose_mark.latitude)));
            System.out.println("Rrrr");
            System.out.println(rest_long_Array);
            System.out.println(choose_mark.longitude);
            System.out.println(rest_long_Array.contains(String.valueOf(choose_mark.longitude)));

            if(rest_lat_Array.contains(String.valueOf(choose_mark.latitude))&&rest_long_Array.contains(String.valueOf(choose_mark.longitude))){
                choose_mark_id=rest_lat_Array.indexOf(String.valueOf(choose_mark.latitude));//用緯度查，可能重複但先頂著用
                mbtn_mapAssemble.setText("菜單");
                mbtn_mapRest.setText("餐表");
                Buttonmode=1;
            }
            else{
                mbtn_mapAssemble.setText("集合點");
                mbtn_mapRest.setText("餐廳");
            }
            Thread t = new thread("locdata",locLat,locLong);//傳送位置訊息
            t.start();


        }

//        UIHandler = new Handler();
//        this.Thread1 = new Thread(new Thread1());
//        this.Thread1.start();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Jimmy","onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Jimmy","onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Jimmy","onProviderDisabled");
    }

    class thread extends Thread{ //傳送的執行續
        private PrintWriter out;
        double locLat;
        double locLong;
        private String loc_data;
        private String myLocData = "locdata";
        private String mode;
        private LatLng mark;
        public thread(String mode,double locLat, double locLong){
            this.mode = mode;
            this.locLat = locLat;
            this.locLong = locLong;
            loc_data = String.valueOf(locLat)+"-"+String.valueOf(locLong);
        }
        public thread(String mode){
            this.mode = mode;
        }
        public thread(String mode,LatLng mark){ //傳送點選的座標
            this.mode = mode;
            this.mark = mark;
        }
        public void run(){
            Socket socket = null;
            try{
                socket = ((MySocket)getApplication()).getSocket();//必須輸入區域網路的IP 不能輸入127.0.0.1
//                if(socket.isConnected()){
//                    //Log.d("Jimmy","Success");
//                }
                switch (mode){
                    case "assemble":
                        out = new PrintWriter(socket.getOutputStream(),true);
                        out.println("assemble");
                        break;
                    case "locdata":
                        out = new PrintWriter(socket.getOutputStream(),true);
                        out.println(myLocData);
                        out.println(loc_data);
                        Log.d("Jimmy","Data:"+loc_data);
                        break;
                    case "markdata":
                        out = new PrintWriter(socket.getOutputStream(),true);
                        out.println("markdata");//點集合點之後傳送給所有人
                        out.println(mark);
                        break;
                    case "resturant":
                        out = new PrintWriter(socket.getOutputStream(),true);
                        out.println("resturant");//要求餐廳位置
                        break;
                    case "menu":
                        out = new PrintWriter(socket.getOutputStream(),true);
                        out.println("menu");//要求餐廳位置
                        out.println(rest_name_Array.get(choose_mark_id));//要求餐廳位置
                        break;
                    case "orderget":
                        out = new PrintWriter(socket.getOutputStream(),true);
                        out.println("orderget");//要求餐廳位置
                        break;
                }

            }catch (Exception e){
                Log.d("Jimmy","Error");
            }
        }
    }
    class Thread1 implements Runnable{ //連接socket 開啟第二個thread
        @Override
        public void run() {
            Socket socket = null;
            try {
                socket = ((MySocket)getApplication()).getSocket();
                if(socket.isConnected()){
                    //Log.d("Jimmy2","Connect");
                }
                Thread2 commThread = new Thread2(socket);
                new Thread(commThread).start();
                return;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    class Thread2 implements Runnable{ //讀取server給的資料 並把更新畫面的任務交給Handler去執行
        private Socket clientSocket;


        private BufferedReader input;
        private String read;
        private char read_who;
        private String read_loc;

        private char read_mode;
        private int toknum;

        private String[] tokens;
        private String[] tokens_2;
        private String read_locLat;
        private String read_locLong;
        private String temp;

        private String read_assemble;
        private double markLat;
        private double markLong;
        private String[] read_mark_tokens;
        private String read_mark_name;
        private String read_mark_lat;
        private String read_mark_long;
        private String[] read_mark_tokens_default;

        private String[] read_rest_tokens;
        private String read_rest_name;
        private String read_rest_lat;
        private String read_rest_long;



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
                    read = input.readLine(); //讀取server傳過來的Array(但是是字串型態)
                    Log.d("Jimmy","read--:"+read);
                    read_mode = read.charAt(1); //讀取第一個數值來判斷模式
                    Log.d("Jimmy","read_mode:"+read.charAt(1));
                    switch (read_mode){
//                        case '1': //如果為1 表示這是自己的ID 並且存在MyID
//                            read_who = read.charAt(3);
//                            Log.d("1_ID","ID:"+read_who); //ID有問題
//                            if(time==0){
//                                MyID = read_who;
//                                time = 1;
//                            }
//                            Log.d("My","myID:"+MyID);
//
//                            break;
                        case '2': //如果是2 表示這是座標回傳
                        case 'n':
                            Log.d("2My","myID:"+MyID);
                            read_loc = read.substring(0,read.length()-2); //擷取Array裡面的值
                            tokens = read_loc.split(","); //以","分開
                            toknum = tokens.length; //查看有幾組資料
                            Log.d("Jimmy","tokens:"+tokens[0]);
                            for(int i=0;i<toknum;i++){
                                if(tokens[i]!=null){
                                    read_who = tokens[i].charAt(3); //檢查是否為自己的位置座標
                                    switch (read_who){
                                        case '1':
                                            Log.d("ID","read_ID:"+String.valueOf(read_who));
                                            if(read_who!=MyID){
                                                read_loc = tokens[i].substring(5);
                                                msg_Array.set(Character.getNumericValue('1')-1,read_loc);
                                                Log.d("Jimmy","loc_list:"+msg_Array);
                                            }
                                            break;
                                        case '2':
                                            Log.d("ID","read_ID:"+String.valueOf(read_who));
                                            if(read_who!=MyID){
                                                read_loc = tokens[i].substring(5);
                                                msg_Array.set(Character.getNumericValue('2')-1,read_loc);
                                                Log.d("Jimmy","loc_list:"+msg_Array); //座標分類 如有多個變數會被覆蓋
                                            }
                                            break;
                                        case '3':
                                            Log.d("ID","read_ID:"+String.valueOf(read_who));
                                            if(read_who!=MyID){
                                                read_loc = tokens[i].substring(5);
                                                msg_Array.set(Character.getNumericValue('3')-1,read_loc);
                                                Log.d("Jimmy","loc_list:"+msg_Array);
                                            }
                                            break;
                                        case '4':
                                            Log.d("ID","read_ID:"+String.valueOf(read_who));
                                            if(read_who!=MyID){
                                                read_loc = tokens[i].substring(5);
                                                msg_Array.set(Character.getNumericValue('4')-1,read_loc);
                                                Log.d("Jimmy","loc_list:"+msg_Array);
                                            }
                                            break;
                                        case '5':
                                            Log.d("ID","read_ID:"+String.valueOf(read_who));
                                            if(read_who!=MyID){
                                                read_loc = tokens[i].substring(5);
                                                msg_Array.set(Character.getNumericValue('5')-1,read_loc);
                                                Log.d("Jimmy","loc_list:"+msg_Array);
                                            }
                                            break;
                                    }

                                }

                            }
                            if(read != null){
                                UIHandler.post(new updataUIThread(2,msg_Array));
                            }else{
                                Thread1 = new Thread(new Thread1());
                                Thread1.start();
                                return;
                            }
                            break;

                        case '4': //如果為4 表示接收集合點座標
                            read_mark_name = read.substring(3);
                            read_mark_tokens_default = read_mark_name.split(",");
                            for(int i=0;i<read_mark_tokens_default.length;i++){
                                default_mark_name_Array.set(i,read_mark_tokens_default[i]);
                            }
                            Log.d("Jimmy","default_mark_name_Array:"+default_mark_name_Array);
                            read = input.readLine(); //讀取server傳過來的Array(但是是字串型態)
                            read_mark_lat = read.substring(3);
                            read_mark_tokens_default = read_mark_lat.split(",");
                            for(int i=0;i<read_mark_tokens_default.length;i++){
                                default_mark_lat_Array.set(i,read_mark_tokens_default[i]);
                            }
                            Log.d("Jimmy","default_mark_lat_Array:"+default_mark_lat_Array);
                            read = input.readLine(); //讀取server傳過來的Array(但是是字串型態)
                            read_mark_long = read.substring(3);
                            read_mark_tokens_default = read_mark_long.split(",");
                            for(int i=0;i<read_mark_tokens_default.length;i++){
                                default_mark_long_Array.set(i,read_mark_tokens_default[i]);
                            }
                            Log.d("Jimmy","default_mark_long_Array:"+default_mark_long_Array);
                            //處理資料 如果用arr要先知道有幾個元素並在接收到後創建 記得清空較不會報錯
                            if(read != null){
                                UIHandler.post(new updataUIThread(4,default_mark_name_Array,default_mark_lat_Array,default_mark_long_Array));
                            }else{
                                Thread1 = new Thread(new Thread1());
                                Thread1.start();
                                return;
                            }
                            break;
                        case '5': //如果為5 表示接收"對方"選擇的集合點

                            read_mark_tokens = read.substring(3,read.length()-1).split(",");
                            Log.d("Jimmy_mark_read","Mark_read:"+read_mark_tokens);
                            markLat = Double.parseDouble(read_mark_tokens[0]);
                            markLong = Double.parseDouble(read_mark_tokens[1]);
                            LatLng mark_temp = new LatLng(markLat,markLong);
                            if(read != null){
                                UIHandler.post(new updataUIThread(5,mark_temp));
                            }else{
                                Thread1 = new Thread(new Thread1());
                                Thread1.start();
                                return;
                            }
                            break;
                        case '6': //如果為4 表示接收集合點座標
                            read_rest_name = read.substring(3);
                            read_rest_tokens = read_rest_name.split(",");
                            for(int i=0;i<read_rest_tokens.length;i++){
                                rest_name_Array.set(i,read_rest_tokens[i]);
                            }
                            //Log.d("Jimmy","default_mark_name_Array:"+default_mark_name_Array);
                            read = input.readLine(); //讀取server傳過來的Array(但是是字串型態)
                            read_rest_lat = read.substring(3);
                            read_rest_tokens = read_rest_lat.split(",");
                            for(int i=0;i<read_rest_tokens.length;i++){
                                rest_lat_Array.set(i,read_rest_tokens[i]);
                            }
                            //Log.d("Jimmy","default_mark_lat_Array:"+default_mark_lat_Array);
                            read = input.readLine(); //讀取server傳過來的Array(但是是字串型態)
                            read_rest_long = read.substring(3);
                            read_rest_tokens = read_rest_long.split(",");
                            for(int i=0;i<read_rest_tokens.length;i++){
                                rest_long_Array.set(i,read_rest_tokens[i]);
                            }
                            //Log.d("Jimmy","default_mark_long_Array:"+default_mark_long_Array);
                            //處理資料 如果用arr要先知道有幾個元素並在接收到後創建 記得清空較不會報錯
                            if(read != null){
                                UIHandler.post(new updataUIThread(6,rest_name_Array,rest_lat_Array,rest_long_Array));
                            }else{
                                Thread1 = new Thread(new Thread1());
                                Thread1.start();
                                return;
                            }
                            break;
                        case '7': //如果為7 表示接收菜單
                            read_menu_dishname = read.substring(3);
                            //Log.d("Jimmy","default_mark_name_Array:"+default_mark_name_Array);
                            read = input.readLine(); //讀取server傳過來的Array(但是是字串型態)
                            read_menu_price = read.substring(3);
                            wait_for_menu_data=1;
                            break;
                        case '8': //如果為7 表示接收菜單
                            read_order_username = read.substring(3);
                            //Log.d("Jimmy","default_mark_name_Array:"+default_mark_name_Array);
                            read = input.readLine(); //讀取server傳過來的Array(但是是字串型態)
                            read_order_dishname = read.substring(3);
                            read = input.readLine(); //讀取server傳過來的Array(但是是字串型態)
                            read_order_price = read.substring(3);
                            wait_for_order_data = 1;
                            break;
                        default:

                            break;
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    class updataUIThread implements Runnable{  //更新UI

        private String[] str1;
        private String[] str2;
        private ArrayList<String> msg_Array;
        private String[] token;
        private int mode;
        private LatLng mark_temp;
        private ArrayList<String> name_msg_Array;
        private ArrayList<String> lat_msg_Array;
        private ArrayList<String> long_msg_Array;

        private ArrayList<String> name_rest_temp_Array;
        private ArrayList<String> lat_rest_temp_Array;
        private ArrayList<String> long_rest_temp_Array;
        private LatLng default_mark;

        public updataUIThread(int mode,ArrayList<String> msg_Array){
            this.mode = mode;
            this.msg_Array = msg_Array;
            str1 = new String[5];
            str2 = new String[5];
        }
        public updataUIThread(int mode,LatLng mark){
            this.mode = mode;
            this.mark_temp = mark;
        }
        public updataUIThread(int mode,ArrayList<String> name_msg_Array,ArrayList<String> lat_msg_Array,ArrayList<String> long_msg_Array){
            this.mode = mode;
            switch (mode){
                case 4:
                    this.name_msg_Array = name_msg_Array;
                    this.lat_msg_Array = lat_msg_Array;
                    this.long_msg_Array = long_msg_Array;
                    break;
                case 6:
                    this.name_rest_temp_Array = name_msg_Array;
                    this.lat_rest_temp_Array = lat_msg_Array;
                    this.long_rest_temp_Array = long_msg_Array;
                    break;
            }

        }
        @Override
        public void run() {
            //更新地圖
            switch (mode){
                case 2:
                    flag = 1;
                    if(msg_Array.isEmpty()==false){
                        for(int i=0;i<5;i++){
                            if(msg_Array.get(i)!=null){
                                token = msg_Array.get(i).split("-");
                                switch (i){
                                    case 0:
                                        str1[i] = token[0];
                                        str2[i] = token[1];
                                        LatLng move1 = new LatLng(Double.parseDouble(str1[i]),Double.parseDouble(str2[i]));  //模擬其他朋友
                                        mMap.addMarker(new MarkerOptions().position(move1).title("A").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                                        Log.d("MAP","draw");
                                        if(old_locLat[i]!=Double.parseDouble(str1[i]) || old_locLong[i]!=Double.parseDouble(str2[i])){ //如果位置改變，則清除地圖
                                            old_locLat[i] = Double.parseDouble(str1[i]);
                                            old_locLong[i] = Double.parseDouble(str2[i]);
                                            mMap.clear();
                                        }
                                        break;
                                    case 1:
                                        str1[i] = token[0];
                                        str2[i] = token[1];
                                        LatLng move2 = new LatLng(Double.parseDouble(str1[i]),Double.parseDouble(str2[i]));  //模擬其他朋友
                                        mMap.addMarker(new MarkerOptions().position(move2).title("B").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                                        Log.d("MAP","draw");
                                        if(old_locLat[i]!=Double.parseDouble(str1[i]) || old_locLong[i]!=Double.parseDouble(str2[i])){ //如果位置改變，則清除地圖
                                            old_locLat[i] = Double.parseDouble(str1[i]);
                                            old_locLong[i] = Double.parseDouble(str2[i]);
                                            mMap.clear();
                                        }
                                        break;
                                    case 2:
                                        str1[i] = token[0];
                                        str2[i] = token[1];
                                        LatLng move3 = new LatLng(Double.parseDouble(str1[i]),Double.parseDouble(str2[i]));  //模擬其他朋友
                                        mMap.addMarker(new MarkerOptions().position(move3).title("C").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                        Log.d("MAP","draw");
                                        if(old_locLat[i]!=Double.parseDouble(str1[i]) || old_locLong[i]!=Double.parseDouble(str2[i])){ //如果位置改變，則清除地圖
                                            old_locLat[i] = Double.parseDouble(str1[i]);
                                            old_locLong[i] = Double.parseDouble(str2[i]);
                                            mMap.clear();
                                        }
                                        break;
                                    case 3:
                                        str1[i] = token[0];
                                        str2[i] = token[1];
                                        LatLng move4 = new LatLng(Double.parseDouble(str1[i]),Double.parseDouble(str2[i]));  //模擬其他朋友
                                        mMap.addMarker(new MarkerOptions().position(move4).title("D").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                                        Log.d("MAP","draw");
                                        if(old_locLat[i]!=Double.parseDouble(str1[i]) || old_locLong[i]!=Double.parseDouble(str2[i])){ //如果位置改變，則清除地圖
                                            old_locLat[i] = Double.parseDouble(str1[i]);
                                            old_locLong[i] = Double.parseDouble(str2[i]);
                                            mMap.clear();
                                        }
                                        break;
                                    case 4:
                                        str1[i] = token[0];
                                        str2[i] = token[1];
                                        LatLng move5 = new LatLng(Double.parseDouble(str1[i]),Double.parseDouble(str2[i]));  //模擬其他朋友
                                        mMap.addMarker(new MarkerOptions().position(move5).title("E").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                                        Log.d("MAP","draw");
                                        if(old_locLat[i]!=Double.parseDouble(str1[i]) || old_locLong[i]!=Double.parseDouble(str2[i])){ //如果位置改變，則清除地圖
                                            old_locLat[i] = Double.parseDouble(str1[i]);
                                            old_locLong[i] = Double.parseDouble(str2[i]);
                                            mMap.clear();
                                        }
                                        break;

                                    default:
                                        break;
                                }
                            }
                        }
                    }
                    break;
                case 4:
                    Log.d("Jimmy","Into this");
                    Log.d("Jimmy","name_mark"+name_msg_Array);
                    if(name_msg_Array.isEmpty()==false){
                        for(int i=0;i<5;i++) {
                            if(name_msg_Array.get(i)!=null){
                                LatLng move_default = new LatLng(Double.parseDouble(lat_msg_Array.get(i)), Double.parseDouble(long_msg_Array.get(i)));  //模擬其他朋友
                                mMap.addMarker(new MarkerOptions().position(move_default).title(name_msg_Array.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).snippet("集合點"));
                            }
                        }
                    }

                    break;
                case 5:
                    Log.d("Jimmy_Handler","Success");
                    new AlertDialog.Builder(MapsActivity.this)
                            .setTitle("集合至集合點")
                            .setIcon(R.mipmap.ic_launcher)
                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Thread t = new thread("resturant");//要求server端的資料庫座標資料
                                    t.start();
                                    choose_mark = mark_temp;
                                    flag = 1;
                                }
                            }).show();
                    break;
                case 6:
//                    Log.d("Jimmy","Into this");
//                    Log.d("Jimmy","name_mark"+name_msg_Array);
                    if(name_rest_temp_Array.isEmpty()==false){
                        for(int i=0;i<5;i++) {
                            if(name_rest_temp_Array.get(i)!=null){
                                LatLng move_rest = new LatLng(Double.parseDouble(lat_rest_temp_Array.get(i)), Double.parseDouble(long_rest_temp_Array.get(i)));  //模擬其他朋友
                                mMap.addMarker(new MarkerOptions().position(move_rest).title(name_rest_temp_Array.get(i)).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).snippet("餐廳"));
                            }
                        }
                    }

                    break;
            }

//            LatLng move = new LatLng(Double.parseDouble(str1),Double.parseDouble(str2));  //模擬其他朋友
//            mMap.addMarker(new MarkerOptions().position(move).title("BUC"));
//            Log.d("MAP","draw");
//            if(old_locLat!=Double.parseDouble(str1) || old_locLnog!=Double.parseDouble(str2)){ //如果位置改變，則清除地圖
//                old_locLat = Double.parseDouble(str1);
//                old_locLnog = Double.parseDouble(str2);
//                mMap.clear();
//            }
        }
    }

}
