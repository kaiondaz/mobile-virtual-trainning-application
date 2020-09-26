package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.myapp.Models.Client2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistrationWithGoogle extends AppCompatActivity {

    //Views
    EditText name;
    EditText address;
    EditText phoneNumber;
    Button registerButton;

    //Firebase
    DatabaseReference clientsReference;
    FirebaseUser user;
    FirebaseAuth auth;
    String userId;
    String userEmail;
    ImageButton profilePicToUpload;
    private int Gallery_intent = 2;
    StorageReference imagePath;
    FirebaseUser firebaseUser;
    String myUrl2;
    String pictureUrl;
    String downloadUrl;
    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_with_google);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Register");
        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        //Init views
        name = findViewById(R.id.nameTxtBox);
        address = findViewById(R.id.addressTxtBox);
        phoneNumber = findViewById(R.id.PhonenumberTxtBox);
        registerButton = findViewById(R.id.registerBtn);
        profilePicToUpload = findViewById(R.id.image);

        //init Firebase
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userId = user.getUid();
        userEmail = user.getEmail();
        clientsReference = FirebaseDatabase.getInstance().getReference("Clients");
        mStorageRef = FirebaseStorage.getInstance().getReference("User/"+userId+"/");


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client2 newClient = new Client2();
                newClient.setID(userId);
                newClient.setName(name.getText().toString().trim());
                newClient.setAddress(address.getText().toString().trim());
                newClient.setEmail(userEmail);
                newClient.setPhone(phoneNumber.getText().toString().trim());
                newClient.setOnlineStatus("1600438490364");
                newClient.setCoachID("Xe6Xy3XOCQW8aAbG2Yjxb9zFNd32");
                newClient.setNutritionistID("fg2DL0An9pe4Owpc5hMiYaQDKtc2");
                newClient.setImageAddress(downloadUrl);
                clientsReference.child(userId).setValue(newClient);


                Intent myIntent = new Intent(RegistrationWithGoogle.this, Subscription.class);
                RegistrationWithGoogle.this.startActivity(myIntent);
            }
        });

    }


    public void haveAccount(View v) {
        startActivity(new Intent(RegistrationWithGoogle.this, Login.class));
    }


    public void buttonImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, Gallery_intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_intent && resultCode == RESULT_OK) {

            Uri uri = data.getData();
            profilePicToUpload.setImageURI(uri);
            //firebaseDatabase = FirebaseDatabase.getInstance();
            FirebaseUser firebaseUser = auth.getCurrentUser();
            String id = firebaseUser.getUid();
            //StorageReference profilePictureRef = mStorageRef.child("User/");


            imagePath = FirebaseStorage.getInstance().getReference().child("Client2").child(id).child(uri.getLastPathSegment());
            mStorageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();
                            Toast.makeText(RegistrationWithGoogle.this, "Picture uploaded." + " " + "uri is " + downloadUrl.toString(), Toast.LENGTH_LONG).show();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


                    //Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                    String downloadUrl2 = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString().trim();
                    pictureUrl = downloadUrl2.toString().trim();
                    String myUrl2 = taskSnapshot.toString();
                    Toast.makeText(RegistrationWithGoogle.this, "Picture uploaded." + " " + "uri is " + mStorageRef.toString(), Toast.LENGTH_LONG).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegistrationWithGoogle.this, "Picture unable to upload  ", Toast.LENGTH_LONG).show();
                        }
                    });

            mStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    myUrl2 = task.toString();
                    Toast.makeText(RegistrationWithGoogle.this, "Picture uploaded." + " " + "uri is " + myUrl2, Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }
}
