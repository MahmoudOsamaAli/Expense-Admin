package com.example.expenseadmin.view.fragments.notifications;

import com.example.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

public interface NotificationsView {
    void onGetNotifications(ArrayList<RestaurantModel> data);
}
