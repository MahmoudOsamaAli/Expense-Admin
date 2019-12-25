package com.expense.expenseadmin.view.fragments.home;

import com.expense.expenseadmin.data.Data;
import com.expense.expenseadmin.pojo.HomeViewModel;

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
