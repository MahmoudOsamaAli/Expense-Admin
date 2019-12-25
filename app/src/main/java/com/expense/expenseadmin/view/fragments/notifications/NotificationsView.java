package com.expense.expenseadmin.view.fragments.notifications;

import com.expense.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

public interface NotificationsView {
    void onGetNotifications(ArrayList<RestaurantModel> data);
}
