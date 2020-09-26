package com.example.myapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Models.NutritionPlan;
import com.example.myapp.NutritionPlanClientDisplay;
import com.example.myapp.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ClientsNutritionPlansAdapter extends RecyclerView.Adapter<ClientsNutritionPlansAdapter.MyViewHolder> {

    Context context;
    List<NutritionPlan> nutritionPlans;
    String Useremail;

    DatabaseReference clientsRef;
    String CoachID2;

    //constructor
    public ClientsNutritionPlansAdapter(Context context, List<NutritionPlan> nutritionPlans, DatabaseReference ref) {

        this.context = context;
        this.nutritionPlans = nutritionPlans;
        this.clientsRef = ref;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nutritionplanrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final String nutritionPlanName = nutritionPlans.get(position).getNutritionPlanName();
        final String nutritionPlanDate = nutritionPlans.get(position).getNutritionPlanDate();
        final String Meal1 = nutritionPlans.get(position).getMeal1Name();
        final String Meal2 = nutritionPlans.get(position).getMeal2Name();
        final String Meal3 = nutritionPlans.get(position).getMeal3Name();


        //set data
        holder.nutritionPlanName.setText(nutritionPlanName);
        holder.Date.setText(nutritionPlanDate);


        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NutritionPlanClientDisplay.class);
                intent.putExtra("nutritionPlanName", nutritionPlanName);
                intent.putExtra("nutritionPlanDate", nutritionPlanDate);
                intent.putExtra("Meal1", Meal1);
                intent.putExtra("Meal2", Meal2);
                intent.putExtra("Meal3", Meal3);


                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return nutritionPlans.size();
    }

    //view holder class
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Date;
        TextView nutritionPlanName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.DateTxV);
            nutritionPlanName = itemView.findViewById(R.id.nutritionPlanTextV);


        }
    }
}
