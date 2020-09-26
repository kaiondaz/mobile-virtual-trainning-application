package com.example.myapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Models.Review;
import com.example.myapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterReviews extends RecyclerView.Adapter<AdapterReviews.MyViewHolder> {

    Context context;
    ArrayList<Review> Reviews;
    String reviewerPic;

    DatabaseReference ClientReference;
    FirebaseAuth auth;
    FirebaseUser user;
    String userID;


    public AdapterReviews(Context c, ArrayList<Review> reviews, String ReviewerPic) {
        context = c;
        Reviews = reviews;
        reviewerPic = ReviewerPic;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.row_review, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final String ReviewerID = Reviews.get(position).getReviewerID();
        holder.ratingBar2.setRating(Float.valueOf(Reviews.get(position).getStars()));
        holder.name.setText(Reviews.get(position).getReviewerName());
        holder.date.setText(Reviews.get(position).getDate());

        if (!(Reviews.get(position).getReviewerPic() == null)) {
            Picasso.get().load(Reviews.get(position).getReviewerPic()).placeholder(R.drawable.ic_default_img).into(holder.profilePic);
        }


    }


    @Override
    public int getItemCount() {
        return Reviews.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, date;
        ImageView profilePic;
        Button ViewClientProfile;
        RatingBar ratingBar2;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.dateTv);
            name = (TextView) itemView.findViewById(R.id.nameTextview);
            profilePic = (ImageView) itemView.findViewById(R.id.reviewerPic);
            ratingBar2 = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }


    }
}
