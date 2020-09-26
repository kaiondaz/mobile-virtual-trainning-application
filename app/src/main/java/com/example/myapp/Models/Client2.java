package com.example.myapp.Models;


public class Client2 {

    private String Name;
    private String address;
    private String email;
    private String createPassword;
    private String uid;
    private String Phone;
    private String onlineStatus;
    private String imageAddress;
    private String coachID;
    private String nutritionistID;
    private String typingTo;
    private String startingWeight;


    public Client2(){

    }


    public Client2(String name, String Address, String Email, String CreatePassword, String ID, String phone, String TypyingTo,String StartingWeight){
        Name =name;
        address=Address;
        email=Email;
        createPassword=CreatePassword;
        uid=ID;
        Phone=phone;
        typingTo=TypyingTo;
        startingWeight=StartingWeight;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatePassword() {
        return createPassword;
    }

    public void setCreatePassword(String createPassword) {
        this.createPassword = createPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return Name;
    }

    public void setID (String ID) {this.uid=ID;}

    public String getUID () {return uid;}

    public void setName(String name) {
        this.Name = name;
    }

    public String getImageAddress() { return imageAddress; }

    public void setImageAddress(String imageAddress) {this.imageAddress = imageAddress;}

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getCoachID() { return coachID; }

    public void setCoachID(String coachID) { this.coachID = coachID; }

    public String getNutritionistID() { return nutritionistID; }

    public void setNutritionistID(String nutritionistID) { this.nutritionistID = nutritionistID; }

    public String getTypingTo() { return typingTo; }

    public void setTypingTo(String typingTo) { this.typingTo = typingTo; }

    public String getStartingWeight() { return startingWeight; }

    public void setStartingWeight(String startingWeight) { this.startingWeight = startingWeight; }



}
