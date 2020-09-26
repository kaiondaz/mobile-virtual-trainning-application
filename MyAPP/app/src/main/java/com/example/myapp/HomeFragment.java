package com.example.myapp;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Adapters.MainAdapter;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    ImageView shopImgView;
    ImageView freeWorkoutsTutorialsImgView;

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

        home= view.findViewById(R.id.NameTv);
        shopImgView = view.findViewById(R.id.shopImageView);
        freeWorkoutsTutorialsImgView = view.findViewById(R.id.freeWorkoutsTutImgView);

        freeWorkoutsTutorialsImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), Select_Workout_page.class);
                // myIntent.putExtra("key", value); //Optional parameters
                getActivity().startActivity(myIntent);

            }
        });


        shopImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), Supplements.class);
                // myIntent.putExtra("key", value); //Optional parameters
                getActivity().startActivity(myIntent);

            }
        });

        /** FOR GRIDVIEW IMPLEMENTATION
        gridView=view.findViewById(R.id.gridView2);

        MainAdapter adapter = new MainAdapter(getActivity().getApplicationContext(),titleWords,wordsImage);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText( getActivity().getApplicationContext(),"You clicked "+titleWords[+position],Toast.LENGTH_SHORT).show();

            }
            });*/



        //init FirebaseAuth
        firebaseAuth=FirebaseAuth.getInstance();

        return view;

    }
/**
    private void searchUsers(String query) {

        //get current user
        final FirebaseUser fUser= FirebaseAuth.getInstance().getCurrentUser();

        //Get path of database named  containing users info
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Clients");

        //Get all data from path
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserList.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Client2 modelUser =ds.getValue(Client2.class);

                    //get all users except currelty sign in user
                    //if (!modelUser.getUID().equals(fUser.getUid())){
                    UserList.add(modelUser);
                    //}

                    //adapter
                    adapterUsers= new AdapterUsers(getActivity(),UserList);
                    recyclerview.setAdapter(adapterUsers);


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

                    getAllUsers();
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

                    getAllUsers();
                }


                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }



    //handle menu clicks

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        //get item id
        int id= item.getItemId();
        if(id==R.id.action_logout){
            firebaseAuth.signOut();
        }

        return super.onOptionsItemSelected(item);
    }
    **/


}
