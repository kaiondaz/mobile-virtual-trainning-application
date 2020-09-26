package com.example.myapp;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.Models.Coach;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class NutritionistProfileFragment extends Fragment {
    //Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,UsersRef ;
    String currentUserID;


    //Views from xml
    TextView nameTv;
    TextView emailTv;

    ImageView AvatarIv;
    String Name;
    View profileView;
    Button Mydetails;
    Button MyClients;
    private RecyclerView myProfileInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.coach_profile_fragment, container, false);

        nameTv= view.findViewById(R.id.nameTv);
        emailTv= view.findViewById(R.id.emailTv);
        Mydetails= view.findViewById(R.id.myDetails);
        MyClients= view.findViewById(R.id.MyClients);
        AvatarIv=view.findViewById(R.id.profileIv);




        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        currentUserID = user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Nutritionists").child(currentUserID);



        //Method to change user details
        Mydetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view) {

                Intent myIntent = new Intent(getActivity(), NutritionistProfileDetails.class);
                // myIntent.putExtra("key", value); //Optional parameters
                NutritionistProfileFragment.this.startActivity(myIntent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

            }});


        MyClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(getActivity(),NutritionistClients.class);
                // myIntent.putExtra("key", value); //Optional parameters
                NutritionistProfileFragment.this.startActivity(myIntent);
                ((Activity) getActivity()).overridePendingTransition(0, 0);

            }
        });

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        databaseReference.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        //check until required data get
                                                        // This method is called once with the initial value and again
                                                        // whenever data at this location is updated.
                                                        Coach coach = dataSnapshot.getValue(Coach.class);
                                                        //Toast.makeText(getContext(), "SUCCESS"+client.getName(), Toast.LENGTH_SHORT).show();

                                                        nameTv.setText(coach.getName());
                                                        emailTv.setText(coach.getEmail());
                                                        String link=coach.getImageAddress();


                                                        try{
                                                            Picasso.get().load(link).fit().into(AvatarIv);
                                                            //Toast.makeText(getContext(), link, Toast.LENGTH_LONG).show();

                                                            Picasso.Builder builder = new Picasso.Builder(getContext());
                                                            builder.listener(new Picasso.Listener() {


                                                                @Override
                                                                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                                                                    exception.printStackTrace();
                                                                };

                                                            });
                                                        }

                                                        catch(Exception e) {
                                                            //if there is any execption while geting the image  then set default
                                                            Picasso.get().load(R.mipmap.ic_add_image).into(AvatarIv);
                                                        }

                                                        // Log.d(TAG, "Value is: " + value);
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }


                                                    // try{
                                                    //       Picasso.get().load(Image).into(avatarIv);
                                                    // }

                                                    //catch(Exception e) {
                                                    //if there is any execption while geting the image  then set default
                                                    //  Picasso.get().load(R.mipmap.ic_add_image).into(avatarIv);
                                                    //}}}

                                                    //  @Override
                                                    //public void onCancelled(@NonNull DatabaseError databaseError) {
                                                    //  Toast.makeText(LOOOOOOOOOOL.this,"NOOOOOT working",Toast.LENGTH_LONG).show();


                                                    //}


                                                }






        );

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
            Intent myIntent = new Intent(getActivity(), Login.class);
            // myIntent.putExtra("key", value); //Optional parameters
            getActivity().startActivity(myIntent);        }

        return super.onOptionsItemSelected(item);
    }




}
