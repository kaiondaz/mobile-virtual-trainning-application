package com.example.myapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Client_profile_nutritionist;
import com.example.myapp.Models.Client2;
import com.example.myapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterClientsNutritionist extends RecyclerView.Adapter<AdapterClientsNutritionist.MyViewHolder> {

    Context context;
    ArrayList<Client2> Clients;

    public AdapterClientsNutritionist(Context c, ArrayList<Client2> clients)
    {
        context=c;
        Clients=clients;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String hisUID= Clients.get(position).getUID();
        holder.name.setText(Clients.get(position).getName());
        holder.email.setText(Clients.get(position).getEmail());
        Picasso.get().load(Clients.get(position).getImageAddress()).fit().into(holder.profilePic);
        //Picasso.get().load(Clients.get(position).getImageAddress()).placeholder(R.drawable.ic_default_img).into(holder.profilePic);


        //handle item click
        holder.ViewClientProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(context, Client_profile_nutritionist.class);
                intent.putExtra("hisUID", hisUID);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return Clients.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,email;
        ImageView profilePic;
        Button ViewClientProfile ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = (TextView) itemView.findViewById(R.id.emailTv);
            name = (TextView) itemView.findViewById(R.id.nameTv);
            profilePic = (ImageView) itemView.findViewById(R.id.reviewerPic);
            ViewClientProfile = (Button) itemView.findViewById(R.id.viewOrder);
        }

        public void onClick (int position)
        {


        }
    }
}
