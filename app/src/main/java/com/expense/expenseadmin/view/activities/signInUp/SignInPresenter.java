package com.expense.expenseadmin.view.activities.signInUp;

import com.expense.expenseadmin.configs.App;
import com.expense.expenseadmin.data.firebase.UsersFirebase;
import com.expense.expenseadmin.data.firebase.callbacks.UsersFirebaseListener;
import com.expense.expenseadmin.pojo.User;

public class SignInPresenter implements UsersFirebaseListener {

    private SignInActivity mCurrent;
    private SignInListener mSignInListener;
    private UsersFirebase mUsersFirebase;

    public SignInPresenter(SignInActivity mCurrent, SignInListener mSignInListener) {
        this.mCurrent = mCurrent;
        this.mSignInListener = mSignInListener;
        this.mUsersFirebase = new UsersFirebase(this);
    }

    public void saveUserIntoFireStore(User user) {
        try {
            if (user != null) {
                if (App.mHandler != null) {
                    App.mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mUsersFirebase.saveUser(user);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    mSignInListener.onSaveNewUser(false,null);
                }
            }else{
                mSignInListener.onSaveNewUser(false,null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mSignInListener.onSaveNewUser(false,null);
        }
    }

    @Override
    public void onSaveNewUserIntoFirestore(boolean status, Throwable t) {
        mSignInListener.onSaveNewUser(status,t);
    }
}
