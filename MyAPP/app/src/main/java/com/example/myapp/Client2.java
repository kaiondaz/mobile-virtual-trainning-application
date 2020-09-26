package com.example.myapp;


public class Client2 {

    private String Name;
    private String address;
    private String email;
    private String createPassword;
    private String uid;
    private String Phone;
    private String onlineStatus;
    private String [] Clients;


    private String imageAddress;

    public Client2(){

    }



    public Client2(String name, String Address, String Email, String CreatePassword, String ID, String phone){
        Name =name;
        address=Address;
        email=Email;
        createPassword=CreatePassword;
        uid=ID;
        Phone=phone;
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
}
