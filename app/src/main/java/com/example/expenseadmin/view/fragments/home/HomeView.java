package com.example.expenseadmin.view.fragments.home;

import com.example.expenseadmin.pojo.HomeViewModel;

import java.util.ArrayList;

public interface HomeView {
    void onGetCategories(ArrayList<HomeViewModel> list);
}
