package com.expense.expenseadmin.data.firebase;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.expense.expenseadmin.data.firebase.callbacks.ImageFbListener;
import com.expense.expenseadmin.pojo.Model.ImageModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class ImagesFirebaseProcess {

    private static final String TAG = "ImagesFirebaseProcess";
    private FirebaseFirestore db;

    private ImageFbListener listener;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    final String fireStorageImagesFolderPath = "images/";

    public ImagesFirebaseProcess(FirebaseFirestore db) {
        this.db = db;
    }

    public ImagesFirebaseProcess(ImageFbListener listener) {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        this.listener = listener;
    }

    public void addPlaceImage(File imageFile, int position) {
        Uri file = Uri.fromFile(imageFile);
        String fileName = fireStorageImagesFolderPath + imageFile.getName();
        Log.i(TAG, "addPlaceImage(): fileName = " + fileName);
        StorageReference fileReference = storageReference.child(fileName);
        fileReference.putFile(file).continueWithTask(task -> {
            if (!task.isSuccessful()) {
                if (task.getException() != null)
                    throw task.getException();
            }
            return fileReference.getDownloadUrl();

        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (listener != null) {
                    Log.i(TAG, "addPlaceImage(): path = " + task.getResult().toString());
                    listener.onAddImageSuccess(task.getResult().toString(), position);
                }
            }

        }).addOnFailureListener(e -> {
            if (listener != null) {
                listener.onAddImageFailure(e);
            }
        });
    }

    public ArrayList<ImageModel> getPlaceImages(String placeID) {
        ArrayList<ImageModel> result = new ArrayList<>();
        try {
            db.collection(placeID);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


}
