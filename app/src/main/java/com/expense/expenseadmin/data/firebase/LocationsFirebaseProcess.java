package com.expense.expenseadmin.data.firebase;

import android.util.Log;

import com.expense.expenseadmin.data.firebase.callbacks.LocationFBListener;
import com.expense.expenseadmin.pojo.Model.LocationModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class LocationsFirebaseProcess {

    private FirebaseFirestore db;
    private final String LOCATION_COLLECTION = "locations";
    private final String TAG = "LocationsFirebase";
    public static String categories_collection = "Categories";
    public static String restaurant_document = "Restaurant";
    public static String places_collection = "places";
    private LocationFBListener callback;

    public LocationsFirebaseProcess(FirebaseFirestore db, LocationFBListener callback) {
        this.db = db;
        this.callback = callback;
    }

    public LocationsFirebaseProcess(LocationFBListener callback) {
        this.db = FirebaseFirestore.getInstance();
        this.callback = callback;
    }

    public void getPlaceLocations(String placeID) {
        ArrayList<LocationModel> result = new ArrayList<>();
        try {
            db/*.collection(categories_collection).document(restaurant_document)*/.collection(LOCATION_COLLECTION).document(placeID).get().addOnCompleteListener(task -> {
                try {
                    if (task.isSuccessful() && task.isComplete()) {
                        Log.i(TAG, "LocationsFirebaseProcess(): onComplete(): task is successful");
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if (documentSnapshot != null) {
                            String id = (String) documentSnapshot.get("id");
                            String country = (String) documentSnapshot.get("country");
                            String city = (String) documentSnapshot.get("country");
                            String street = (String) documentSnapshot.get("country");
                            String place_id = (String) documentSnapshot.get("country");
                            GeoPoint mGeoPoint = (GeoPoint) documentSnapshot.get("latlang");
                            double lat = 0, lang = 0;
                            if (mGeoPoint != null) {
                                lat = mGeoPoint.getLatitude();
                                lang = mGeoPoint.getLongitude();
                            }
                            result.add(new LocationModel(id, place_id, country, city, street, lat, lang));
                        }
                    } else {
                        Log.i(TAG, "LocationsFirebaseProcess(): onComplete(): task is Canceled " + task.isCanceled());
                    }
                    if (callback != null) {
                        callback.onReadPlaceLocation(result, null);
                    }
                } catch (Exception e) {
                    if (callback != null) {
                        callback.onReadPlaceLocation(null, e);
                    }
                }
            }).addOnFailureListener(e -> {
                if (callback != null) {
                    callback.onReadPlaceLocation(null, e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
