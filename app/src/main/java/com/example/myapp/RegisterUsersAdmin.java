package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Models.Coach;
import com.example.myapp.Models.Nutritionist;
import com.example.myapp.Models.Client2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class RegisterUsersAdmin extends AppCompatActivity {
    String userType;

    //Firebase fields
    FirebaseDatabase database;
    DatabaseReference coachRef, nutritionistRef;
    Client2 client;
    FirebaseAuth auth;
    private static final String TAG = "Registration";
    ImageButton imageButton;
    private int Gallery_intent=2;
    StorageReference imagePath;
    FirebaseUser firebaseUser;
    String myUrl2;

    //views
    EditText name;
    EditText address;
    EditText email;
    EditText createPassword;
    EditText phone;
    TextView mHaveAccount;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_users_admin);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Register user");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //init
        name = (EditText) findViewById(R.id.nameTxtBox);
        address = (EditText) findViewById(R.id.addressTxtBox);
        email = (EditText) findViewById(R.id.email);
        phone= (EditText) findViewById(R.id.PhonenumberTxtBox);
        createPassword = (EditText) findViewById(R.id.createPassword);
        register = (Button) findViewById(R.id.registerBtn);
        mHaveAccount=(TextView) findViewById(R.id.alreadyHaveAcc);
        database = FirebaseDatabase.getInstance();
        coachRef = database.getReference("Coaches");
        nutritionistRef = database.getReference("Nutritionists");
        client = new Client2();
        auth = FirebaseAuth.getInstance();
        imageButton= (ImageButton) findViewById(R.id.image);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String id=firebaseUser.getUid();
        client=new Client2();


        Intent intent = getIntent();
        userType = intent.getStringExtra("userType");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (userType) {

                    case "Coach":
                        aadCoach();
                        break;

                    case "Nutritionist":
                        addNutritionist();
                        break;

                }



            }
        });



    }

    private void addNutritionist() {
        //input email and password
        String EMAIL;
        String PASSWORD;
        EMAIL=email.getText().toString().trim();;
        PASSWORD=createPassword.getText().toString().trim();
        //auth.createUserWithEmailAndPassword(EMAIL, PASSWORD);

        //Validating entries
        if (name.length()==0){

            name.setError("Please enter your name");
        }

        else if (address.length()==0){
            address.setError("Please enter an address");
        }

        else if (phone.length()==0){
            phone.setError("Please create a password");
        }

        else if (email.length()==0){
            email.setError("Please enter an email");
        }

        else if (createPassword.length()==0){
            createPassword.setError("Please create a password");
        }



        else{

        auth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // setValuesToDatabase(client);
                            //user.getUid();


                            Nutritionist nutritionist = new Nutritionist();
                            firebaseUser = auth.getCurrentUser();
                            nutritionist.setEmail(email.getText().toString().trim());
                            nutritionist.setUserType("Nutritionist");
                            nutritionist.setName(name.getText().toString().trim());
                            nutritionist.setAddress(address.getText().toString().trim());
                            nutritionist.setPhone(phone.getText().toString().trim());
                            nutritionist.setCreatePassword(createPassword.getText().toString().trim());

                            String id=firebaseUser.getUid();
                           // createNewClient(nutritionist);
                            nutritionistRef.child(id).setValue(nutritionist);
                            Toast.makeText(RegisterUsersAdmin.this,"User has been registered...",Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(RegisterUsersAdmin.this, DashboardActivityAdmin.class);
                            RegisterUsersAdmin.this.startActivity(myIntent);
                            finish();


                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterUsersAdmin.this,"Data inserted...",Toast.LENGTH_LONG).show();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterUsersAdmin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }



                        // ...
                    }
                });
        }



    }

    private void aadCoach() {
        //input email and password
        String EMAIL;
        String PASSWORD;
        EMAIL=email.getText().toString().trim();;
        PASSWORD=createPassword.getText().toString().trim();
        //auth.createUserWithEmailAndPassword(EMAIL, PASSWORD);

        auth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // setValuesToDatabase(client);
                            //user.getUid();
                            firebaseUser = auth.getCurrentUser();
                            Coach coach = new Coach();
                            coach.setEmail(email.getText().toString().trim());
                            coach.setUserType("Coach");
                            coach.setName(name.getText().toString().trim());
                            coach.setAddress(address.getText().toString().trim());
                            coach.setPhone(phone.getText().toString().trim());
                            coach.setCreatePassword(createPassword.getText().toString().trim());
                            String id=firebaseUser.getUid();
                            //createNewClient(coach);
                            coachRef.child(id).setValue(coach);
                            Toast.makeText(RegisterUsersAdmin.this,"User has been registered...",Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(RegisterUsersAdmin.this, DashboardActivityAdmin.class);
                            RegisterUsersAdmin.this.startActivity(myIntent);
                            finish();


                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterUsersAdmin.this,"Data inserted...",Toast.LENGTH_LONG).show();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterUsersAdmin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }



                        // ...
                    }
                });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous activity
    }


}
