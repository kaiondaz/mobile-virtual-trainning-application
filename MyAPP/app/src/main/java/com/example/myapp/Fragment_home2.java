package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class Fragment_home2 extends Fragment {

    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    com.google.firebase.database.FirebaseDatabase FirebaseDatabase;
    DatabaseReference Databasereference;

    //Views from xml
    ImageView avatarIv;
    TextView nameTv, emailTv;

    public Fragment_home2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        //init Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        FirebaseDatabase= com.google.firebase.database.FirebaseDatabase.getInstance();
        Databasereference=FirebaseDatabase.getReference("Clients");

        //init Views
        avatarIv= view.findViewById(R.id.homeTv);
        nameTv=view.findViewById(R.id.NameTv);
        emailTv= view.findViewById(R.id.EmailTv);


        Query query =Databasereference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Check until required data get
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    //Get data
                    String name = "" + ds.child("name").getValue();
                    //String address= ""+ ds.child("address").getValue();
                    String email = "" + ds.child("email").getValue();
                    //String image = "" + ds.child("imageAddress").getValue();
                    //System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww"+name);

                    //set Data
                    nameTv.setText(name);
                    emailTv.setText(email);

                    try {
                        //If image is received then set into imageView
                        // Picasso.get().load(image).into(avatarIv);
                    } catch (Exception e) {
                        //If there is an exception getting then set to default
                        // Picasso.get().load(R.mipmap.ic_add_image).into(avatarIv);


                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
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
            startActivity(new Intent(getActivity(),MainActivity.class));
            getActivity().finish();

        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);//to show menu
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflating menu
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    /*handle menu clicks */

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        //get item id
        int id= item.getItemId();
        if(id==R.id.action_logout){
            firebaseAuth.signOut();
        }

        return super.onOptionsItemSelected(item);
    }


}
