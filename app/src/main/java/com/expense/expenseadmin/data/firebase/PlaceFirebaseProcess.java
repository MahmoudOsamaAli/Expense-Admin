package com.expense.expenseadmin.data.firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.expense.expenseadmin.Utilities.PlaceColumns;
import com.expense.expenseadmin.data.firebase.callbacks.LocationFBListener;
import com.expense.expenseadmin.data.firebase.callbacks.PlaceFirebaseListener;
import com.expense.expenseadmin.data.sqlite.DBProcess;
import com.expense.expenseadmin.pojo.Model.LocationModel;
import com.expense.expenseadmin.pojo.Model.PlaceModel;
import com.expense.expenseadmin.view.activities.editPlace.EditPlacePresenter;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaceFirebaseProcess implements LocationFBListener {

    private FirebaseFirestore db;
    private static String TAG = "PlaceFirebase";
    public static String categories_collection = "Categories";
    public static String restaurant_document = "Restaurant";
    public static String places_collection = "places";

    private LocationsFirebaseProcess locationsFirebase;
    private ImagesFirebaseProcess imagesFirebase;
    private DBProcess dbProcess;
    private Context context;
    private PlaceFirebaseListener callback;
    private ArrayList<PlaceModel> placesResult;

    public PlaceFirebaseProcess(Context context, PlaceFirebaseListener callback) {
        this.context = context;
        this.dbProcess = new DBProcess(this.context);
        this.db = FirebaseFirestore.getInstance();
        this.locationsFirebase = new LocationsFirebaseProcess(db, this);
        this.callback = callback;

    }

    public void readPlacesByCategory(String root, String category, String collection) {
        try {
            db.collection(root).document(category).collection(collection).addSnapshotListener((snapshot, e) -> {
                if (e == null) {
                    try {
                        //initializing places result list
                        placesResult = new ArrayList<>();
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
                        returningPlacesByCategoryResult(callback, placesResult);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void returningPlacesByCategoryResult(PlaceFirebaseListener callback, ArrayList<PlaceModel> placeModels) {
        try {
            //checking callback nullability
            if (callback != null) {
                //sending result over callback
                callback.onReadPlaceByCategory(placeModels);
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

    public void addPlace(PlaceModel placeModel, PlaceFirebaseListener listener) {
        db.collection(categories_collection).document(placeModel.getCategory()).collection(places_collection).add(placeModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                try {
                    if (task.isSuccessful()) {
                        if (listener != null) {
                            listener.onAddPlaceSuccess(true, null);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (listener != null) {
                    listener.onAddPlaceSuccess(false, e);
                }
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {
                if (listener != null) {
                    listener.onAddPlaceSuccess(false, null);
                }
            }
        });
    }

    @Override
    public void onReadPlaceLocation(ArrayList<LocationModel> data, Throwable t) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onEditPlace(PlaceModel place, String oldCategory, EditPlacePresenter listener) {
        try {
            Log.i(TAG, "onEditPlace(): is called");

            if (!oldCategory.matches(place.getCategory())) {
                db.collection(categories_collection).document(oldCategory).collection(places_collection).document(place.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i(TAG, "onEditPlace(): place " + place.getName() + " in " + oldCategory + " is deleted Successfully");
                    }
                });
            }
            applyPlaceEdit(place);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyPlaceEdit(PlaceModel place) {
        try {

            db.collection(categories_collection).document(place.getCategory()).collection(places_collection).document(place.getId()).set(place).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (callback != null) {
                        callback.onEditPlaceSuccess(true, null);
                    }
                }
            }).addOnFailureListener(e -> {
                if (callback != null) {
                    callback.onEditPlaceSuccess(false, e);
                }
            }).addOnCanceledListener(() -> {
                if (callback != null) {
                    callback.onEditPlaceSuccess(false, null);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePlace(PlaceModel place) {

        db.collection(categories_collection).document(place.getCategory()).collection(places_collection).document(place.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (callback != null) {
                        callback.onDeletePlace(true);
                    }
                } else if (task.isCanceled()) {
                    if (callback != null) {
                        callback.onDeletePlace(false);
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                if (callback != null) {
                    callback.onDeletePlace(false);
                }
            }
        });
    }
}
