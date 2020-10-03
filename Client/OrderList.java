package com.example.myapplication;

import android.app.Application;

import java.util.ArrayList;

public class OrderList{
 //username 使用者 dishname菜名 price價錢
     public ArrayList<String> userName = new ArrayList<String>();
     public ArrayList<String> dishName = new ArrayList<String>();
     public ArrayList<String> price = new ArrayList<String>();
     public ArrayList<String> getUserName(){
         return userName;
     }
     public ArrayList<String> getDishName() {
         return dishName;
     }
     public ArrayList<String> getPrice() {
         return price;
     }

     public void setUserName(int i,String username) {
         this.userName.set(i,username);
     }
     public void setDishName(int i,String dishname) {
         this.dishName.set(i,dishname);
     }
     public void setPrice(int i,String price) {
         this.price.set(i,price);
     }

     public void addUserName(String username){
         this.userName.add(username);
     }
     public void addDishName(String dishname){
         this.dishName.add(dishname);
     }
     public void addPrice(String price){
         this.price.add(price);
     }

}
