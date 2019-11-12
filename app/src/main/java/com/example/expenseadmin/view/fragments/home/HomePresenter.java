package com.example.expenseadmin.view.fragments.home;

import com.example.expenseadmin.data.Data;
import com.example.expenseadmin.pojo.HomeViewModel;

import java.util.ArrayList;

public class HomePresenter {

    private HomeView view;

    public HomePresenter(HomeView view) {
        this.view = view;
    }

    private ArrayList<HomeViewModel> getCategories(){
        return Data.getCategoriesData();
    }
    void getCategoriesData(){
        view.onGetCategories(getCategories());
    }
}
