package com.example.expenseadmin.view.fragments.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expenseadmin.R;
import com.example.expenseadmin.adapters.SelectedCategoryAdapter;
import com.example.expenseadmin.data.Data;
import com.example.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsFragment extends Fragment implements NotificationsView{

    @BindView(R.id.RV_notification_fragment)
    RecyclerView mRV;
    @BindView(R.id.search_view_fragment_notification)
    SearchView mSearchView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        init();
    }

    private void init() {
        NotificationsPresenter presenter = new NotificationsPresenter(this);
        presenter.getNotifications();
        searchViewConfig();
    }
    @Override
    public void onGetNotifications(ArrayList<RestaurantModel> data) {
        RVConfig(data);
    }
    private void RVConfig(ArrayList<RestaurantModel> data) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRV.setLayoutManager(manager);
        SelectedCategoryAdapter adapter = new SelectedCategoryAdapter(getContext(), data);
        mRV.setAdapter(adapter);
    }
    private void searchViewConfig() {
        mSearchView.setOnClickListener(view -> mSearchView.setIconified(false));
    }
}