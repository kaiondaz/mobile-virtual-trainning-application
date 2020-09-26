package com.example.myapp.Models;

public class Review {

    String stars;
    String date;
    String reviewerID;
    String reviewerName;
    String reviewerEmail;
    String reviewerPic;



    public Review(){

    }


    public Review(String stars, String date, String notes, String ReviewerID, String ReviewrName, String ReviewerEmail,String ReviewerPic ) {
        this.stars = stars;
        this.date = date;
        this.reviewerID=ReviewerID;
        this.reviewerName=ReviewrName;
        this.reviewerEmail=ReviewerEmail;
        this.reviewerPic=ReviewerPic;
    }


    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getReviewerID() { return reviewerID; }

    public void setReviewerID(String reviewer) { this.reviewerID = reviewer; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getReviewerName() { return reviewerName; }

    public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }

    public String getReviewerEmail() { return reviewerEmail; }

    public void setReviewerEmail(String reviewerEmail) { this.reviewerEmail = reviewerEmail; }

    public String getReviewerPic() { return reviewerPic; }

    public  void setReviewerPic (String reviewerPic){this.reviewerPic = reviewerPic;}



}
