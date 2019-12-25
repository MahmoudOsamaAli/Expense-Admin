package com.expense.expenseadmin.view.activities.selectedCategory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ViewAnimator;

import com.expense.expenseadmin.R;
import com.expense.expenseadmin.view.adapters.SelectedCategoryAdapter;
import com.expense.expenseadmin.pojo.Model.ImageModel;
import com.expense.expenseadmin.pojo.Model.LocationModel;
import com.expense.expenseadmin.pojo.Model.PlaceModel;
import com.expense.expenseadmin.view.activities.Home.HomeActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedCategory extends AppCompatActivity implements SelectedCategoryView {

    @BindView(R.id.RV_selected_category)
    RecyclerView mRV;
    @BindView(R.id.selected_category_toolbar)
    Toolbar toolbar;
    @BindView(R.id.selected_category_activity_VA)
    ViewAnimator viewAnimator;

    SelectedCategoryPresenter presenter;
    SelectedCategory mCurrent;
    private final String TAG = "SelectedCategory";
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);
        init();
    }

    private void init() {
        try {
            ButterKnife.bind(this);
            mCurrent = SelectedCategory.this;

            category = getIntent().getStringExtra("label");

            presenter = new SelectedCategoryPresenter(mCurrent, mCurrent);

            //reading data from firebase
            presenter.readPlaceByCategoryFromFireStore(category);
            toolBarConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void toolBarConfig() {
        try {
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(category);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    toolbar.setNavigationOnClickListener(v -> {
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        finish();
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readPlacesByCategoryFromFirebase(ArrayList<PlaceModel> data) {
        try {


            Log.i(TAG, "readPlacesByCategoryFromFirebase(): is called");
            if (data != null && !data.isEmpty()) {
                Log.i(TAG, "readPlacesByCategoryFromFirebase(): places size = " + data.size());
                for (PlaceModel place : data) {
                    String result = "---------------------------------\n";
                    result += "id = " + place.getId() + " name = " + place.getName() + " phone = " + place.getPhoneNumber() + " \n";
                    result += "description = " + place.getDescription() + " facebook = " + place.getFacebookUrl() + " twitter = " + place.getTwitterUrl() + " \n";
                    result += "website = " + place.getWebsiteUrl() + " \n";
                    result += "\nLocations\n";

                    for (LocationModel locationModel : place.getLocationModels()) {
                        result += "Country = " + locationModel.getCountry() + " city = " + locationModel.getCity() + " Street = " + locationModel.getStreet();
                        result += " Latitude = " + locationModel.getLatitude() + " Longitude = " + locationModel.getLongitude() + "\n";
                    }
                    result += "\nImages\n";

                    for (String image : place.getImagesURL()) {
                        result += "place ID = " + place.getId() + " url = " + image + "\n";
                    }
                    result += "---------------------------------\n";
                    Log.i(TAG, "readPlacesByCategoryFromFirebase(): " + result);
                }

                viewAnimator.setDisplayedChild(2);
                LinearLayoutManager manager = new LinearLayoutManager(this);
                mRV.setLayoutManager(manager);
                SelectedCategoryAdapter adapter = new SelectedCategoryAdapter(this, data);
                mRV.setAdapter(adapter);
            } else {
                viewAnimator.setDisplayedChild(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
