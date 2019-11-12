package com.example.expenseadmin.view.activities.selectedCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.expenseadmin.R;
import com.example.expenseadmin.adapters.SelectedCategoryAdapter;
import com.example.expenseadmin.pojo.RestaurantModel;
import com.example.expenseadmin.view.activities.Home.HomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedCategory extends AppCompatActivity implements SelectedCategoryView {

    @BindView(R.id.RV_selected_category)
    RecyclerView mRV;
    @BindView(R.id.selected_category_toolbar)
    Toolbar toolbar;
    SelectedCategoryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        presenter = new SelectedCategoryPresenter(this);
        presenter.getList();
        toolBarConfig();
    }

    private void toolBarConfig() {
        if(toolbar != null) {
            String label = getIntent().getStringExtra("label");
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(label);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                toolbar.setNavigationOnClickListener(v -> {
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                });
            }
        }
    }

    @Override
    public void onGetCategoryData(ArrayList<RestaurantModel> list) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRV.setLayoutManager(manager);
        SelectedCategoryAdapter adapter = new SelectedCategoryAdapter(this, list);
        mRV.setAdapter(adapter);
    }
}
