package com.example.myapp.Models;

public class Nutritionist extends Client2 {

    public String userType;

    public Nutritionist(String name, String Address, String Email, String CreatePassword, String ID, String phone, String userType) {
        this.userType = userType;
    }

    public Nutritionist(){
        super();

    }

    public String getUserType() { return userType; }

    public void setUserType(String userType) { this.userType = userType; }

    public Nutritionist(String userType) { this.userType = userType; }

}
