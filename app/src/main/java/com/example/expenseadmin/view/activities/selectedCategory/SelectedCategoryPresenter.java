package com.example.expenseadmin.view.activities.selectedCategory;

import com.example.expenseadmin.data.Data;
import com.example.expenseadmin.pojo.RestaurantModel;
import com.example.expenseadmin.view.activities.selectedCategory.SelectedCategoryView;

import java.util.ArrayList;

class SelectedCategoryPresenter {

    private SelectedCategoryView view;

    SelectedCategoryPresenter(SelectedCategoryView view) {
        this.view = view;
    }

    private ArrayList<RestaurantModel> getCategoryList(){
        return Data.getSelectedCategoryData();
    }
    void getList(){
        view.onGetCategoryData(getCategoryList());
    }
}
