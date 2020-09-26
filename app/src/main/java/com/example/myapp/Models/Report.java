package com.example.myapp.Models;

public class Report {

    String BMI;
    String weight;
    String strentgh;
    String reportID;
    String clientName;
    String reviewerEmail;
    String UID;

    public  Report(){

    }

    public Report(String BMI, String weight, String strentgh, String reportID, String clientName, String reviewerEmail) {
        this.BMI = BMI;
        this.weight = weight;
        this.strentgh = strentgh;
        this.reportID = reportID;
        this.clientName = clientName;
        this.reviewerEmail = reviewerEmail;
    }

    public String getBMI() {
        return BMI;
    }

    public void setBMI(String BMI) {
        this.BMI = BMI;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getStrentgh() {
        return strentgh;
    }

    public void setStrentgh(String strentgh) {
        this.strentgh = strentgh;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }



}
