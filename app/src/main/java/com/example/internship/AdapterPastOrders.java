package com.example.internship;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPastOrders extends RecyclerView.Adapter<AdapterPastOrders.MyViewHolder> {

    Context context;

    public AdapterPastOrders(Context context, ArrayList<PastOrderList> list) {
        this.context = context;
        this.list = list;
    }

    ArrayList<PastOrderList> list;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.past_orders_card,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PastOrderList pastOrderList = list.get(position);
        holder.donationName.setText(pastOrderList.getIdentity());
        holder.quantity.setText(pastOrderList.getQuantity());
        holder.desc.setText(pastOrderList.getDesc());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView donationName,quantity,desc;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            donationName=itemView.findViewById(R.id.donationName);
            quantity=itemView.findViewById(R.id.quantity);
            desc=itemView.findViewById(R.id.FoodDesc);

        }

    }

}
