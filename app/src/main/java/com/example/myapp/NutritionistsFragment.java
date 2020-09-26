package com.example.myapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.myapp.Adapters.AdapterCoaches;
import com.example.myapp.Adapters.AdapterNutritionists;
import com.example.myapp.Models.Client2;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NutritionistsFragment extends Fragment {
    RecyclerView recyclerview;
    AdapterNutritionists adapterNutritionists;
    List<Client2>   NutritionistsList;
    FirebaseUser fUser;
    String userEmail;
    String userID;



    //Firebase authentication
    FirebaseAuth firebaseAuth;


    public NutritionistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_nutritionists, container, false);

        //init FirebaseAuth
        firebaseAuth=FirebaseAuth.getInstance();


        //get current user
        fUser= firebaseAuth.getCurrentUser();

        userEmail=fUser.getEmail().toString().trim();

        userID= fUser.getUid().toString().trim();


        //init Recycler view
        recyclerview= view.findViewById(R.id.myRecycler);

        //init user list
        NutritionistsList =new ArrayList<>();

        //get all users
        getAllusers();



        //set its properties
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    private void getAllusers() {
        //get current user
        final FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();

        //Get path of database named  containing users info
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Nutritionists");
        final DatabaseReference nutritionistsRef = FirebaseDatabase.getInstance().getReference("Nutritionists");


        //Get all data from path
        ref2.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                NutritionistsList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Client2 modelClient2 =ds.getValue(Client2.class);

                    //get all users except currelty sign in user
                    //if (!modelClient2.getUID().equals(fUser.getUid())){
                    NutritionistsList.add(modelClient2);
                    //}

                    //adapter
                    adapterNutritionists= new AdapterNutritionists(getActivity(), NutritionistsList, userEmail,nutritionistsRef,userID);
                    recyclerview.setAdapter(adapterNutritionists);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void searchUsers( final String query) {
        //get current user
        final FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();

        //Get path of database named  containing users info
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Nutritionists");
        final DatabaseReference coachesRef = FirebaseDatabase.getInstance().getReference("Nutritionists");


        //Get all data from path
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                NutritionistsList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Client2 modelClient2 =ds.getValue(Client2.class);

                    //get all users except currently sign in user//if (!modelClient2.getUID().equals(fUser.getUid())){

                    if (!(modelClient2.getName() == null)){
                    if(modelClient2.getName().toLowerCase().contains(query.toLowerCase()) || modelClient2.getEmail().toLowerCase().contains(query.toLowerCase())){

                        NutritionistsList.add(modelClient2);

                    }
                    // }

                }

                //adapter
                adapterNutritionists= new AdapterNutritionists(getActivity(), NutritionistsList,userEmail,coachesRef,userID);
                //Refresh adapter
                adapterNutritionists.notifyDataSetChanged();
                recyclerview.setAdapter(adapterNutritionists);

            }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



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

        //Search view
        MenuItem item =menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(item);

        //Search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //called when the user presses the search button on keyboard
                //if search query is not empty then search
                if (!TextUtils.isEmpty(s.trim())){
                    //search text contains text search it
                    searchUsers(s);


                }

                else {

                    getAllusers();
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //called when the user presses any single letter
                //if search query is not empty then search
                if (!TextUtils.isEmpty(s.trim())){
                    //search text contains text search it
                    searchUsers(s);


                }

                else {

                    getAllusers();
                }


                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }


    /*handle menu clicks */
    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        //get item id
        int id= item.getItemId();
        if(id==R.id.action_logout){
            //firebaseAuth.signOut();
            Intent myIntent = new Intent(getActivity(), Login.class);
            // myIntent.putExtra("key", value); //Optional parameters
            getActivity().startActivity(myIntent);

        }

        return super.onOptionsItemSelected(item);
    }


}








