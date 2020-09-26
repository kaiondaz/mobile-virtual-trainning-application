package com.example.myapp.Models;

public class Coach extends Client2 {
    public String userType;

    public Coach(){

    }


    public Coach(String UserType){
        super();
        userType=UserType;

    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }


}
