package com.example.expenseadmin.view.activities.selectedCategory;


import com.example.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

public interface SelectedCategoryView {
    void onGetCategoryData(ArrayList<RestaurantModel> list);
}
