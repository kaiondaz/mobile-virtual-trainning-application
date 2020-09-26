package com.example.myapp.Models;

import java.lang.reflect.Type;

public class Subscription {

    String type;
    String price;
    String subsImage;

    public Subscription(String type, String price, String subsImage) {
        this.type = type;
        this.price = price;
        this.subsImage = subsImage;
    }

    public Subscription()
    {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubsImage() {
        return subsImage;
    }

    public void setSubsImage(String subsImage) {
        this.subsImage = subsImage;
    }
}
