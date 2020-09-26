package com.example.myapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Models.OrderModel;
import com.example.myapp.R;
import com.example.myapp.SingleOrderDisplay;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.MyHolder> {

    Context context;
    List<OrderModel> ordersList;
    String Useremail;
    DatabaseReference clientstsRef;
    String clientID;
    String userType = "nutritionist";


    //Constructor
    public AdapterOrders(Context context, List<OrderModel> OrdersList, String Email, DatabaseReference ref, String ClientID) {
        this.context = context;
        this.ordersList = OrdersList;
        this.Useremail = Email;
        this.clientstsRef = ref;
        clientID = ClientID;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.orders_row, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //get data
        final String name = ordersList.get(position).getClientName();
        String date = ordersList.get(position).getDate();
        final String totalPrice = ordersList.get(position).getPrice();
        final String orderID = ordersList.get(position).getOrderID();
        final String Address = ordersList.get(position).getClientAddress();

        //set data
        holder.nameTv.setText(name);
        holder.orderDateTv.setText(date);

        //handle item click
        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleOrderDisplay.class);
                intent.putExtra("clientEmail", Useremail);
                intent.putExtra("ClientName", name);
                intent.putExtra("clientID", clientID);
                intent.putExtra("clientAddress", Address);
                intent.putExtra("orderTotalPrice", totalPrice);
                intent.putExtra("oderID", orderID);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTv, orderDateTv;
        Button viewOrder;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
            orderDateTv = itemView.findViewById(R.id.orderDateTv);
            viewOrder = (Button) itemView.findViewById(R.id.viewOrder);


        }
    }
}
