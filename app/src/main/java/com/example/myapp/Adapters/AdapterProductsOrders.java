package com.example.myapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Models.Review;
import com.example.myapp.R;
import com.example.myapp.Shopping_Cart.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterProductsOrders extends RecyclerView.Adapter<AdapterProductsOrders.MyViewHolder> {

    Context context;
    List<Cart> productList;
    String reviewerPic;

    DatabaseReference ClientReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String userID;


    public AdapterProductsOrders(Context c, List<Cart> ProductList) {
        context = c;
        productList = ProductList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_items_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtProductQuantity.setText("Quantity = " + productList.get(position).getQuantity());
        holder.txtProductPrice.setText("Price " + productList.get(position).getPrice());
        holder.txtProductName.setText(productList.get(position).getPname());

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductQuantity, txtProductPrice,txtProductName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductPrice = (TextView) itemView.findViewById(R.id.cart_product_price);
            txtProductName = (TextView) itemView.findViewById(R.id.cart_product_name);
            txtProductQuantity = (TextView) itemView.findViewById(R.id.cart_product_quantity);
        }


    }
}
