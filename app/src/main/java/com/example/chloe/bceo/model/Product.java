package com.example.chloe.bceo.model;

import java.io.Serializable;

/**
 * Created by chuntaejin on 11/12/15.
 */
public class Product implements Serializable{
    private int pID;
    private String pName;
    private float pPrice;
    private String pDescription;
    private int pWaiting;
    private int imageId;
    private int groupId;
    private String category;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product(int pID, String pName, float pPrice, String pDescription, int pWaiting, int imageId, int groupId, String category, String status){
        setpID(pID);
        setpName(pName);
        setpPrice(pPrice);
        setpDescription(pDescription);
        setpWaiting(pWaiting);
        setImageId(imageId);
        setGroupId(groupId);
        setCategory(category);
        setStatus(status);
    }


    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDescription() {
        return pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public float getpPrice() {
        return pPrice;
    }

    public void setpPrice(float pPrice) {
        this.pPrice = pPrice;
    }

    public int getpWaiting() {
        return pWaiting;
    }

    public void setpWaiting(int pWaiting) {
        this.pWaiting = pWaiting;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String toString(){

        StringBuilder sb = new StringBuilder();
        sb.append("Product Detail"); sb.append("\n");

        sb.append("id: ");
        sb.append(getpID()); sb.append("\n");

        sb.append("name: ");
        sb.append(getpName()); sb.append("\n");

        sb.append("price: ");
        sb.append(getpPrice()); sb.append("\n");

        sb.append("description: ");
        sb.append(getpDescription()); sb.append("\n");

        sb.append("waitlist: ");
        sb.append(getpWaiting()); sb.append("\n");

        sb.append("image_id: ");
        sb.append(getImageId()); sb.append("\n");

        sb.append("group_id: ");
        sb.append(getGroupId()); sb.append("\n");

        sb.append("category: ");
        sb.append(getCategory()); sb.append("\n");

        return sb.toString();
    }
}
