package com.expense.expenseadmin.view.fragments.requests;

import android.content.Context;

import com.expense.expenseadmin.Utilities.AppUtils;
import com.expense.expenseadmin.configs.App;
import com.expense.expenseadmin.data.firebase.RequestsFirebaseProcess;
import com.expense.expenseadmin.data.firebase.callbacks.RequestFBListener;
import com.expense.expenseadmin.pojo.Model.PlaceModel;

import java.util.ArrayList;

public class RequestsPresenter implements RequestFBListener {

    private RequestsView mListener;
    private Context context;
    private RequestsFirebaseProcess mRequestFB;

    public RequestsPresenter(RequestsView mListener, Context context) {
        this.mListener = mListener;
        this.context = context;
        this.mRequestFB = new RequestsFirebaseProcess(this);

    }

    void readRequestsFromFirestore() {
        if (App.mHandler != null) {
            if (mRequestFB != null) {
                mRequestFB.readRequests();
            } else {
                mListener.onReadRequests(null);
            }
        } else {
            mListener.onReadRequests(null);
        }
    }

    @Override
    public void onReadRequestFromFirestore(ArrayList<PlaceModel> requests) {
        if (mListener != null) {
            mListener.onReadRequests(requests);
        }
    }
}
