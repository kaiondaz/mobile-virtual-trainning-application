package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapp.Models.OrderModel;
import com.example.myapp.Models.Client2;
import com.example.myapp.Shopping_Cart.Cart_Activity;
import com.example.myapp.Shopping_Cart.Product_Details;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Supplements_Store extends AppCompatActivity {

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference CoachesRef, ClientsRef, OrdersRef;
    String currentUserID;
    String Name;
    String address;
    String currentUserName;
    FloatingActionButton fab;

    ListView listView;
    String mtitle []={"BCA","Creatine performance","Creatine ","Aswagandha","Omega set fish oil","Protein whey set","Creatine monohydrate"};
    String mDescription [] = {"BCA ","Creatine performance "," Creatine ",
                            "Aswagandha ","Omega set fish oil ","Protein whey set ","Creatine monohydrate  "
                              };
    int images []={R.drawable.store_bca2_supp,R.drawable.store_creatine_performance_supp,R.drawable.store_creatine_supp,
                   R.drawable.store_aswagandha,R.drawable.store_omegaset_supp,R.drawable.store_whey_set,R.drawable.store_creatinemono_supp};
    int prices []={15,20,25,10,12,30,40};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplements);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        ClientsRef =firebaseDatabase.getReference("Clients");
        OrdersRef =firebaseDatabase.getReference("Clients").child(currentUserID).child("Orders");


        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Store");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Clients name and address from database clientsReference
        ClientsRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())

                {
                    Client2 client=dataSnapshot1.getValue(Client2.class);
                    if(!(client.getUID()==null)){
                        //Toast.makeText(CoachClients.this,"CoachID "+client.getCoachID() ,Toast.LENGTH_LONG).show();
                        if(client.getUID().equals(currentUserID)){

                            Name=client.getName().trim();
                            address=client.getAddress().trim();



                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Supplements_Store.this, Cart_Activity.class);
                startActivity(intent);
            }
        });


        listView = findViewById(R.id.listview);
        //now create an adapter class
        final MyAdapter adapter = new MyAdapter(this, mtitle, mDescription, images,prices);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                }
                if (position==1){
                }
                if (position==2){
                }
                if (position==3){
                }
                if (position==4){
                }
                if (position==5){
                }
                if (position==6){
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void BuyProduct (final String productName, final String productPrice, final String Description){

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();

        //create new order model
        OrderModel orderModel = new OrderModel();

        //set order model properties
        orderModel.setDate(date.toString().trim());
        orderModel.setProduct(productName);
        orderModel.setProductDescription(Description);
        orderModel.setPrice(productPrice);

        ClientsRef.child(currentUserID).child("Orders").push().setValue(orderModel);

    }


    private void AlertMessage(){


        AlertDialog.Builder alert = new AlertDialog.Builder(Supplements_Store.this);
        alert.setTitle("Buy Product?");
        alert.setMessage("Message : Buy Product?");

        // Set an EditText view to get user input
        final EditText input = new EditText(Supplements_Store.this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(DialogInterface dialog, int whichButton) {

                /**BuyProduct(productName,productPrice,productDescription);
                Toast.makeText(Supplements_Store.this,"Thank you, your order was placed.",Toast.LENGTH_LONG).show();*/


                return;
            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        return;
                    }
                });
        alert.show();


    }

    class MyAdapter extends ArrayAdapter<String> {
            Context context;
            String rTitle[];
            String rDescription[];
            int rImgs[];
            int rPrices[];
            Button addToCart;


            MyAdapter (Context c, String title[], String description[], int imgs[], int prices[]) {
                super(c,R.layout.supplements_row,R.id.nutritionPlanTextV,title);
                this.context=c;
                this.rTitle=title;
                this.rDescription=description;
                this.rImgs=imgs;
                this.rPrices=prices;
            }

            @NonNull
            @Override
            public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater layoutInflater =(LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row=layoutInflater.inflate(R.layout.supplements_row,parent,false);
                ImageView images = row.findViewById(R.id.image);
                TextView myTitle = row.findViewById(R.id.nutritionPlanTextV);
                TextView myDescription = row.findViewById(R.id.DateTxV);
                TextView price = row.findViewById(R.id.price);


                //now set image resources on views
                images.setImageResource(rImgs[position]);
                myTitle.setText(rTitle[position]);
                myDescription.setText(rDescription[position]);
                price.setText("Price: "+"£"+rPrices[position]);
                addToCart = row.findViewById(R.id.addToCart);
                addToCart.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {

                        /**Intent intent =new Intent(Supplements_Store.this, Product_Details.class);
                        intent.putExtra("Product_name",rTitle[position]);
                        intent.putExtra("Product_price", ""+rPrices[position]);
                        intent.putExtra("Product_description",rDescription[position]);
                        intent.putExtra("resourceInt", rImgs[position]);
                        Supplements_Store.this.startActivity(intent);*/

                        final Intent prouctDetails = new Intent(Supplements_Store.this, Product_Details.class);
                        prouctDetails.putExtra("RESOURCE_ID", rImgs[position]);
                        prouctDetails.putExtra("Product_name",rTitle[position]);
                        prouctDetails.putExtra("Product_price", ""+rPrices[position]);
                        prouctDetails.putExtra("Product_description",rDescription[position]);
                        startActivity(prouctDetails);
                        //AlertMessage();
                        //BuyProduct(rTitle[position],rPrices[position]+"",rDescription[position]);

                    }
                });
                return row;

            }
        }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous activity
    }

}




