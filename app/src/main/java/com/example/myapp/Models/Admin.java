package com.example.myapp.Models;

public class Admin {

    private String Name;

    private String address;
    private String email;
    private String createPassword;
    private String uid;
    private String Phone;
    private String onlineStatus;
    private String imageAddress;

    public Admin(String name, String address, String email, String createPassword, String uid, String phone, String onlineStatus, String imageAddress) {
        Name = name;
        this.address = address;
        this.email = email;
        this.createPassword = createPassword;
        this.uid = uid;
        Phone = phone;
        this.onlineStatus = onlineStatus;
        this.imageAddress = imageAddress;
    }

    public String getName() { return Name; }

    public void setName(String name) { Name = name; }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email;
    }

    public String getCreatePassword() { return createPassword; }

    public void setCreatePassword(String createPassword) { this.createPassword = createPassword; }

    public String getUid() { return uid; }

    public void setUid(String uid) { this.uid = uid; }

    public String getPhone() { return Phone; }

    public void setPhone(String phone) { Phone = phone; }

    public String getOnlineStatus() { return onlineStatus; }

    public void setOnlineStatus(String onlineStatus) { this.onlineStatus = onlineStatus; }

    public String getImageAddress() { return imageAddress; }

    public void setImageAddress(String imageAddress) { this.imageAddress = imageAddress; }

}
