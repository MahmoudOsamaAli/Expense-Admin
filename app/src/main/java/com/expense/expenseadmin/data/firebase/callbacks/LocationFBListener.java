package com.expense.expenseadmin.data.firebase.callbacks;

import com.expense.expenseadmin.pojo.Model.LocationModel;

import java.util.ArrayList;

public interface LocationFBListener {

    void onReadPlaceLocation(ArrayList<LocationModel> data, Throwable t);
}
