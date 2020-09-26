package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class registration extends AppCompatActivity {
    //views
    EditText name;
    EditText address;
    EditText email;
    EditText createPassword;
    EditText phone;
    TextView mHaveAccount;
    Button register;

    FirebaseDatabase database;
    DatabaseReference ref;
    Client2 client;
    FirebaseAuth auth;
    private static final String TAG = "Registration";
    ImageButton imageButton;
    private int Gallery_intent=2;
    StorageReference imagePath;
    FirebaseUser firebaseUser;
    String myUrl2;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //init
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        email = (EditText) findViewById(R.id.email);
        phone= (EditText) findViewById(R.id.PhonenumberTxtBox);
        createPassword = (EditText) findViewById(R.id.createPassword);
        register = (Button) findViewById(R.id.Login);
        mHaveAccount=(TextView) findViewById(R.id.alreadyHaveAcc);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Clients");
        client = new Client2();
        auth = FirebaseAuth.getInstance();
        imageButton= (ImageButton) findViewById(R.id.image);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String id=firebaseUser.getUid();


        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Create account");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        //user = auth.getCurrentUser();








        //if (auth.getCurrentUser() != null) {
          //  Intent myIntent = new Intent(registration.this, MainActivity.class);
            //registration.this.startActivity(myIntent);
        //}
    }


    public void CreateAccount(){
        //input email and password
        String EMAIL;
        String PASSWORD;
        EMAIL=email.getText().toString().trim();;
        PASSWORD=createPassword.getText().toString().trim();
        //auth.createUserWithEmailAndPassword(EMAIL, PASSWORD);

        //Validate
        //if(!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()){
          //  //set error and focus to email edittext
            //EMAIL.setError("Invalid Email");
            //EMAIL.setFocusable(true);

        //}

        //else if (PASSWORD.length()<6){
          //  EMAIL.setError("Password lenght should contain at least 6 characters");
            //EMAIL.setFocusable(true);

      //  }

       // else {

        auth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            // setValuesToDatabase(client);
                            //user.getUid();
                            firebaseUser = auth.getCurrentUser();

                            String id=firebaseUser.getUid();
                            createNewClient(client);
                            ref.child(id).setValue(client);
                            Toast.makeText(registration.this,"Data inserted...",Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(registration.this, Subscription.class);
                            registration.this.startActivity(myIntent);
                            finish();


                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(registration.this,"Data inserted...",Toast.LENGTH_LONG).show();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(registration.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }



                        // ...
                    }
                });

            }
    //}


    public void createNewClient(Client2 client){
        String id=firebaseUser.getUid();
        client.setAddress(address.getText().toString().trim());
        client.setPhone(phone.getText().toString().trim());
        client.setCreatePassword(createPassword.getText().toString().trim());
        client.setEmail(email.getText().toString().trim());
        client.setName(name.getText().toString().trim());
        client.setOnlineStatus("Online");
        //FirebaseUser user = auth.getCurrentUser();
        //String id = user.getUid().toString().trim();
        client.setID(id);
        client.setImageAddress(myUrl2);


    }


        public void haveAccount(View v){
        startActivity(new Intent (registration.this, Login.class));

    }



    public void buttonImage(View view){

        Intent intent=  new Intent (Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,Gallery_intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_intent && resultCode == RESULT_OK){

            Uri uri =data.getData();
            imageButton.setImageURI(uri);
            //firebaseDatabase = FirebaseDatabase.getInstance();
            FirebaseUser firebaseUser = auth.getCurrentUser();
            String id=firebaseUser.getUid();
            imagePath= FirebaseStorage.getInstance().getReference().child("Client2").child(id).child(uri.getLastPathSegment());
            imagePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getUploadSessionUri();


                    String myUrl2= taskSnapshot.toString();



                    Toast.makeText(registration.this,"Picture uploaded."+" "+"uri is "+myUrl2,Toast.LENGTH_LONG).show();
                }
            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(registration.this,"Picture unable to upload  ",Toast.LENGTH_LONG).show();

                        }
                    });

            imagePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    myUrl2= task.toString();
                    Toast.makeText(registration.this,"Picture uploaded."+" "+"uri is "+myUrl2,Toast.LENGTH_LONG).show();



                }
            });

        }
    }


    public void register(View view){

        CreateAccount();





    }

   // public void updateUI(FirebaseUser currentUser){
            //String keyID=ref.push().getKey();
            //ref.child(keyID).setValue(client);

    //}

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }




}



