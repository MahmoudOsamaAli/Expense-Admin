package com.expense.expenseadmin.view.activities.Home;

import android.content.Context;

import com.expense.expenseadmin.pojo.Model.PlaceModel;

import java.util.ArrayList;

public interface HomeActivityListener {

    void readPlacesByCategoryFromFirebase(ArrayList<PlaceModel> places);

}
