package com.questdo.twang.grocerylist;

/**
 * Created by twang on 3/29/17.
 */

public class Grocery {
    private long mID;
    private String mName, mDescription, mPrice, mType;

    public Grocery(long ID, String name, String description, String price, String type) {
        mID = ID;
        mName = name;
        mDescription = description;
        mPrice = price;
        mType = type;
    }

    public long getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getType() {
        return mType;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public void setType(String type) {
        mType = type;
    }
}
