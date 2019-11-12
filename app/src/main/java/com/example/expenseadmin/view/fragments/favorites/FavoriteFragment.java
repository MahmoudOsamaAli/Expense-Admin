package com.example.expenseadmin.view.fragments.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
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

public class FavoriteFragment extends Fragment implements FavoritesView{

    @BindView(R.id.RV_favorite_fragment)
    RecyclerView mRV;
    private FavoritesPresenter presenter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this , view);
        init();
    }

    private void init() {
        presenter = new FavoritesPresenter(this);
        presenter.getFavorites();
    }

    @Override
    public void onGetFavoriteData(ArrayList<RestaurantModel> list) {
        setmRV(list);
    }
    private void setmRV(ArrayList<RestaurantModel> list){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRV.setLayoutManager(manager);
        SelectedCategoryAdapter adapter = new SelectedCategoryAdapter(getContext() , list);
        mRV.setAdapter(adapter);
    }
}