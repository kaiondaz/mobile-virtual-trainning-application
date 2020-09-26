package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    //Firebase auth
    FirebaseAuth firebaseAuth;
    private AppBarConfiguration mAppBarConfiguration;

    //Views
    //TextView mProfileTv;
    NavigationView mNavigationView;
    View mHeaderView;

    TextView textViewUsername;
    TextView textViewEmail;
    private FirebaseAuth mAuth;


    FirebaseUser user2;

    public Main3Activity(FirebaseUser user){
        user2=user;


    }

    public Main3Activity()
    {}




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);

        // NavigationView Header
        mHeaderView =  navigationView.getHeaderView(0);

        mAuth = FirebaseAuth.getInstance();

        // View
        FirebaseUser user = mAuth.getCurrentUser();

        textViewUsername = (TextView) mHeaderView.findViewById(R.id.DateTxV);

        textViewUsername.setText(user.getEmail().toString());



       // mProfileTv = (TextView) navigationView.getHeaderView(0).findViewById(R.id.textView2);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //init
        firebaseAuth=FirebaseAuth.getInstance();

        //init Views
        //mProfileTv =findViewById(R.id.textView2);

    }


    private void checkUserStatus(){
        // get current user
        FirebaseUser user= firebaseAuth.getCurrentUser();
        if (user!=null) {
            //user is sign in stay here
            //set email of loggin in user
            //mProfileTv.setText(user.getEmail().toString().trim()) ;

            // Set username & email
            textViewUsername.setText(user.getEmail().toString());
           // mProfileTv.setText(user.getEmail());
            //NavigationView navigationView = findViewById(R.id.nav_view);
            // View header = navigationView.getHeaderView(0);
            //TextView text = (TextView) header.findViewById(R.id.textView2);
            //text.setText(mProfileTv);}
        }

        else{
            //user not signed in, go to main activity
            startActivity(new Intent(Main3Activity.this,MainActivity.class));
            finish();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main3, menu);
      //  getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /*inflate options menu*/

    //@Override
    //public boolean onCreateOptionsMenu (Menu menu){
        //inflating menu
      //  getMenuInflater().inflate(R.menu.menu_main,menu);
        //return super.onCreateOptionsMenu(menu);
    //}


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //get item id
        int id=item.getItemId();

        if (id==R.id.action_logout){

            firebaseAuth.signOut();
            checkUserStatus();
        }

        return super.onOptionsItemSelected(item);
    }
}




