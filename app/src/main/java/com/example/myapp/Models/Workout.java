package com.example.myapp.Models;

import android.widget.EditText;
import android.widget.ImageView;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Workout {
    private String workoutName;
    private String workoutDate;
    private String exerciseName;
    private String repetitions;
    private String sets;
    private String exercisePic;
    private String exerciseID;

    public Workout(String workoutName, String workoutDate, String exerciseName, String repetitions, String sets, String exercisePic,String ExerciseID) {
        this.workoutName = workoutName;
        this.workoutDate = workoutDate;
        this.exerciseName = exerciseName;
        this.repetitions = repetitions;
        this.sets = sets;
        this.exercisePic = exercisePic;
        this.exerciseID=ExerciseID;
    }

    public Workout(){

    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }

    public String getExercisePic() {
        return exercisePic;
    }

    public void setExercisePic(String exercisePic) {
        this.exercisePic = exercisePic;
    }

    public String getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(String exerciseID) {
        this.exerciseID = exerciseID;
    }















}
