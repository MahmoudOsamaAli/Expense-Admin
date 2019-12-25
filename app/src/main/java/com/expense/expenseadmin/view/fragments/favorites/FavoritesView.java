package com.expense.expenseadmin.view.fragments.favorites;

import com.expense.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

public interface FavoritesView {
    void onGetFavoriteData(ArrayList<RestaurantModel> list);
}
