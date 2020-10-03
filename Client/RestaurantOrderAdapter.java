package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantOrderAdapter extends RecyclerView.Adapter<RestaurantOrderAdapter.LinearViewHolder> {

    private Context mContext;
    private List<String> mUsername;
    private List<String> mDishname;
    private List<Integer> mPrice;

    public RestaurantOrderAdapter(Context context,List<String> Username,List<String> Dishname, List<Integer> Price){
        this.mContext=context;
        this.mUsername=Username;
        this.mDishname=Dishname;
        this.mPrice=Price;
    }

    @NonNull
    @Override
    public RestaurantOrderAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_restaurant_order_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantOrderAdapter.LinearViewHolder holder, int position) {
        holder.textView_username.setText(mUsername.get(position));
        holder.textView_dishname.setText(mDishname.get(position));
        holder.textView_price.setText("$"+mPrice.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mUsername.size();
    }
    class LinearViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_username;
        private TextView textView_dishname;
        private TextView textView_price;

        //        private Button btnrr;
        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_username = itemView.findViewById(R.id.tv_restorder_username);
            textView_dishname = itemView.findViewById(R.id.tv_restorder_dishname);
            textView_price = itemView.findViewById(R.id.tv_restorder_price);
//            btnrr = itemView.findViewById(R.id.btnRemove);
        }
    }
}
