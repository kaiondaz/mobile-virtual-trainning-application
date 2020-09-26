package com.example.myapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.Models.Workout;
import com.example.myapp.NutritionPlanClientDisplay;
import com.example.myapp.R;
import com.example.myapp.WorkoutPlanClientDisplay;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ClientsWorkoutPlansAdapter extends RecyclerView.Adapter<ClientsWorkoutPlansAdapter.MyViewHolder> {

    Context context;
    List<Workout> workoutPlans;
    String Useremail;

    DatabaseReference clientsRef;
    String CoachID2;

    //constructor
    public ClientsWorkoutPlansAdapter(Context context, List<Workout> workoutPlans, DatabaseReference ref) {

        this.context = context;
        this.workoutPlans = workoutPlans;
        this.clientsRef = ref;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workoutplanrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final String workoutPlanName = workoutPlans.get(position).getWorkoutName();
        final String workoutPlanDate = workoutPlans.get(position).getWorkoutDate();
        final String exerciseName = workoutPlans.get(position).getExerciseName();
        final String reps = workoutPlans.get(position).getRepetitions();
        final String sets = workoutPlans.get(position).getSets();


        /**final String Meal1 = workoutPlans.get(position).getMeal1Name();
         final String Meal2 = workoutPlans.get(position).getMeal2Name();
         final String Meal3 = workoutPlans.get(position).getMeal3Name();*/


        //set data
        holder.workoutPlanName.setText(workoutPlanName);
        holder.Date.setText(workoutPlanDate);


        //handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WorkoutPlanClientDisplay.class);
                intent.putExtra("workoutPlanName", workoutPlanName);
                intent.putExtra("workoutPlanDate", workoutPlanDate);
                intent.putExtra("exerciseName", exerciseName);
                intent.putExtra("reps", reps);
                intent.putExtra("sets", sets);


                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return workoutPlans.size();
    }

    //view holder class
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Date;
        TextView workoutPlanName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.DateTxV);
            workoutPlanName = itemView.findViewById(R.id.workoutPlanTextV);


        }
    }
}
