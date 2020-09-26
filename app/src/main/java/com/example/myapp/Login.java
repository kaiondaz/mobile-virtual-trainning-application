package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Models.Client2;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    //views
    EditText mEmailEt, mPasswordEt;
    TextView notHaveAccTv,mRecoverPassW;
    //Button mLoginBtn;
    SignInButton mGoogleLoginBtn;
    //ProgressDialog pd;

    //Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private static final int  RC_SIGN_IN=100;
    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "Login";
    DatabaseReference ref;
    Client2 client;
    FirebaseDatabase database;
    public FirebaseUser user;
    private String userEmail;
    FirebaseAuth auth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);


        //Before mAuth
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient=GoogleSignIn.getClient(this,gso);


        //In the onCreate() method, initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();


        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Login");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


        //init
       // firebaseAuth= FirebaseAuth.getInstance();
        mEmailEt=findViewById(R.id.email);
        mPasswordEt=findViewById(R.id.createPassword);
        mRecoverPassW=findViewById(R.id.recoverPassW);
        mGoogleLoginBtn=findViewById(R.id.GoogleSignInButton);
        //notHaveAccTv=findViewById(R.id.)
        //mLoginBtn=findViewById(R.id.login6);
        auth = FirebaseAuth.getInstance();
        user =auth.getCurrentUser();


        mGoogleLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {

                //Begin google login process
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent,RC_SIGN_IN);

            }
        }

    );}


    private void loginUser(String email,String passw){
        //show progress dialog
        //pd.show();

        mAuth.signInWithEmailAndPassword(email, passw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Dismiss progress dialog
                            //pd.dismiss();
                            //sign in success,update UI with the signed-in user's information
                            //user is logged in so start new activity
                            //Main3Activity profile = new Main3Activity(user);
                            user =auth.getCurrentUser();
                            userEmail=user.getEmail();
                            if (userEmail.contains("@my.coach")){
                            startActivity(new Intent(Login.this, DashboardActivityCoach.class));
                            finish();}

                           else  if (userEmail.contains("@my.nutrition")){
                                startActivity(new Intent(Login.this, DashboardActivityNutritionist.class));
                                finish();}

                            else if (userEmail.contains("@my.admin.com")){
                                startActivity(new Intent(Login.this, DashboardActivityAdmin.class));
                                finish();}

                            else {
                                startActivity(new Intent(Login.this, DashboardActivityClient.class));
                                finish();}

                        }
                    }

                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, get and show error message
                Toast.makeText(Login.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

                });
    }



    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login6:
                String EMAIL;
                String PASSWORD;
                EMAIL=mEmailEt.getText().toString().trim();;
                PASSWORD=mPasswordEt.getText().toString().trim();

                if (mEmailEt.length()==0){

                    mEmailEt.setError("Please enter your email");
                }

                else if (mPasswordEt.length()==0){
                    mPasswordEt.setError("Please enter your password");
                }

                else {
                    loginUser(EMAIL, PASSWORD);
                }
                break;


            case R.id.dontHaveAcc:
                Intent myIntent = new Intent(Login.this, Registration.class);
                // myIntent.putExtra("key", value); //Optional parameters
                Login.this.startActivity(myIntent);
                finish();
                //init progress dialog
                //pd= new ProgressDialog(this);
                //pd.setMessage("Loggin in...");
                break;

        }

    }




    //Recover password textview click
    public void recoverPassWord(View v) {
        showRecoverPasswordDialog();

    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Recover password");

        //set layout linear layout
        LinearLayout linearlayout= new LinearLayout(this);

        //Views to set on dialog
        final EditText emailEt= new EditText(this);
        emailEt.setHint("Enter email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        /*sets the mind width of an EditView to fit a text of n 'M' letters regardless of the actual text extension and size */
        emailEt.setMinEms(16);

        linearlayout.addView(emailEt);
        linearlayout.setPadding(10,10,10,10);

        builder.setView(linearlayout);

        //Buttons
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String Email=emailEt.getText().toString().trim();
                beginRecovery(Email);

            }
        });


        //Button cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();
            }
        });

        //Show dialog
        builder.create().show();


    }

    private void beginRecovery(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                Toast.makeText(Login.this,"Email sent",Toast.LENGTH_SHORT).show();
            }

                else{
                    Toast.makeText(Login.this,"Failded...Email not sent",Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous ativity
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();


                            //if user is sign in for the first time then get and show user info from google account
                            if (task.getResult().getAdditionalUserInfo().isNewUser()){

                                Intent myIntent = new Intent(Login.this, RegistrationWithGoogle.class);
                                Login.this.startActivity(myIntent);
                            }

                            else{

                            //Show user email in toast
                            Toast.makeText(Login.this,""+user.getEmail(),Toast.LENGTH_SHORT).show();

                            //Go to profile activity after sign in
                            //Main3Activity profile = new Main3Activity(user);
                            startActivity(new Intent(Login.this, DashboardActivityClient.class));
                            //finish();
                            //updateUI(user);

                            }
                        }

                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this,""+"Login failed...",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //get and show error message
                Toast.makeText(Login.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });}

        public void CreateAccount(){
            //input email and password
            String EMAIL;
            String PASSWORD;
            EMAIL=mEmailEt.getText().toString().trim();
            PASSWORD=mPasswordEt.getText().toString().trim();
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

            mAuth.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                Toast.makeText(Login.this,"Data inserted...",Toast.LENGTH_LONG).show();

                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }

                            // ...
                        }
                    });




        }
    public void createNewClient(Client2 client){
        //client.setAddress(address.getText().toString().trim());
        //client.setCreatePassword(createPassword.getText().toString().trim());
        client.setEmail(mEmailEt.getText().toString().trim());
        client.setName(mEmailEt.getText().toString().trim());
        FirebaseUser user = mAuth.getCurrentUser();
        String id = user.getUid();
        client.setID(id);
        //client.setImageAddress(imagePath.toString().trim());


    }




    }














