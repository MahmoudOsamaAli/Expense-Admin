package com.example.expenseadmin.view.activities.displayImage;

import com.example.expenseadmin.data.Data;
import com.example.expenseadmin.pojo.PlaceImage;

import java.util.ArrayList;

class DisplayImagePresenter {

    private DisplayImageView view;

    DisplayImagePresenter(DisplayImageView view) {
        this.view = view;
    }

    private ArrayList<PlaceImage> getImagesList(){
        return Data.getPlaceImages();
    }
    void getList(){
        view.onGetImages(getImagesList());
    }
}
