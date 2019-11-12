package com.example.expenseadmin.view.fragments.favorites;

import com.example.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

public interface FavoritesView {
    void onGetFavoriteData(ArrayList<RestaurantModel> list);
}
