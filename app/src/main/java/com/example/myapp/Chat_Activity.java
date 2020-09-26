package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.Adapters.AdapterChat;
import com.example.myapp.Models.ModelChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Chat_Activity extends AppCompatActivity {

    //views from xml
    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView profileIv;
    TextView nameTv,userStatusTv;
    EditText messageEt;
    ImageButton sendBtn;
    FirebaseAuth firebaseAuth;
    public FirebaseUser user;
    private String userEmail;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDbRef,coachesDbRef,nutritionistsDbRef;

    //Foe checking if user has seen message or not
    ValueEventListener seenListener;
    DatabaseReference userRefForSeen;

    List<ModelChat> chatList;
    AdapterChat adapterChat;


    String hisUID;
    String myID;
    String hisImage;
    String userType,userType1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);

        //init views
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        recyclerView=findViewById(R.id.chat_recyclerView);
        profileIv=findViewById(R.id.profileIv);
        nameTv=findViewById(R.id.nameTv);
        userStatusTv=findViewById(R.id.userStatusTv);
        messageEt=findViewById(R.id.messsageEt);
        sendBtn=findViewById(R.id.sendBtn);
        firebaseAuth = FirebaseAuth.getInstance();
        user =firebaseAuth.getCurrentUser();

        //Layout (Linear Layout) for RecyclerView
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        //recyclerView properties
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        /** On clicking on user from user list we have passed that user's UID using intent
         * so get that uid here to get the profile picture , name and start chat with that user
         */

        Intent intent=getIntent();
        hisUID=intent.getStringExtra("hisUID");
        userType1=intent.getStringExtra("coach");
        userType=intent.getStringExtra("nutritionist");


        //Firebase auth instance
        firebaseAuth=FirebaseAuth.getInstance();


        firebaseDatabase=FirebaseDatabase.getInstance();
        usersDbRef=firebaseDatabase.getReference("Clients");
        coachesDbRef=firebaseDatabase.getReference("Coaches");
        nutritionistsDbRef=firebaseDatabase.getReference("Nutritionists");



        //Search user to get that user info
        Query userQuery =usersDbRef.orderByChild("uid").equalTo(hisUID);
        if(!(userType1==null)){


             userQuery =coachesDbRef.orderByChild("uid").equalTo(hisUID);


        }

        if(!(userType==null)){


            userQuery =nutritionistsDbRef.orderByChild("uid").equalTo(hisUID);

        }

        //get user picture and name
        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Check until required info is received
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    //Get data
                    String name =""+ds.child("name").getValue();
                    hisImage =""+ds.child("imageAddress").getValue();
                    //get value of online status
                    String onlineStatus = ""+ds.child("onlineStatus").getValue();


                    //Set data
                    nameTv.setText(name);
                    if (onlineStatus.equals("online")){
                    userStatusTv.setText(onlineStatus);
                    }

                    else{
                        //convert time stamp to proper time
                        //convert time stamp to dd/mm/yyyy hh:mm am/pm
                        //Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        //cal.setTimeInMillis(Long.parseLong(onlineStatus));
                        //String dateTime = DateFormat.getDateTimeInstance().format("dd/MM/YYYY").toString();
                        //userStatusTv.setText("Last seen at "+dateTime);

                    }




                    try{
                        //Image received set image to toolbar
                        Picasso.get().load(hisImage).placeholder(R.drawable.ic_default).into(profileIv);
                    }

                    catch (Exception e) {
                        //There is execption
                        Picasso.get().load(R.drawable.ic_default).into(profileIv);
                    }

                }


        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //click button to send message
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get text from edit text
                String message= messageEt.getText().toString().trim();

                //check if text is empty or not
                if(TextUtils.isEmpty(message)){
                    //text empty
                   Toast.makeText(Chat_Activity.this,"Cannot send the empty message...",Toast.LENGTH_SHORT).show();
                }
                else{
                    //Text not empty
                    sendMessage(message);
                }

            }


        });

        readMessages();
        seenMessage();






    }

    private void seenMessage() {
        userRefForSeen =FirebaseDatabase.getInstance().getReference("Chats");
        seenListener= userRefForSeen.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ModelChat chat = ds.getValue(ModelChat.class);
                    if(chat.getReceiver().equals(myID) && chat.getSender().equals(hisUID)){
                        HashMap<String,Object> hasSeenHashMap = new HashMap<>();
                        hasSeenHashMap.put("isSeen", true);
                        ds.getRef().updateChildren(hasSeenHashMap);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessages() {
        chatList =new ArrayList<>();
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Chats");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();

                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    ModelChat chat =ds.getValue(ModelChat.class);
                    if(!(chat==null)){
                    if(chat.getReceiver().equals(myID) && chat.getSender().equals(hisUID) ||
                            chat.getReceiver().equals(hisUID) && chat.getSender().equals(myID)){
                        chatList.add(chat);
                    }
                    //adapter
                    adapterChat= new AdapterChat(Chat_Activity.this,chatList,hisImage);
                    adapterChat.notifyDataSetChanged();
                    //set adapter to recyclerView
                    recyclerView.setAdapter(adapterChat);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void sendMessage(String message) {
                /*"Chats" node will be created that will contain all the chats
                Whenever user sends message it will create a new child in chats "Chats" node and that chat will contain
                the following key values:
                sender:UID of sender
                receiver:UID of receiver
                message:The actual message
                 */


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        String timeStamp= String.valueOf(System.currentTimeMillis());

        HashMap<String,Object>hashMap=new HashMap<>();
        hashMap.put("sender",myID);
        hashMap.put("receiver",hisUID);
        hashMap.put("message",message);
        hashMap.put("timeStamp",timeStamp);
        hashMap.put("isSeen",false);
        databaseReference.child("Chats").push().setValue(hashMap);

        //reset edit text after sending message
        messageEt.setText("");





    }


    private void checkUserStatus(){

        //init Firebase auth
        FirebaseUser user =firebaseAuth.getCurrentUser();

        if(user!=null){
            //user is sign in stay here
            //set email of loggin in user
            //mProfileTv.setText(user.getEmail()) ;
            myID=user.getUid();//current sign in user



        }

        else{
            //Client2 not sign in go to main activity
            startActivity(new Intent(this,MainActivity.class));
            finish();



        } }

        private void checkOnlineStatus(String Status){


        if (!user.getEmail().contains("@my.coach")){
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Clients").child(myID);
            HashMap <String, Object> hashMap = new HashMap<>();
            hashMap.put("onlineStatus",Status);
            //Update value of onlineStatus of current user
            dbRef.updateChildren(hashMap);
        }
        else{

            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Coaches").child(myID);
            HashMap <String, Object> hashMap = new HashMap<>();
            hashMap.put("onlineStatus",Status);
            //Update value of onlineStatus of current user
            dbRef.updateChildren(hashMap);
        }



        }

    @Override
    protected void onStart() {
        checkUserStatus();
        //set online
        checkOnlineStatus("online");
        super.onStart();
    }

    @Override
    protected void onResume() {
        //set online
        checkOnlineStatus("online");
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

        //get time stamp
        String timeStamp = String.valueOf(System.currentTimeMillis());

        //Set offline with last seen time stamp
        checkOnlineStatus(timeStamp);
        userRefForSeen.removeEventListener(seenListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Hide searchView as its not needed here
        menu.findItem(R.id.action_search).setVisible(false);

        return super.onCreateOptionsMenu(menu);
            }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        //get item id
        int id= item.getItemId();
        if(id==R.id.action_logout){
            //firebaseAuth.signOut();
            Intent intent =new Intent(Chat_Activity.this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }





}


