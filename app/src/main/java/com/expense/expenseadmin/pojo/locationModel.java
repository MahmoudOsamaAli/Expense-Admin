package com.expense.expenseadmin.pojo;

public class locationModel {

    private String mStreet;
    private String mCity;


    public locationModel(String mLocation , String city) {
        this.mStreet = mLocation;
        this.mCity = city;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmStreet() {
        return mStreet;
    }

    public void setmStreet(String mStreet) {
        this.mStreet = mStreet;
    }
}
