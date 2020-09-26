package com.example.myapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserFragment extends Fragment {
    Button addCoach,addNutritionist;



    public AddUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_addusers, container, false);

        addCoach = view.findViewById(R.id.addCoach);
        addNutritionist = view.findViewById(R.id.addNutritionist);

        addCoach.setOnClickListener(new View.OnClickListener() {
            String userType ="Coach";
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getContext(), RegisterUsersAdmin.class);
                myIntent.putExtra("userType", userType);
                // myIntent.putExtra("key", value); //Optional parameters
                getContext().startActivity(myIntent);
            }
        });

        addNutritionist.setOnClickListener(new View.OnClickListener() {
            String userType ="Nutritionist";
            @Override
            public void onClick(View v) {
                Intent myIntent2 = new Intent(getContext(), RegisterUsersAdmin.class);
                myIntent2.putExtra("userType", userType);
                // myIntent.putExtra("key", value); //Optional parameters
                getContext().startActivity(myIntent2);

            }
        });

        return view;


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








