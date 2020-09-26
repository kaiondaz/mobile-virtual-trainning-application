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

import com.example.myapp.Chat_Activity;
import com.example.myapp.Models.Client2;
import com.example.myapp.R;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterNutritionists extends RecyclerView.Adapter<AdapterNutritionists.MyHolder> {

    Context context;
    List<Client2> nutritionistsList;
    String Useremail;
    DatabaseReference nutritionistsRef;
    String NutritionistID2;
    String userType = "nutritionist";


    //Constructor
    public AdapterNutritionists(Context context, List<Client2> client2List, String Email, DatabaseReference ref, String nutritionistID) {
        this.context = context;
        this.nutritionistsList = client2List;
        this.Useremail = Email;
        this.nutritionistsRef = ref;
        NutritionistID2 = nutritionistID;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //get data
        final String hisUID = nutritionistsList.get(position).getUID();
        String userImage = nutritionistsList.get(position).getImageAddress();
        String userName = nutritionistsList.get(position).getName();
        final String userEmail = nutritionistsList.get(position).getEmail();

        //set data
        holder.nNameTv.setText(userName);
        holder.mEmailTv.setText(userEmail);


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
                intent.putExtra("nutritionist", userType);
                intent.putExtra("hisUID", hisUID);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return nutritionistsList.size();
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
            addClient.setVisibility(View.GONE);


        }
    }
}
