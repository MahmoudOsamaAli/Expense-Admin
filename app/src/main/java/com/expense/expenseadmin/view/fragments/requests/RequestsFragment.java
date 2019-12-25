package com.expense.expenseadmin.view.fragments.requests;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expense.expenseadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    public RequestsFragment() {
        // Required empty public constructor
    }

    public static RequestsFragment newInstance() {

        return new RequestsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requests, container, false);
    }
}
