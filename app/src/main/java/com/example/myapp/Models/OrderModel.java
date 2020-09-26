package com.example.myapp.Models;

public class OrderModel {
    String date;
    String product;
    String clientName;
    String productDescription;
    String price;
    String orderID;
    String clientAddress;

    public OrderModel(){

    }

    public OrderModel(String date, String product, String clientName, String productDescription, String Price,String ClientAddress, String OrderID) {
        this.date = date;
        this.product = product;
        this.clientName = clientName;
        this.productDescription = productDescription;
        this.price = Price;
        this.clientAddress=ClientAddress;
        this.orderID=OrderID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrderID(){return orderID;}

    public void setOrderID(String orderID) { this.orderID = orderID; }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
}
