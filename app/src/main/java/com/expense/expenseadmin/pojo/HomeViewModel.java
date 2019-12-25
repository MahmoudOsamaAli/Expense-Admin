package com.expense.expenseadmin.pojo;

public class HomeViewModel{

    private String mText;
    private int mImageId;

    public HomeViewModel(String mText, int mImageId) {
        this.mText = mText;
        this.mImageId = mImageId;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public int getmImageId() {
        return mImageId;
    }

    public void setmImageId(int mImageId) {
        this.mImageId = mImageId;
    }

}