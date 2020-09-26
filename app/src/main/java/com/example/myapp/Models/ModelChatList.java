package com.example.myapp.Models;

public class ModelChatList {
    String id;//id to get chat list, sender/receiver uid

    public ModelChatList () {

    }

    public ModelChatList(String id) {
        this.id = id;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }


}
