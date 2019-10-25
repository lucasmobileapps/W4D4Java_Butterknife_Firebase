package com.google.firebase.example.w4d4java_butterknife_firebase;

public class Message {

    private String message;
    private String name;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Message(String message, String name, String photo) {
        this.message = message;
        this.name = name;
        this.photo = photo;
    }
    public Message() {}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
