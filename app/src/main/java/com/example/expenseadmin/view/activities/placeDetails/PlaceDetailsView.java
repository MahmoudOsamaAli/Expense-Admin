package com.example.expenseadmin.view.activities.placeDetails;

import com.example.expenseadmin.pojo.PlaceImage;
import com.example.expenseadmin.pojo.locationModel;

import java.util.ArrayList;

public interface PlaceDetailsView {

    void onGetLocations(ArrayList<locationModel> list);
    void onGetPlaceImages(ArrayList<PlaceImage> list);
    void onGetSeekBarRating(ArrayList<Integer> list);
    void onGetChartRating(double value);
}
