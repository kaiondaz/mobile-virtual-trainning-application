package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardActivityNutritionist extends AppCompatActivity {

    //Firebase authentication
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference2,UsersRef ;
    String currentUserID;
    FirebaseUser user;
    ActionBar actionBar;


    //views
    //TextView MprofileTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard2);



        //init
        firebaseAuth=FirebaseAuth.getInstance();

        //Action bar and its title
        actionBar=getSupportActionBar();
        user = firebaseAuth.getCurrentUser();
        currentUserID = user.getUid();
        //actionBar.setTitle("Profile");



        //home fragment transaction (default, on start)
        actionBar.setTitle("Home");//change action bar title
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction ft1 =getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Clients").child(currentUserID);
        databaseReference2 = firebaseDatabase.getReference("Coaches").child(currentUserID);





        //init views
        //MprofileTv=findViewById(R.id.profileTv);



        BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        //Handle item clicks
                        switch (item.getItemId()) {

                            case R.id.nav_home:
                                //home fragment transaction
                                actionBar.setTitle("Home");//change action bar title
                                HomeFragment fragment1 = new HomeFragment();
                                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                                ft1.replace(R.id.content, fragment1, "");
                                ft1.commit();
                                return true;

                            case R.id.nav_profile_coach:
                                //profile fragment transaction
                                /**
                                 actionBar.setTitle("Profile");//change action bar title
                                 Query query = CoachesRef.orderByChild("email").equalTo(user.getEmail());

                                 ProfileFragment fragment2 = new ProfileFragment();
                                 FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                                 ft2.replace(R.id.content, fragment2, "");
                                 ft2.commit();
                                 else {*/
                                actionBar.setTitle("Nutritionist profile");//change action bar title
                                NutritionistProfileFragment fragment2 = new NutritionistProfileFragment();
                                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                                ft2.replace(R.id.content, fragment2, "");
                                ft2.commit();

                                return true;

                            case R.id.nav_users:
                                //users fragment transaction
                                actionBar.setTitle("Users");//change action bar title
                                UsersFragment fragment3 = new UsersFragment();
                                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                                ft3.replace(R.id.content, fragment3, "");
                                ft3.commit();

                                return true;


                            case R.id.nav_coaches:
                                //users fragment transaction
                                actionBar.setTitle("Coaches");//change action bar title
                                CoachesFragment fragment4 = new CoachesFragment();
                                FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                                ft4.replace(R.id.content, fragment4, "");
                                ft4.commit();

                                return true;

                            case R.id.nav_nutritionists:
                                //users fragment transaction
                                actionBar.setTitle("Nutritionists");//change action bar title
                                NutritionistsFragment fragment5 = new NutritionistsFragment();
                                FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                                ft5.replace(R.id.content, fragment5, "");
                                ft5.commit();

                                return true;

                        }
                        return false;


                    }
                }; //BottomNavigation
        BottomNavigationView navigationView=findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);

    }







    private void checkUserStatus(){
        // get current user
        FirebaseUser user= firebaseAuth.getCurrentUser();
        if (user!=null) {
            //user is sign in stay here
            //set email of loggin in user
            //mProfileTv.setText(user.getEmail().toString().trim()) ;

            // Set username & email
            //textViewUsername.setText(user.getEmail().toString());
            // mProfileTv.setText(user.getEmail());
            //NavigationView navigationView = findViewById(R.id.nav_view);
            // View header = navigationView.getHeaderView(0);
            //TextView text = (TextView) header.findViewById(R.id.textView2);
            //text.setText(mProfileTv);}
        }

        else{
            //user not signed in, go to main activity
            startActivity(new Intent(DashboardActivityNutritionist.this,MainActivity.class));
            finish();

        }
    }










}
