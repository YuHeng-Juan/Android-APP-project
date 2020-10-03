package com.pcsetting.example;
import java.io.IOException;
import java.io.*;
import java.net.*;
import java.util.*;

//import jdk.jfr.internal.PrivateAccess;

//import jdk.internal.org.jline.utils.InputStreamReader;
import java.sql.*;
import java.io.*;

public class MyServerCp {

	ArrayList<String> assemble_name_list = new ArrayList<String>();
	ArrayList<String> assemble_lon_list = new ArrayList<String>();
	ArrayList<String> assemble_lat_list = new ArrayList<String>();
	ArrayList<String> restaurant_name_list = new ArrayList<String>();
	ArrayList<String> restaurant_lon_list = new ArrayList<String>();
	ArrayList<String> restaurant_lat_list = new ArrayList<String>();
	ArrayList<String> menu_dishname = new ArrayList<String>();
	ArrayList<String> menu_price = new ArrayList<String>();
	ArrayList<String> order_username = new ArrayList<String>();
	ArrayList<String> order_dishname = new ArrayList<String>();
	ArrayList<String> order_price = new ArrayList<String>();
	
	
	///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
	// MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/friendsgo?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "1234567890";
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
    private void SQL_InsertTable(String SQLcommand) {
    	Connection conn = null;
    	Statement stmt = null;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			stmt.executeUpdate(SQLcommand);
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}	
    private void SQL_SelectTable(String SQLcommand) {
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
//			stmt.executeUpdate(SQLcommand);
			rs = stmt.executeQuery(SQLcommand);
			while(rs.next()){
                // 通过字段检索
            	String id  = rs.getString("餐廳名稱");
                String name = rs.getString("餐廳ID");
                Double do1 = rs.getDouble("經度");
                Double do2 = rs.getDouble("緯度");
                
                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", 站点名称: " + name);
                System.out.print(", 經度: " + do1);
                System.out.print(", 緯度: " + do2);
                System.out.print("\n");
                
            }
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}	
    
    //這是求出資料庫內的集合點資料，用參數去接
    private void SQL_SearchAssemble(ArrayList<String> namelist,ArrayList<String> lonlist,ArrayList<String> latlist) {
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	String SQLcommand;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
//			stmt.executeUpdate(SQLcommand);
			SQLcommand = "SELECT * FROM 集合點 ORDER BY 集合點ID";
			rs = stmt.executeQuery(SQLcommand);
			namelist.clear();
			lonlist.clear();
			latlist.clear();
			while(rs.next()){
                // 通过字段检索
            	String id  = rs.getString("集合點ID");
                String name = rs.getString("名稱");
                Double do1 = rs.getDouble("經度");
                Double do2 = rs.getDouble("緯度");
                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", 名稱: " + name);
                System.out.print(", 經度: " + do1);
                System.out.print(", 緯度: " + do2);
                System.out.print("\n");

                
                namelist.add(name);
                lonlist.add(String.valueOf(do1));
                latlist.add(String.valueOf(do2));
            }
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}	
    
  //這是求出資料庫內的集合點資料，用參數去接
    private void SQL_SearchRestaurant(ArrayList<String> namelist,ArrayList<String> lonlist,ArrayList<String> latlist) {
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	String SQLcommand;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
//			stmt.executeUpdate(SQLcommand);
			SQLcommand = "SELECT * FROM 餐廳 ORDER BY 餐廳ID";
			rs = stmt.executeQuery(SQLcommand);
			namelist.clear();
			lonlist.clear();
			latlist.clear();
			while(rs.next()){
                // 通过字段检索
            	String id  = rs.getString("餐廳ID");
                String name = rs.getString("餐廳名稱");
                Double do1 = rs.getDouble("經度");
                Double do2 = rs.getDouble("緯度");
                
                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", 名稱: " + name);
                System.out.print(", 經度: " + do1);
                System.out.print(", 緯度: " + do2);
                System.out.print("\n");

                
                namelist.add(name);
                lonlist.add(String.valueOf(do1));
                latlist.add(String.valueOf(do2));
                
            }
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}	
//    透過餐廳名稱搜尋餐點
    private void SQL_SearchMenu(String RestaurantName,ArrayList<String> DishName,ArrayList<String> Price) {
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	PreparedStatement pst = null; 
    	String SQLcommand;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			SQLcommand = "SELECT * FROM 餐點 WHERE 餐廳名稱= ? ";
			pst = conn.prepareStatement(SQLcommand);
	    	pst.setString(1, RestaurantName);
			rs = pst.executeQuery();
			DishName.clear();
			Price.clear();
			while(rs.next()){
                // 通过字段检索
            	String id  = rs.getString("餐廳名稱");
                String name = rs.getString("餐名");
                Integer do1 = rs.getInt("價格");
                
//                 输出数据
//                System.out.print("餐廳名稱: " + id);
//                System.out.print(", 餐名: " + name);
//                System.out.print(", 價格: " + do1);
//                System.out.print("\n");

                
                DishName.add(name);
                Price.add(String.valueOf(do1));
                
            }
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}	
    //登入帳號，如果正確回傳1，否則回傳0
    private Integer SQL_Sign_In(String name,String pass) {
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	PreparedStatement pst = null; 
    	Integer flag=0;
    	String sql;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			sql = "SELECT * FROM 帳號 WHERE Username = ? AND Password = ?";
	    	pst = conn.prepareStatement(sql);
	    	pst.setString(1, name);
	    	pst.setString(2, pass);
			rs = pst.executeQuery();
			if(rs.next()){
                System.out.println("sign in success!");
                flag=1;
            }
			else {
                System.out.println("sign in fail!");
                flag=0;
            }
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
    	return flag;
	}	
    private void SQL_Sign_Up(String name,String pass) {
    	Connection conn = null;
    	Statement stmt = null;
    	ResultSet rs = null;
    	PreparedStatement pst = null; 
    	String sql;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			sql = "INSERT into 帳號(UserID,Username,Password) SELECT ifNULL(max(UserID),0)+1,?,? FROM 帳號";
	    	pst = conn.prepareStatement(sql);
	    	pst.setString(1, name);
	    	pst.setString(2, pass);
	    	pst.executeUpdate(); 
			System.out.println("sign up success!");
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}	
    
    private void SQL_AddRest(String name,String Lat,String Lon) {
    	Connection conn = null;
    	Statement stmt = null;
    	PreparedStatement pst = null; 
    	String SQLcommand;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			SQLcommand = "INSERT INTO `friendsgo`.`餐廳` (`餐廳ID`, `餐廳名稱`, `緯度`, `經度`) VALUES ('9999999999', ?, ?, ?);";

			pst = conn.prepareStatement(SQLcommand);
	    	pst.setString(1, name);
	    	pst.setString(2, Lat);
	    	pst.setString(3, Lon);
	    	pst.executeUpdate();
	    	
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}	
    
    private void SQL_AddLoc(String name,String Lat,String Lon) {
    	Connection conn = null;
    	Statement stmt = null;
    	PreparedStatement pst = null; 
    	String SQLcommand;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			SQLcommand = "INSERT INTO `friendsgo`.`集合點` (`集合點ID`, `名稱`, `緯度`, `經度`) VALUES ('9999999999', ?, ?, ?);";

			pst = conn.prepareStatement(SQLcommand);
	    	pst.setString(1, name);
	    	pst.setString(2, Lat);
	    	pst.setString(3, Lon);
	    	pst.executeUpdate();
	    	
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}	
    
    private void SQL_AddDish(String restname,String dishname,String price) {
    	Connection conn = null;
    	Statement stmt = null;
    	PreparedStatement pst = null; 
    	String SQLcommand;
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			
			SQLcommand = "INSERT INTO `friendsgo`.`餐點` (`餐廳名稱`, `餐名`, `價格`) VALUES (?, ?, ?);";
			System.out.println("TTTT1");
			System.out.println(restname);
			System.out.println("TTTT2");
			System.out.println(dishname);
			System.out.println("TTTT3");
			System.out.println(price);
			pst = conn.prepareStatement(SQLcommand);
	    	pst.setString(1, restname);
	    	pst.setString(2, dishname);
	    	pst.setString(3, price);
	    	pst.executeUpdate();
	    	
		}catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
	}	
    
    
    
    
    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////
	
	int port;
	List<Socket> clients; //宣告一個Socket陣列
	ServerSocket server;
	ArrayList<String> msg_Array = new ArrayList<String>();
	public static void main(String[] args) {
		new MyServerCp();
	}
	
	public MyServerCp() {
		PrintWriter pw;
		
		
		///////////////////////////////////////////////////資料庫
		///////////////////////////////////////////////////資料庫
		
		///////////////////////////////////////////////////資料庫
		///////////////////////////////////////////////////資料庫
		for(int i=0;i<5;i++) {
			msg_Array.add(i,null);
		}
		SQL_SearchAssemble(assemble_name_list, assemble_lon_list, assemble_lat_list);
		SQL_SearchRestaurant(restaurant_name_list,restaurant_lon_list,restaurant_lat_list);
		for(int i = 0 ;i < assemble_name_list.size() ; i++){
			System.out.println(assemble_name_list.get(i));
			System.out.println(assemble_lon_list.get(i));
			System.out.println(assemble_lat_list.get(i));
		}
		for(int i = 0 ;i < assemble_name_list.size() ; i++){
			System.out.println(restaurant_name_list.get(i));
			System.out.println(restaurant_lon_list.get(i));
			System.out.println(restaurant_lat_list.get(i));
		}
		try {
			port = 5678;
			clients = new ArrayList<Socket>(); //建立一個存client的socket的陣列
			server = new ServerSocket(port); 
			System.out.println("Server is opening!");
			int clientMember = 0;
			while (true) {
				Socket socket = server.accept(); //等待socket
				System.out.printf("try to connect to %s \n",socket.getInetAddress().toString());
				clientMember++;
				clients.add(socket); //將client的socket存進陣列
				Mythread mythread = new Mythread(socket, clientMember); //建立thread
				mythread.start();
			}
		} catch (Exception ex) {
			System.out.println("vv");
		}
	}
	
	class Mythread extends Thread {
		Socket ssocket;
		private int count;
		private BufferedReader br;
		private PrintWriter pw;
		public String msg;
		public char who_client;
		public char mark;
		public String userName="";
		public String password="";
		public String addName;
		public String addLat;
		public String addLon;
		public String addRestName;
		public String addDishName;
		public String addPrice;
		private int flag=0;
		private double locLat = 24.179053;
		private double locLong = 120.645233;
		private String[] temp;
		
		public Mythread(Socket s, int c) {
			ssocket = s;
			count = c;
		}

		public void run() {
			
			char str[] = new char[50];
			try {
				br = new BufferedReader(new InputStreamReader(ssocket.getInputStream())); //讀取標準輸入串流

				msg = "Welcome【" + "Client " + count + "】enter chatroom！There are【" + clients.size() + "】people";

				System.out.println(msg);
				//sendMsg();//傳送訊息
				while ((msg = br.readLine()) != null) {
					switch (msg) {
						case "login":
							//帳號登入
							for(int i=0;i<2;i++) {
								msg = br.readLine();
								if(i==0) {
									userName=msg;
								}
								else {
									password=msg;
								}
								System.out.println(msg);
							}
							flag = SQL_Sign_In(userName,password);
							if(flag==0) {
								msg = "3No";
							}else {
								msg = "3Yes";
							}
							sendMsg();
							break;
						case "locdata":
							msg = br.readLine();
							System.out.println(msg);
							msg = "2["+count+"]"+msg;
							
							msg_Array.set(count-1,msg);
//							if(msg_Array.get(count-1)==null) {
//								msg_Array.add(count-1,msg);
//							}else {
//								msg_Array.set(count-1,msg);
//							}
							sendMsg();
							break;
						case "login_next":
							msg = "1["+count+"]";
							sendMsg();
							break;
						case "assemble":
							//跟資料庫要資料 可用list 
							SQL_SearchAssemble(assemble_name_list, assemble_lon_list, assemble_lat_list);
							msg = "4";
							sendMsg();
							break;
						case "markdata":
							msg = br.readLine();
							msg = msg.substring(9);
							msg = "5"+msg;
							System.out.println("Result: "+msg);
							sendMsg();
							break;
						case "resturant":
							//跟資料庫要資料 可用list 
							SQL_SearchRestaurant(restaurant_name_list,restaurant_lon_list,restaurant_lat_list);
							msg = "6";
							sendMsg();
							break;
						case "menu":
							//跟資料庫要資料 可用list 
							msg = br.readLine();
							System.out.println("POPO:"+msg);
							SQL_SearchMenu(msg,menu_dishname,menu_price);
							msg = "7"+msg;
							sendMsg();
							break;
						case "orderget":
							//跟資料庫要資料 可用list 
							msg = "8"+msg;
							sendMsg();
							break;
						case "orderadd":
							msg = br.readLine();
							temp = msg.split(",");
							for(int i=0;i<temp.length;i++) {
								order_username.add(temp[i]);
							}
							msg = br.readLine();
							temp = msg.split(",");
							for(int i=0;i<temp.length;i++) {
								order_dishname.add(temp[i]);
							}
							msg = br.readLine();
							temp = msg.split(",");
							for(int i=0;i<temp.length;i++) {
								order_price.add(temp[i]);
							}

							System.out.println("1"+order_username);

							System.out.println("2"+order_dishname);

							System.out.println("3"+order_price);
							break;
						case "addRest":
							addName = br.readLine();
							addLat = br.readLine();
							addLon = br.readLine();
							SQL_AddRest(addName, addLat, addLon);
							break;
						case "addLoc":
							addName = br.readLine();
							addLat = br.readLine();
							addLon = br.readLine();
							SQL_AddLoc(addName, addLat, addLon);
							break;
						case "addDish":
							addRestName = br.readLine();
							System.out.println(addRestName);
							addDishName = br.readLine();
							System.out.println(addDishName);
							addPrice = br.readLine();
							System.out.println(addPrice);
							SQL_AddDish(addRestName, addDishName, addPrice);
							break;
						default:
							throw new IllegalArgumentException("Unexpected value: " + msg);
					}
					for(String number:msg_Array) {
							System.out.println("String: "+number);
					}
					
					
					/*mark = msg.charAt(0); //擷取字串第0個字元
					if(msg.length()>1) {
						who_client = msg.charAt(1); //擷取字串第1個字元
					}
					if(mark=='[') { //如果第0個字元是 ' [ ' 表示這次的傳送是要傳送秘密訊息
						msg.getChars(3,msg.length(),str,0);
						//因為前三個字元是表示所要傳輸的對象，所以從字串第三的位置往後取做為要傳輸的訊息
						msg = "【" + "Client " + count + "】said the secret to you:" + String.valueOf(str);
						//將擷取後的字串加入要傳送的字串中
					}else {
						msg = "【" + "Client " + count + "】said:" + msg;
					}*/
					//sendMsg();

				}
				ssocket.close();
			} catch (Exception ex) {
				System.out.println("Error");
			}
		}

		public void sendMsg() {
			char cnum;
			String msg5;
			try {
				cnum = msg.charAt(0);
				System.out.println(msg);
				System.out.println("("+cnum+")");
				
				/*if(mark=='[') { //表示這次傳輸為秘密傳輸
					pw = new PrintWriter(clients.get(Character.getNumericValue(who_client)-1).getOutputStream(), true);
					pw.println(msg);
					pw.flush();
				}else { //廣播傳輸
					for (int i = clients.size() - 1; i >= 0; i--) {
						pw = new PrintWriter(clients.get(i).getOutputStream(), true);
						pw.println(msg);
						pw.flush();
					}
				}*/
				switch (cnum) {
				case '1':
					msg = "["+msg;
					pw = new PrintWriter(clients.get(count-1).getOutputStream(), true);
					pw.println(msg);
					pw.flush();
					break ;
				case '2':
					for (int i = clients.size() - 1; i >= 0; i--) {
						pw = new PrintWriter(clients.get(i).getOutputStream(), true);
						pw.println(msg_Array);
						pw.flush();
					}
					break ;
				case '3':
					pw = new PrintWriter(clients.get(count-1).getOutputStream(), true);
					pw.println(msg);
					pw.flush();
					break ;
				case '4':
					pw = new PrintWriter(clients.get(count-1).getOutputStream(), true);
					msg = String.join(",", assemble_name_list);
					msg = "[4["+msg;
					pw.println(msg);
					msg = String.join(",", assemble_lat_list);
					msg = "[4["+msg;
					pw.println(msg);
					msg = String.join(",", assemble_lon_list);
					msg = "[4["+msg;
					pw.println(msg);
					pw.flush();
					break ;
				case '5':
					for (int i = clients.size() - 1; i >= 0; i--) {
						if(i!=count-1) {
							System.out.println(i);
							pw = new PrintWriter(clients.get(i).getOutputStream(), true);
							msg5 = "["+msg;
							System.out.println("RRR"+msg5);
							pw.println(msg5);
							pw.flush();
						}
					}
					break ;
				case '6':
					pw = new PrintWriter(clients.get(count-1).getOutputStream(), true);
					msg = String.join(",", restaurant_name_list);
					msg = "[6["+msg;
					pw.println(msg);
					msg = String.join(",", restaurant_lat_list);
					msg = "[6["+msg;
					pw.println(msg);
					msg = String.join(",", restaurant_lon_list);
					msg = "[6["+msg;
					pw.println(msg);
					pw.flush();
					break ;
				case '7':
					pw = new PrintWriter(clients.get(count-1).getOutputStream(), true);
					msg = String.join(",", menu_dishname);
					msg = "[7["+msg;
					pw.println(msg);
					msg = String.join(",", menu_price);
					msg = "[7["+msg;
					pw.println(msg);
					pw.flush();
					break ;
				case '8':
					pw = new PrintWriter(clients.get(count-1).getOutputStream(), true);
					msg = String.join(",", order_username);
					msg = "[8["+msg;
					pw.println(msg);
					msg = String.join(",", order_dishname);
					msg = "[8["+msg;
					pw.println(msg);
					msg = String.join(",", order_price);
					msg = "[8["+msg;
					pw.println(msg);
					pw.flush();
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: ");
				}
			} catch (Exception ex) {
			}
		}
	}   

}

