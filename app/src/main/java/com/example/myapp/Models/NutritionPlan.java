package com.example.myapp.Models;

public class NutritionPlan {

    private String nutritionPlanName;
    private String nutritionPlanDate;
    private String meal1Name;
    private String meal2Name;
    private String meal3Name;
    private String NutritionPlanPic;
    private String NutritionPlanID;


    public NutritionPlan(){

    }

    public NutritionPlan(String nutritionPlanName, String nutritionPlanDate, String meal1Name, String meal2Name, String meal3Name, String nutritionPlanPic, String nutritionPlanID) {
        this.nutritionPlanName = nutritionPlanName;
        this.nutritionPlanDate = nutritionPlanDate;
        this.meal1Name = meal1Name;
        this.meal2Name = meal2Name;
        this.meal3Name = meal3Name;
        NutritionPlanPic = nutritionPlanPic;
        NutritionPlanID = nutritionPlanID;
    }

    public String getNutritionPlanName() {
        return nutritionPlanName;
    }

    public void setNutritionPlanName(String nutritionPlanName) {
        this.nutritionPlanName = nutritionPlanName;
    }

    public String getNutritionPlanDate() {
        return nutritionPlanDate;
    }

    public void setNutritionPlanDate(String nutritionPlanDate) {
        this.nutritionPlanDate = nutritionPlanDate;
    }

    public String getMeal1Name() {
        return meal1Name;
    }

    public void setMeal1Name(String meal1Name) {
        this.meal1Name = meal1Name;
    }

    public String getMeal2Name() {
        return meal2Name;
    }

    public void setMeal2Name(String meal2Name) {
        this.meal2Name = meal2Name;
    }

    public String getMeal3Name() {
        return meal3Name;
    }

    public void setMeal3Name(String meal3Name) {
        this.meal3Name = meal3Name;
    }

    public String getNutritionPlanPic() {
        return NutritionPlanPic;
    }

    public void setNutritionPlanPic(String nutritionPlanPic) {
        NutritionPlanPic = nutritionPlanPic;
    }

    public String getNutritionPlanID() {
        return NutritionPlanID;
    }

    public void setNutritionPlanID(String nutritionPlanID) {
        NutritionPlanID = nutritionPlanID;
    }

}
