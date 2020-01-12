package com.expense.expenseadmin.data.firebase;

import android.util.Log;

import androidx.annotation.Nullable;

import com.expense.expenseadmin.Utilities.PlaceColumns;
import com.expense.expenseadmin.data.firebase.callbacks.PlaceFirebaseListener;
import com.expense.expenseadmin.data.firebase.callbacks.RequestFBListener;
import com.expense.expenseadmin.pojo.Model.LocationModel;
import com.expense.expenseadmin.pojo.Model.PlaceModel;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestsFirebaseProcess {

    private static final String TAG = "RequestsFirebaseProcess";
    private RequestFBListener listener;

    private final String requests_root = "Requests";
    private FirebaseFirestore db;
    public RequestsFirebaseProcess(RequestFBListener listener) {
        this.listener = listener;
        this.db = FirebaseFirestore.getInstance();
    }


    public void readRequests() {
        db.collection(requests_root).addSnapshotListener((snapshot, e) -> {
            if (e == null) {
                try {
                    //initializing places result list
                    ArrayList<PlaceModel> placesResult = new ArrayList<>();
                    if (snapshot != null) {
                        Log.i(TAG, "readPlacesByCategory(): snapshot not null");
                        Log.i(TAG, "readPlacesByCategory(): snapshot size = " + snapshot.size());

                        for (QueryDocumentSnapshot queryDocumentSnapshot : snapshot) {
                            //initializing location result list
                            ArrayList<LocationModel> locationsResult = new ArrayList<>();
                            //initializing images result list
                            ArrayList<String> imagesResult = new ArrayList<>();

                            //reading place data from document
                            HashMap<String, Object> data = (HashMap<String, Object>) queryDocumentSnapshot.getData();
                            //reading locations data from document
                            ArrayList<Map<String, String>> locations = (ArrayList<Map<String, String>>) data.get(PlaceColumns.locationModels);
                            //reading images data from document
                            ArrayList<String> images = (ArrayList<String>) queryDocumentSnapshot.get(PlaceColumns.imagesURL);

                            Log.i(TAG, "readPlacesByCategory(): data size = " + data.size());
                            //extracting locations from result
                            extractLocations(locations, locationsResult);
                            //extracting images from result
                            extractImages(imagesResult, images);

                            //extracting place from result
                            PlaceModel placeModel = extractPlace(queryDocumentSnapshot, data, imagesResult, locationsResult);
                            //adding place result into places result list
                            placesResult.add(placeModel);

                            Log.i(TAG, "readPlacesByCategory(): places size = " + placesResult.size());

                        }
                    } else {
                        Log.i(TAG, "readPlacesByCategory(): snapshot is null");
                    }
                    //returning result to callback
                    returningPlacesByCategoryResult(listener, placesResult);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        });
    }

    private void returningPlacesByCategoryResult(RequestFBListener callback, ArrayList<PlaceModel> placeModels) {
        try {
            //checking callback nullability
            if (callback != null) {
                //sending result over callback
                callback.onReadRequestFromFirestore(placeModels);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void extractLocations(ArrayList<Map<String, String>> locations, ArrayList<LocationModel> locationModels) {
        try {
            //checking if result not null or empty
            if (locations != null && !locations.isEmpty()) {
                //looping over location from FireStore
                for (Map<String, String> location : locations) {
                    Log.i(TAG, "extractLocations(): locations size = " + location.size());
                    try {
                        //extracting location into location model
                        LocationModel locationModel = extractLocation(location);
                        //adding location into list
                        locationModels.add(locationModel);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void extractImages(ArrayList<String> imageModels, ArrayList<String> images) {
        try {
            //checking if images not null or empty
            if (images != null && !images.isEmpty()) {
                Log.i(TAG, "extractImages(): images size = " + images.size());
                //looping over images from FireStore
                for (String image : images) {
                    try {
                        //adding image into list
                        imageModels.add(image);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            } else {
                Log.i(TAG, "extractImages(): images size = 0 ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PlaceModel extractPlace(QueryDocumentSnapshot queryDocumentSnapshot, HashMap<String, Object> data, ArrayList<String> imageModels, ArrayList<LocationModel> locationModels) {
        return new PlaceModel(
                queryDocumentSnapshot.getId(),
                (String) data.get(PlaceColumns.name),
                (String) data.get(PlaceColumns.category),
                (String) data.get(PlaceColumns.phoneNumber),
                (String) data.get(PlaceColumns.description),
                (String) data.get(PlaceColumns.facebookUrl),
                (String) data.get(PlaceColumns.twitterUrl),
                (String) data.get(PlaceColumns.instagramUrl),
                (String) data.get(PlaceColumns.websiteUrl),
                Integer.parseInt(String.valueOf(data.get(PlaceColumns.likesCount))),
                Integer.parseInt(String.valueOf(data.get(PlaceColumns.okayCount))),
                Integer.parseInt(String.valueOf(data.get(PlaceColumns.dislikesCount))),
                locationModels,
                imageModels
        );
    }

    private LocationModel extractLocation(Map<String, String> location) {
        return new LocationModel(
                location.get(PlaceColumns.id),
                location.get(PlaceColumns.placeID),
                location.get(PlaceColumns.country),
                location.get(PlaceColumns.city),
                location.get(PlaceColumns.street),
                Double.parseDouble(String.valueOf(location.get(PlaceColumns.latitude))),
                Double.parseDouble(String.valueOf(location.get(PlaceColumns.longitude))
                ));
    }

}
