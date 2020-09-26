package com.example.myapp.Shopping_Cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Adapters.AdapterUsers;
import com.example.myapp.Models.Client2;
import com.example.myapp.R;
import com.example.myapp.Supplements_Store;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextProcessBtn;
    private TextView txtTotalAmount;
    private int overTotalPrice = 0;
    private List<Cart> productList;


    //Firebase auth
    FirebaseAuth auth;
    FirebaseUser user;
    String uID;

    //FirebaseDatabase
    DatabaseReference userViewReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Action bar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cart Items");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = (RecyclerView) findViewById(R.id.CartList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        nextProcessBtn = (Button) findViewById(R.id.button_next);
        txtTotalAmount = (TextView) findViewById(R.id.total_price);

        auth = FirebaseAuth.getInstance();
        uID = auth.getCurrentUser().getUid().toString();

        productList = new ArrayList<>();

        userViewReference = FirebaseDatabase.getInstance().getReference("Cart_Activity List").child("User View").child(uID).child("Products");

        nextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get all data from path
                userViewReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (!(productList == null)) {
                            productList.clear();
                        }

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Cart product = ds.getValue(Cart.class);
                            //get all users except currently sign in user
                            //if (!modelClient2.getUID().equals(fUser.getUid())){
                            productList.add(product);
                            //}
                        }

                        txtTotalAmount.setText("Total price = £" + String.valueOf(overTotalPrice));
                        Intent intent = new Intent(Cart_Activity.this, ConfirmFinalOrderActivity.class);
                        intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                        intent.putExtra("productList", (Serializable) productList);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart_Activity List");
        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View").child(uID).child("Products"), Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter =
                new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull final Cart cart) {

                        cartViewHolder.txtProductQuantity.setText("Quantity = " + cart.getQuantity());
                        cartViewHolder.txtProductPrice.setText("Price " + cart.getPrice());
                        cartViewHolder.txtProductName.setText(cart.getPname());

                        int oneTypeProductPrice = (Integer.valueOf(cart.getPrice().replace("£", ""))) * Integer.valueOf(cart.getQuantity());
                        overTotalPrice = overTotalPrice + oneTypeProductPrice;
                        txtTotalAmount.setText("Total price = £" + String.valueOf(overTotalPrice));


                        cartViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                CharSequence options[] = new CharSequence[]
                                        {
                                                "Edit",
                                                "Remove"
                                        };
                                AlertDialog.Builder builder = new AlertDialog.Builder(Cart_Activity.this);
                                builder.setTitle("Cart options");

                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (which == 0) {
                                            Intent intent = new Intent(Cart_Activity.this, Product_Details.class);
                                            intent.putExtra("RESOURCE_ID", Integer.parseInt(cart.getPid()));
                                            intent.putExtra("Product_name", cart.getPname());
                                            intent.putExtra("Product_price", "" + cart.getPrice().replace("£", ""));
                                            intent.putExtra("Product_description", cart.getPname());
                                            startActivity(intent);

                                        }


                                        if (which == 1) {
                                            cartListRef.child("User View")
                                                    .child(uID)
                                                    .child("Products")
                                                    .child(cart.getPid())
                                                    .removeValue()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(Cart_Activity.this, "Item removed successfully.", Toast.LENGTH_LONG).show();

                                                                Intent intent = new Intent(Cart_Activity.this, Cart_Activity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                                builder.show();

                            }
                        });

                    }

                    @NonNull
                    @Override
                    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous activity
    }


}
