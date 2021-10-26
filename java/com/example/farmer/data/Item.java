package com.example.farmer.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {
    @SerializedName("commodity")
    @Expose
    private String commodity;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("uniqueID")
    @Expose
    private String uniqueID;

    public Item(){ }

    public Item(String commodity, String price, String uniqueID) {
        this.commodity = commodity;
        this.price = price;
        this.uniqueID = uniqueID;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
