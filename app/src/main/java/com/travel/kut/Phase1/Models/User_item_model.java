package com.travel.kut.Phase1.Models;

import java.util.ArrayList;

public class User_item_model {

    String user_email;
    String user_name;
    String user_pic;

    public User_item_model(String user_email, String user_name, String user_pic) {
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_pic = user_pic;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }
}
