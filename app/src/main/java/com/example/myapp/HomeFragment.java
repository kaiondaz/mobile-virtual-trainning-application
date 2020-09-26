package com.example.myapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ImageView shopImgView;
    ImageView freeWorkoutsTutorialsImgView;
    ImageView ordersImgView;
    ImageView freeTuttorialsImgView;

    //Firebase authentication
    FirebaseAuth firebaseAuth;
    TextView home;
    GridView gridView;
    String[] titleWords ={"Workout tutorials","Free Workouts","About Vtrainer","Subscriptions"} ;
    int [] wordsImage ={R.drawable.workouttutorialspic,R.drawable.freeworkoutsimg,R.drawable.aboutusimg,R.drawable.subscriptionlogo};


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        home= view.findViewById(R.id.NameEdTxt);
        shopImgView = view.findViewById(R.id.shopImageView);
        freeWorkoutsTutorialsImgView = view.findViewById(R.id.freeWorkoutsTutImgView);
        ordersImgView = view.findViewById(R.id.orders);
        freeTuttorialsImgView = view.findViewById(R.id.freeTuttorialsImgView);

        freeTuttorialsImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), FreeTutorialVid.class);
                // myIntent.putExtra("key", value); //Optional parameters
                getActivity().startActivity(myIntent);

            }
        });


        freeWorkoutsTutorialsImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), Workout_tutorial1.class);
                // myIntent.putExtra("key", value); //Optional parameters
                getActivity().startActivity(myIntent);

            }
        });


        shopImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), Supplements_Store.class);
                // myIntent.putExtra("key", value); //Optional parameters
                getActivity().startActivity(myIntent);

            }
        });

        ordersImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), OrdersDisplay.class);
                // myIntent.putExtra("key", value); //Optional parameters
                getActivity().startActivity(myIntent);
            }
        });


        //init FirebaseAuth
        firebaseAuth=FirebaseAuth.getInstance();

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);//to show menu
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflating menu
        inflater.inflate(R.menu.menu_main2, menu);
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
