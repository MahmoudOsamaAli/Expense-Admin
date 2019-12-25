package com.expense.expenseadmin.view.activities.Home;

import android.content.Context;
import android.util.Log;

import com.expense.expenseadmin.configs.App;
import com.expense.expenseadmin.data.firebase.PlaceFirebaseProcess;
import com.expense.expenseadmin.data.firebase.callbacks.PlaceFirebaseListener;
import com.expense.expenseadmin.data.sqlite.DBProcess;
import com.expense.expenseadmin.pojo.Model.ImageModel;
import com.expense.expenseadmin.pojo.Model.LocationModel;
import com.expense.expenseadmin.pojo.Model.PlaceModel;

import java.util.ArrayList;

public class HomeActivityPresenter implements PlaceFirebaseListener {
    private final String TAG = "HomeActPresenter";

    private HomeActivityListener listener;
    private Context context;
    private PlaceFirebaseProcess placeFBProcess;
    private DBProcess dbProcess;

    HomeActivityPresenter(HomeActivityListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        this.dbProcess = new DBProcess(this.context);
    }

    public HomeActivityPresenter(Context context) {
        this.context = context;
    }

    void readPlaceByCategoryFromFireStore() {
        placeFBProcess = new PlaceFirebaseProcess(context, this);

//        ArrayList locations = new ArrayList();
//        locations.add(new LocationModel("1", "1", "Egypt", "Cairo", "Al Haram St", 30.02135, 31.21362));
//        locations.add(new LocationModel("2", "1", "Egypt", "Cairo", "Faisal St", 30.02135, 31.21362));
//        locations.add(new LocationModel("3", "1", "Egypt", "Cairo", "El Marghany St", 30.02135, 31.21362));
//        locations.add(new LocationModel("4", "1", "Egypt", "Cairo", "El Khalifa Al-Maamon St", 30.02135, 31.21362));
//
//        ArrayList<ImageModel> images = new ArrayList<>();
//        images.add(new ImageModel("1", "1", "https://www.imdb.com/title/tt2575988/"));
//        images.add(new ImageModel("1", "1", "https://www.imdb.com/title/tt2575988/"));
//
//        PlaceModel placeModel = new PlaceModel();
//        placeModel.setName("MAC");
//        placeModel.setId("1");
//        placeModel.setCategory("Restaurant");
//        placeModel.setDescription("Blah Blah Blah");
//        placeModel.setPhoneNumber("01096611061");
//        placeModel.setFacebookUrl("https://www.facebook.com/GoogleStudents/");
//        placeModel.setTwitterUrl("");
//        placeModel.setWebsiteUrl("https://firebase.google.com/docs/storage/android/start?authuser=0");
//        placeModel.setLikesCount(2);
//        placeModel.setOkayCount(2);
//        placeModel.setDislikesCount(3);
//        placeModel.setLocationModels(locations);
//        placeModel.setImagesURL(images);

//        placeFBProcess.addPlaceLocations(placeModel);

        placeFBProcess.readPlacesByCategory(PlaceFirebaseProcess.categories_collection, PlaceFirebaseProcess.restaurant_document, PlaceFirebaseProcess.places_collection);
    }

    @Override
    public void onReadPlaceByCategory(ArrayList<PlaceModel> data) {
        try {
            //save places into SQLite
            savePlacesIntoSQLite(data);

            // return data to activity
            if (listener != null) {
                listener.readPlacesByCategoryFromFirebase(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddPlaceSuccess(boolean status, Throwable t) {

    }

    @Override
    public void onEditPlaceSuccess(boolean status, Throwable t) {

    }

    @Override
    public void onDeletePlace(boolean status) {

    }

    private void savePlacesIntoSQLite(ArrayList<PlaceModel> data) {
        try {
            if (!data.isEmpty()) {
                if (App.mHandler != null) {
                    App.mHandler.post(() -> {
                        try {
                            dbProcess.insertPlace(data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
