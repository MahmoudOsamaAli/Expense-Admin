package com.expense.expenseadmin.data.firebase.callbacks;

import com.expense.expenseadmin.pojo.Model.PlaceModel;

import java.util.ArrayList;

public interface PlaceFirebaseListener {

    void onReadPlaceByCategory(ArrayList<PlaceModel> data);

    void onAddPlaceSuccess(boolean status, Throwable t);

    void onEditPlaceSuccess(boolean status, Throwable t);

    void onDeletePlace(boolean status);
}
