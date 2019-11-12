package com.example.expenseadmin.view.activities.placeDetails;

import com.example.expenseadmin.data.Data;
import com.example.expenseadmin.pojo.PlaceImage;
import com.example.expenseadmin.pojo.locationModel;

import java.util.ArrayList;

class PlaceDetailsPresenter {

    private PlaceDetailsView view;

    PlaceDetailsPresenter(PlaceDetailsView view) {
        this.view = view;
    }
    private ArrayList<locationModel> getLocations(){
        return Data.getLocations();
    }
    private ArrayList<PlaceImage> getPlaceImages(){
        return Data.getPlaceImages();
    }
    private ArrayList<Integer> getSeekBarValues(){
        ArrayList<Integer> list = new ArrayList<>();
        list.add(85);
        list.add(6);
        list.add(9);
        return list;
    }
    private double getTotalRating(){
        return 8.8;
    }
    void getLocationsList(){
        view.onGetLocations(getLocations());
    }
    void getPlaceImagesList(){
        view.onGetPlaceImages(getPlaceImages());
    }
    void getRatings(){
        view.onGetSeekBarRating(getSeekBarValues());
    }
    void getChartRating(){
        view.onGetChartRating(getTotalRating());
    }
}
