package com.expense.expenseadmin.data.firebase.callbacks;

public interface UsersFirebaseListener {


    void onSaveNewUserIntoFirestore(boolean status, Throwable t);

}
