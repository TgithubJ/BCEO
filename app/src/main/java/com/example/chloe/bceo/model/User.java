package com.example.chloe.bceo.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chuntaejin on 11/12/15.
 */
public class User implements Serializable {
    private int id;
    private String email;
    private String password;
    private int group_id;
    private String phone;
    private String msg;

    public User(int id, String email, String password, int group_id, String phone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.group_id = group_id;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        msg = message;
    }

    public int getGroupID() {
        return group_id;
    }

    public void setGroupID(int groupID) {
        group_id = groupID;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phone;
    }

    public void setPhoneNum(String phoneNum) {
        phone = phoneNum;
    }

    public String getUserID() {
        return email;
    }

    public void setUserID(String userID) {
        this.email = userID;
    }


}
