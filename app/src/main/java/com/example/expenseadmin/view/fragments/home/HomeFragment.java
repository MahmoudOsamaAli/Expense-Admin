package com.example.expenseadmin.view.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expenseadmin.R;
import com.example.expenseadmin.data.Data;
import com.example.expenseadmin.pojo.HomeViewModel;
import com.example.expenseadmin.adapters.RVHomeAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements HomeView{

    @BindView(R.id.categories_RV)
    RecyclerView mRV;
    @BindView(R.id.search_view_fragment)
    SearchView mSearchView;
    private HomePresenter presenter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this , view);
        init();
    }

    private void init() {
        presenter = new HomePresenter(this);
        presenter.getCategoriesData();
        searchViewConfig();
    }

    @Override
    public void onGetCategories(ArrayList<HomeViewModel> list) {
        RVConfig(list);
    }
    private void RVConfig(ArrayList<HomeViewModel> data) {
        GridLayoutManager manager = new GridLayoutManager(getContext() , 2);
        mRV.setLayoutManager(manager);
        RVHomeAdapter adapter = new RVHomeAdapter(getContext() , data);
        mRV.setAdapter(adapter);
    }
    private void searchViewConfig() {
        mSearchView.setOnClickListener(view -> mSearchView.setIconified(false));
    }
}