package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.LinearViewHolder> {
    Integer i=0;
    SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
    public static ArrayList<String> OrderUsername = new ArrayList<String>();
    public static ArrayList<String> OrderDishname = new ArrayList<String>();
    public static ArrayList<String> OrderPrice = new ArrayList<String>();

    private OnItemClickListener onItemClickListener;



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view,int position);
    }


    private Context mContext;
    private List<String> mDishname;
    private List<Integer> mPrice;
    public RestaurantMenuAdapter(Context context,List<String> Dishname,List<Integer> Price){
        this.mContext = context;
        this.mDishname = Dishname;
        this.mPrice = Price;
    }
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    //呼叫將選擇的Checkbox設為被被選中會不選中
    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }
    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }
    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    @NonNull
    @Override
    public RestaurantMenuAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_restaurant_menu_item,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull final RestaurantMenuAdapter.LinearViewHolder holder, final int position) {
        holder.textView_dishname.setText(mDishname.get(position));
        holder.textView_price.setText("$"+mPrice.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isItemChecked(position)) {
                    setItemChecked(position, false);
//                    i=OrderDishname.indexOf(holder.textView_dishname.getText().toString());
                    OrderUsername.remove(OrderDishname.indexOf(holder.textView_dishname.getText().toString()));
                    OrderPrice.remove(OrderDishname.indexOf(holder.textView_dishname.getText().toString()));
                    OrderDishname.remove(OrderDishname.indexOf(holder.textView_dishname.getText().toString()));
                    holder.linearLayout_background.setBackgroundResource(R.drawable.restaurant_menu_design);
                } else {
                    setItemChecked(position, true);
                    OrderUsername.add(MainActivity.userName);
                    OrderDishname.add(holder.textView_dishname.getText().toString());
                    OrderPrice.add(holder.textView_price.getText().toString().substring(1));
                    holder.linearLayout_background.setBackgroundResource(R.drawable.restaurant_menu_design_selected);
                }
//                Toast.makeText(mContext,"click..."+position,Toast.LENGTH_SHORT).show();
            }
        });


//        if(position == 2){
//            holder.btnrr.setVisibility(View.INVISIBLE);
//        }
//        else holder.btnrr.setVisibility(View.VISIBLE);

    }
    public void addItem() {
        // 為了示範效果，固定新增在位置3。若要新增在最前面就把3改成0
        System.out.println("3");
    }
    @Override
    public int getItemCount() {
        return mDishname.size();
    }

    class LinearViewHolder extends RecyclerView.ViewHolder{

        private TextView textView_dishname;
        private TextView textView_price;
        private LinearLayout linearLayout_background;
//        private Button btnrr;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_dishname = itemView.findViewById(R.id.tv_restmenu_dishname);
            textView_price = itemView.findViewById(R.id.tv_restmenu_price);
            linearLayout_background = itemView.findViewById(R.id.tv_restmenu_background);
//            btnrr = itemView.findViewById(R.id.btnRemove);
        }
    }
}
