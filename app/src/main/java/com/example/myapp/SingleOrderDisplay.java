package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.Adapters.AdapterOrders;
import com.example.myapp.Adapters.AdapterProductsOrders;
import com.example.myapp.Adapters.AdapterUsers;
import com.example.myapp.Models.Client2;
import com.example.myapp.Shopping_Cart.Cart;
import com.example.myapp.Shopping_Cart.Cart_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SingleOrderDisplay extends AppCompatActivity {

    private RecyclerView recyclerview;
    private String clientID, clientEmail;
    private String orderTotalPrice, orderID;
    private String clientName;
    private String clientAddress;
    AdapterProductsOrders adapterProductsOrders;
    List<Cart> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EditText nameEdTxt, addressEdTxt, totalPriceEdTxt, emailEdTxt;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_order_display);

        //Action bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Order");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //init Recycler view
        recyclerview = findViewById(R.id.products_recyclerView);

        //set its properties
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(SingleOrderDisplay.this));

        //init user list
        productList = new ArrayList<>();

        nameEdTxt = findViewById(R.id.NameEdTxt);
        addressEdTxt = findViewById(R.id.AddressTv);
        totalPriceEdTxt = findViewById(R.id.PriceTv);
        emailEdTxt = findViewById(R.id.EmailTv);

        Intent intent = getIntent();
        clientID = intent.getStringExtra("clientID");
        clientEmail = intent.getStringExtra("clientEmail");
        clientAddress = intent.getStringExtra("clientAddress");
        orderTotalPrice = intent.getStringExtra("orderTotalPrice");
        orderID = intent.getStringExtra("oderID");
        clientName = intent.getStringExtra("ClientName");

        nameEdTxt.setText(clientName);
        addressEdTxt.setText(clientAddress);
        totalPriceEdTxt.setText("£" + orderTotalPrice);
        emailEdTxt.setText(clientEmail);

        //get current user
        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

        //Get path of database named  containing users info
        DatabaseReference ordersProductsRef = FirebaseDatabase.getInstance().getReference("Clients");

        //Get all data from path
        ordersProductsRef.child(clientID).child("Orders").child(orderID).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        productList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Cart product = ds.getValue(Cart.class);
                            //get all users except currelty sign in user
                            //if (!modelClient2.getUID().equals(fUser.getUid())){
                            productList.add(product);

                        }


                        //}
                        //adapter
                        adapterProductsOrders = new AdapterProductsOrders(SingleOrderDisplay.this, productList);
                        recyclerview.setAdapter(adapterProductsOrders);


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


}
