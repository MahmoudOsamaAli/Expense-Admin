package com.expense.expenseadmin.data.firebase;

import android.util.Log;

import com.expense.expenseadmin.data.firebase.callbacks.UsersFirebaseListener;
import com.expense.expenseadmin.pojo.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class UsersFirebase {

    private final String TAG = "UsersFirebase";
    private String users_root = "Users";
    private String user_document = "admin";
    private String users_info = "UserInfo";
    private FirebaseFirestore db;
    private UsersFirebaseListener listener;

    public UsersFirebase(UsersFirebaseListener listener) {
        this.db = FirebaseFirestore.getInstance();
        this.listener = listener;
    }

    /**
     * saving user into Firestore database
     * if it doesn't exists, it will be created
     * it not, it will be updated.
     */
    public void saveUser(User user) {
        Log.d(TAG, "saveUser(): is called");

        try {
            db.collection(users_root).document(user_document).collection(user.getUid()).document(users_info).set(user).addOnCompleteListener(task -> {
                try {
                    if (task.isComplete()) {
                        Log.d(TAG, "saveUser(): task is complete");
                        if (task.isSuccessful()) {
                            Log.d(TAG, "saveUser(): task is successful");
                            if (listener != null) {
                                listener.onSaveNewUserIntoFirestore(true, null);
                            }
                        } else {
                            Log.d(TAG, "saveUser(): task is not successful");
                            if (listener != null) {
                                listener.onSaveNewUserIntoFirestore(false, null);
                            }
                        }
                    } else {
                        Log.d(TAG, "saveUser(): task is not complete");
                        if (listener != null) {
                            listener.onSaveNewUserIntoFirestore(false, null);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onSaveNewUserIntoFirestore(false, e);
                    }
                }
            }).addOnFailureListener(e -> {
                try {
                    Log.e(TAG, "saveUser(): task is failed");
                    if (listener != null) {
                        listener.onSaveNewUserIntoFirestore(false, e);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).addOnCanceledListener(() -> {
                try {
                    Log.e(TAG, "saveUser(): task is canceled");
                    if (listener != null) {
                        listener.onSaveNewUserIntoFirestore(false, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
