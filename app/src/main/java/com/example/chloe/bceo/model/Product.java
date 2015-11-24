package com.example.chloe.bceo.model;

/**
 * Created by chuntaejin on 11/12/15.
 */
public class Product {
    private String pID;
    private String pName;
    private float pPrice;
    private int pWaiting;

    private String pDescription;

    public String getpID() {
        return this.pID;
    }
    public String getpName() {
        return this.pName;
    }
    public String getpDescription() {
        return this.pDescription;
    }
    public float getpPrice() {
        return this.pPrice;
    }
    public int getpWaiting() {
        return this.pWaiting;
    }

    public void setpID(String pID) {
        this.pID = pID;
    }
    public void setpName(String pName) {
        this.pName = pName;
    }
    public void setpPrice(float price) {
        this.pPrice = price;
    }
    public void setpWaiting(int pWaiting) {
        this.pWaiting = pWaiting;
    }
    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

}
