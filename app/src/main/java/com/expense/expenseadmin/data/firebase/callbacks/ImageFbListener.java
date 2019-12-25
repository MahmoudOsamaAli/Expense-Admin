package com.expense.expenseadmin.data.firebase.callbacks;

public interface ImageFbListener {

    void onAddImageSuccess(String url, int position);

    void onAddImageFailure(Throwable t);
}
