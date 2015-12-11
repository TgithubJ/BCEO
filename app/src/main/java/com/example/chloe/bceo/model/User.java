package com.example.chloe.bceo.model;

import java.io.Serializable;

/**

 * Created by chuntaejin on 11/12/15.
 */
public class User implements Serializable {
    private int userID;
    private String userEmail;
    private String userName;
    private int groupID;
    private String phoneNum;
    private int priority;
    private String message;

    public User(){}

    public User(int login_id, String login_name ,String login_email, String login_password, int login_groupId, String login_phone){
        this.userName = login_name;
        this.userID = login_id;
        this.userEmail = login_email;
        this.password = login_password;
        this.groupID = login_groupId;
        this.phoneNum = login_phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String password;

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("User Detail"); sb.append("\n");

        sb.append("group_id: ");
        sb.append(getGroupID()); sb.append("\n");

        sb.append("user_id: ");
        sb.append(getUserID()); sb.append("\n");

        sb.append("name: ");
        sb.append(getUserName()); sb.append("\n");

        sb.append("email: ");
        sb.append(getUserEmail()); sb.append("\n");

        sb.append("phone number: ");
        sb.append(getPhoneNum()); sb.append("\n");

        sb.append("priority: ");
        sb.append(getPriority()); sb.append("\n");

        sb.append("message: ");
        sb.append(getMessage()); sb.append("\n");

        return sb.toString();
    }
}
