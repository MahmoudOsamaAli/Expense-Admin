package com.expense.expenseadmin.view.fragments.home;

import com.expense.expenseadmin.pojo.HomeViewModel;

import java.util.ArrayList;

public interface HomeView {
    void onGetCategories(ArrayList<HomeViewModel> list);
}
