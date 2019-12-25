package com.expense.expenseadmin.view.activities.editPlace;

import com.expense.expenseadmin.data.firebase.ImagesFirebaseProcess;
import com.expense.expenseadmin.data.firebase.PlaceFirebaseProcess;
import com.expense.expenseadmin.data.firebase.callbacks.ImageFbListener;
import com.expense.expenseadmin.data.firebase.callbacks.PlaceFirebaseListener;
import com.expense.expenseadmin.pojo.Model.PlaceModel;

import java.io.File;
import java.util.ArrayList;

public class EditPlacePresenter implements PlaceFirebaseListener, ImageFbListener {

    private EditActivity mContext;
    private EditPlaceView listener;
    private PlaceFirebaseProcess placeDb;
    private ImagesFirebaseProcess imageDb;

    private ArrayList<File> imageFiles;
    ArrayList<String> imageUrls;
    private String oldCategory;

    private ArrayList<File> images;
    private PlaceModel placeModel;

    public EditPlacePresenter(EditActivity mContext, EditPlaceView listener) {
        this.mContext = mContext;
        this.listener = listener;
        this.placeDb = new PlaceFirebaseProcess(mContext, this);
        this.imageDb = new ImagesFirebaseProcess(this);
    }

    public void deletePlace(PlaceModel place){
        placeDb.deletePlace(place);
    }
    public void editPlace(PlaceModel place, ArrayList<File> images, ArrayList<String> mImageUrls, String oldCategory) {
        try {
            this.imageUrls = mImageUrls;
            this.placeModel = place;
            this.imageFiles = images;
            this.oldCategory = oldCategory;
            if (images != null && !images.isEmpty()) {
                for (int i = 0; i < imageFiles.size(); i++) {
                    imageDb.addPlaceImage(imageFiles.get(i), i);
                }
            } else {
                placeModel.setImagesURL(imageUrls);
                placeDb.onEditPlace(place, oldCategory, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAddImageSuccess(String url, int position) {
        if (position == imageFiles.size() - 1) {
            imageUrls.add(url);
            placeModel.setImagesURL(imageUrls);
            placeDb.onEditPlace(placeModel, oldCategory, this);
        } else {
            imageUrls.add(url);
        }
    }

    @Override
    public void onAddImageFailure(Throwable t) {
        t.printStackTrace();
        listener.onEditPlace(false);
    }

    @Override
    public void onReadPlaceByCategory(ArrayList<PlaceModel> data) {

    }

    @Override
    public void onAddPlaceSuccess(boolean status, Throwable t) {

    }

    @Override
    public void onEditPlaceSuccess(boolean status, Throwable t) {
        if (!status) {
            if (t != null) {
                t.printStackTrace();
            }
        }
        if (listener != null)
            listener.onEditPlace(status);
    }

    @Override
    public void onDeletePlace(boolean status) {
        if (listener != null){
            listener.onDeletePlace(status);
        }
    }
}
