package com.example.myapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Chat_Activity;
import com.example.myapp.Models.Client2;
import com.example.myapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder> {

    Context context;
    List<Client2> client2List;
    String Useremail;
    DatabaseReference clientsRef;
    String CoachID2;

    //contructor
    public AdapterUsers(Context context, List<Client2> client2List, String Email, DatabaseReference ref, String coachID) {
        this.context = context;
        this.client2List = client2List;
        this.Useremail = Email;
        this.clientsRef = ref;
        CoachID2 = coachID;

    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        if (client2List.get(position) != null) {
            //get data
            final String hisUID = client2List.get(position).getUID();
            String userImage = client2List.get(position).getImageAddress();
            String userName = client2List.get(position).getName();
            final String userEmail = client2List.get(position).getEmail();
            final String CoachID = client2List.get(position).getCoachID();


            //set data
            holder.nNameTv.setText(userName);
            holder.mEmailTv.setText(userEmail);

            //Take out the option to add a client if they already have a coach
            if (CoachID != null) {

                if (!CoachID.equals("")) {
                    holder.addClient.setVisibility(View.GONE);

                }
            }
            try {
                Picasso.get().load(userImage).placeholder(R.drawable.ic_default_img).into(holder.mAvatarIv);


            } catch (Exception e) {
                e.printStackTrace();
            }

            //handle item click
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Chat_Activity.class);
                    intent.putExtra("hisUID", hisUID);
                    context.startActivity(intent);
                }
            });


            //handle item click
            holder.addClient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Title");
                    alert.setMessage("Message : Are you sure you want to add this client to your group?");

                    // Set an EditText view to get user input
                    final EditText input = new EditText(context);
                    alert.setView(input);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            String value = input.getText().toString();
                            final DatabaseReference clientsRef2 = FirebaseDatabase.getInstance().getReference("Clients");


                            clientsRef2.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        Client2 modelClient2 = ds.getValue(Client2.class);
                                        Client2 model = new Client2();
                                        //String CoachID2=modelClient2.getCoachID().toString().trim();

                                        if (modelClient2.getUID().equals(hisUID)) {
                                            model = modelClient2;
                                            model.setCoachID(CoachID2);
                                            clientsRef2.child(hisUID).setValue(modelClient2);
                                            Toast.makeText(context, "coachID: " + model.getCoachID(),
                                                    Toast.LENGTH_LONG).show();

                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            return;
                        }
                    });

                    alert.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    return;
                                }
                            });
                    alert.show();


                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return client2List.size();
    }

    //view holder class
    class MyHolder extends RecyclerView.ViewHolder {
        ImageView mAvatarIv;
        TextView nNameTv, mEmailTv;
        Button addClient;


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            nNameTv = itemView.findViewById(R.id.nameTv);
            mEmailTv = itemView.findViewById(R.id.emailTv);
            addClient = (Button) itemView.findViewById(R.id.viewOrder);

            if (!Useremail.contains("@my.coach.com")) {
                addClient.setVisibility(View.GONE);
            }


        }
    }
}
