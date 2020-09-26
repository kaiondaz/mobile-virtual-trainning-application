package com.example.myapp.Shopping_Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class Product_Details extends AppCompatActivity {

    private FloatingActionButton addToCart;
    private ImageView productImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription, productName;

    //Firebase auth
    private FirebaseUser user;
    private FirebaseAuth auth;

    //Product ID
    //String productID;
    String EntryId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__details);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Product details");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Init firbase auth
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        //Init fields
        //productID = getIntent().getStringExtra("pid");
        addToCart= (FloatingActionButton) findViewById(R.id.add_product_to_cart_btn);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        productImage = (ImageView) findViewById(R.id.Product_image_details);
        productName = (TextView) findViewById(R.id.Product_name_details);
        productDescription = (TextView) findViewById(R.id.Product_description);
        productPrice = (TextView) findViewById(R.id.Product_price_details);

       /** Intent intent1=getIntent();
        productName.setText(intent1.getStringExtra("Product_name"));
        productPrice.setText(intent1.getStringExtra("Product_price"));
        productDescription.setText(intent1.getStringExtra("Product_description"));*/

       //Getting values from previous activity
        Bundle extras = getIntent().getExtras();
        int entryId = extras.getInt("RESOURCE_ID");
        String pName = extras.getString("Product_name");
        String pPrice = extras.getString("Product_price");
        String pDescription = extras.getString("Product_description");
        EntryId=""+entryId;

        //Set values
        Picasso.get().load(entryId).into(productImage);
        //productImage.setImageResource(entryId);
        productName.setText(pName);
        productPrice.setText("£"+pPrice);
        productDescription.setText(pDescription);


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddingToCartList();
            }
        });

    }

    private void AddingToCartList() {
        final String saveCurrentTime,saveCurrentDate,userID;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd YYYY");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart_Activity List");

        final HashMap <String, Object> cartMap = new HashMap <> ();
        cartMap.put("pid",EntryId);
        cartMap.put("pname",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount",EntryId);

        userID = user.getUid();

        cartListRef.child("User View").child(userID).child("Products").child(EntryId)
        .updateChildren(cartMap)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    cartListRef.child("Admin View").child(userID).child("Products").child(EntryId)
                            .updateChildren(cartMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Product_Details.this, "Added to cart List",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }

            }
        });


    }

    private void getProductDetails(String productID){


        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products ");
        productsRef.child(EntryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous activity
    }

}
