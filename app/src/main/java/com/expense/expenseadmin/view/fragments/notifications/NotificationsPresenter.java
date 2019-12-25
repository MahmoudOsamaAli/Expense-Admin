package com.expense.expenseadmin.view.fragments.notifications;

import com.expense.expenseadmin.data.Data;
import com.expense.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

public class NotificationsPresenter {

    private NotificationsView view;

    public NotificationsPresenter(NotificationsView view) {
        this.view = view;
    }
    private ArrayList<RestaurantModel> getNotificationsData (){
        return Data.getSelectedCategoryData();
    }
    void getNotifications(){
        view.onGetNotifications(getNotificationsData());
    }
}
