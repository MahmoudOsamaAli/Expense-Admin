package com.expense.expenseadmin.view.fragments.favorites;

import com.expense.expenseadmin.data.Data;
import com.expense.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

public class FavoritesPresenter {

    private FavoritesView view;

    public FavoritesPresenter(FavoritesView view) {
        this.view = view;
    }

    private ArrayList<RestaurantModel> getFavoriteFromDatabase(){
        return Data.getSelectedCategoryData();
    }
    void getFavorites(){
        view.onGetFavoriteData(getFavoriteFromDatabase());
    }
}
