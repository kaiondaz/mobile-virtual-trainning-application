package com.example.myapp.Shopping_Cart;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.Create_Reviews;
import com.example.myapp.Models.OrderModel;
import com.example.myapp.R;
import com.example.myapp.Registration;
import com.example.myapp.Subscription;
import com.example.myapp.Supplements_Store;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, cityEditText;
    private Button confirmOrderBtn;
    private  String totalAmount = "";
    private List <Cart>productList;

    //Firebase
    DatabaseReference clientsReference,clientsReference2;
    FirebaseUser user;
    FirebaseAuth auth;
    String userId;
    String userEmail;

    public ConfirmFinalOrderActivity(List<Cart> ProductList){
        productList = ProductList;
    }

    public ConfirmFinalOrderActivity(){
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Confirm order");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        totalAmount = getIntent().getStringExtra("Total Price");
        final ArrayList<Cart> productList = (ArrayList<Cart>) getIntent().getSerializableExtra("productList");


        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_button);
        nameEditText = (EditText) findViewById(R.id.shipment_name);
        phoneEditText = (EditText) findViewById(R.id.shipment_phone_number);
        addressEditText = (EditText) findViewById(R.id.shipment_address);
        cityEditText = (EditText) findViewById(R.id.shipment_city_name);

        //init Firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();
        clientsReference = FirebaseDatabase.getInstance().getReference("Clients").child(userId).child("Orders");
        clientsReference2 = FirebaseDatabase.getInstance().getReference("Clients").child(userId).child("Orders");


        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderModel order = new OrderModel();
                order.setClientAddress(addressEditText.getText().toString().trim());
                order.setClientName(nameEditText.getText().toString().trim());
                order.setPrice(totalAmount);

                //Get and store order ID
                String orderId = clientsReference.push().getKey();
                order.setOrderID(orderId);

                //Get and store order date
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                order.setDate(date.toString().trim());

                //Store order reference
                clientsReference.child(orderId).setValue(order);
                clientsReference2.child(orderId).child("Products").setValue(productList);
                Toast.makeText(ConfirmFinalOrderActivity.this,"Your order has been placed, thank you.",Toast.LENGTH_LONG).show();

                //Get back to supplement store
                Intent myIntent = new Intent(ConfirmFinalOrderActivity.this, Supplements_Store.class);
                ConfirmFinalOrderActivity.this.startActivity(myIntent);
                finish();

            }
        });



    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous activity
    }
}
