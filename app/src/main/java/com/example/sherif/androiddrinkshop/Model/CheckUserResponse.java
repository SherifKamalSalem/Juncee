package com.example.sherif.androiddrinkshop.Model;

public class CheckUserResponse {

    private boolean exists;
    private String error_msg;


    public CheckUserResponse() {
    }

    public boolean isExists() {
        return exists;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
