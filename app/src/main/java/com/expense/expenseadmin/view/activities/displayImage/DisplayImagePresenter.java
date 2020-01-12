package com.expense.expenseadmin.view.activities.displayImage;

import com.expense.expenseadmin.data.Data;
import com.expense.expenseadmin.pojo.PlaceImage;

import java.util.ArrayList;

class DisplayImagePresenter {

    private DisplayImageView view;
    private DisplayImage mCurrent;

    DisplayImagePresenter(DisplayImageView view,DisplayImage context) {
        this.view = view;
        this.mCurrent = context;
    }

    private ArrayList<PlaceImage> getImagesList(){
        return Data.getPlaceImages();
    }

    void getList(){
        view.onGetImages(getImagesList());
    }
}
