package com.example.expenseadmin.data;

import com.example.expenseadmin.R;
import com.example.expenseadmin.pojo.HomeViewModel;
import com.example.expenseadmin.pojo.PlaceImage;
import com.example.expenseadmin.pojo.RestaurantModel;
import com.example.expenseadmin.pojo.locationModel;

import java.util.ArrayList;

public class Data {
    private  String name;
    private  String email;

    public static ArrayList<HomeViewModel> getCategoriesData() {

        ArrayList<HomeViewModel> mData = new ArrayList<>();
        HomeViewModel model = new HomeViewModel("coffee" , R.drawable.ic_coffee);
        mData.add(model);
        model = new HomeViewModel("Electronics" , R.drawable.icons_multiple_devices);
        mData.add(model);
        model = new HomeViewModel("Food" , R.drawable.icon_food);
        mData.add(model);
        model = new HomeViewModel("Clothes" , R.drawable.icon_tshirt);
        mData.add(model);
        model = new HomeViewModel("Beauty" , R.drawable.icon_makeup);
        mData.add(model);
        model = new HomeViewModel("Others" , R.drawable.ic_shopping);
        mData.add(model);
        return mData;
    }
    public static ArrayList<RestaurantModel> getSelectedCategoryData(){

        ArrayList<RestaurantModel> mData = new ArrayList<>();
        RestaurantModel item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        item = new RestaurantModel("Al-Mohandeseen" , R.drawable.restaurant);
        mData.add(item);
        return mData;
    }

    public static ArrayList<PlaceImage> getPlaceImages(){
        ArrayList<PlaceImage> data = new ArrayList<>();
        PlaceImage item = new PlaceImage(R.drawable.restaurant);
        data.add(item);
        item = new PlaceImage(R.drawable.restaurant);
        data.add(item);
        item = new PlaceImage(R.drawable.restaurant);
        data.add(item);
        item = new PlaceImage(R.drawable.restaurant);
        data.add(item);
        item = new PlaceImage(R.drawable.restaurant);
        data.add(item);
        item = new PlaceImage(R.drawable.restaurant);
        data.add(item);
        item = new PlaceImage(R.drawable.restaurant);
        data.add(item);
        return data;
    }
    public static ArrayList<locationModel> getLocations(){
        ArrayList<locationModel> data = new ArrayList<>();
        locationModel item = new locationModel("Al-Haram Street" , "Giza / Egypt");
        data.add(item);
        item = new locationModel("Al-Haram Street" , "Giza / Egypt");
        data.add(item);
        return data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
