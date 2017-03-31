package com.healthplus.healthplus.Entity.Actors;

import com.google.gson.annotations.SerializedName;

/**
 * Created by VSRK on 9/23/2016.
 */

public class Restaraunt {
    String name;

    @SerializedName("Url")
    String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
