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

import com.example.myapp.Models.Client2;
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

public class Registration extends AppCompatActivity {
    //views
    EditText name;
    EditText address;
    EditText email;
    EditText createPassword;
    EditText phone;
    TextView mHaveAccount;
    Button register;

    //Firebase fields
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
    String pictureUrl;
    String downloadUrl;
    private StorageReference mStorageRef;

    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //init
        name = (EditText) findViewById(R.id.nameTxtBox);
        address = (EditText) findViewById(R.id.addressTxtBox);
        email = (EditText) findViewById(R.id.email);
        phone= (EditText) findViewById(R.id.PhonenumberTxtBox);
        createPassword = (EditText) findViewById(R.id.createPassword);
        register = (Button) findViewById(R.id.registerBtn);
        mHaveAccount=(TextView) findViewById(R.id.alreadyHaveAcc);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Clients");
        client = new Client2();
        auth = FirebaseAuth.getInstance();
        imageButton= (ImageButton) findViewById(R.id.image);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        String id=firebaseUser.getUid();
        client=new Client2();
        mStorageRef = FirebaseStorage.getInstance().getReference("User/"+id+"/");


        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Create account");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

    }


    public void CreateAccount(){

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

        //Input email and password
        String EMAIL;
        String PASSWORD;
        EMAIL=email.getText().toString().trim();;
        PASSWORD=createPassword.getText().toString().trim();


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
                            Toast.makeText(Registration.this,"Data inserted...",Toast.LENGTH_LONG).show();
                            Intent myIntent = new Intent(Registration.this, Subscription.class);
                            Registration.this.startActivity(myIntent);
                            finish();


                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(Registration.this,"Data inserted...",Toast.LENGTH_LONG).show();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registration.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                    }
                });
        }

            }



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

        client.setImageAddress(downloadUrl);
        Toast.makeText(Registration.this,"Picture uploaded."+" "+pictureUrl,Toast.LENGTH_LONG).show();



    }


    public void haveAccount(View v){
        startActivity(new Intent (Registration.this, Login.class));
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
            //StorageReference profilePictureRef = mStorageRef.child("User/");


            imagePath= FirebaseStorage.getInstance().getReference().child("Client2").child(id).child(uri.getLastPathSegment());
            mStorageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                            Toast.makeText(Registration.this,"Picture uploaded."+" "+"uri is "+downloadUrl.toString(),Toast.LENGTH_LONG).show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                    //Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                    String downloadUrl2 = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString().trim();
                    pictureUrl = downloadUrl2.toString().trim();
                    String myUrl2= taskSnapshot.toString();
                    Toast.makeText(Registration.this,"Picture uploaded."+" "+"uri is "+mStorageRef.toString(),Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this,"Picture unable to upload  ",Toast.LENGTH_LONG).show();
                        }
                    });

            mStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    myUrl2= task.toString();
                    Toast.makeText(Registration.this,"Picture uploaded."+" "+"uri is "+myUrl2,Toast.LENGTH_LONG).show();
                }
            });

        }
    }


    public void register(View view){
        CreateAccount();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


}



