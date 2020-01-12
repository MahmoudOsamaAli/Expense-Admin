package com.expense.expenseadmin.data.firebase.callbacks;

import com.expense.expenseadmin.pojo.Model.PlaceModel;

import java.util.ArrayList;

public interface RequestFBListener {

    void onReadRequestFromFirestore(ArrayList<PlaceModel> requests);
}
