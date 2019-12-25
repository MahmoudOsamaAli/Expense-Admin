package com.expense.expenseadmin.view.activities.selectedCategory;


import com.expense.expenseadmin.pojo.Model.PlaceModel;
import com.expense.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

public interface SelectedCategoryView {
    void readPlacesByCategoryFromFirebase(ArrayList<PlaceModel> places);

}
