package com.example.expenseadmin.view.activities.displayImage;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.expenseadmin.R;
import com.example.expenseadmin.adapters.FullScreenPagerAdapter;
import com.example.expenseadmin.data.Data;
import com.example.expenseadmin.pojo.PlaceImage;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayImage extends AppCompatActivity implements DisplayImageView{

    @BindView(R.id.viewpager_full_screen)
    ViewPager viewPager;
    int position;
    private DisplayImagePresenter presenter;
    private static final String TAG = "DisplayImage";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        presenter = new DisplayImagePresenter(this);
        presenter.getList();
        position = getIntent().getIntExtra("position" ,0);
        Log.i(TAG, "init: image position = "+position);
    }

    @Override
    public void onGetImages(ArrayList<PlaceImage> list) {
        FullScreenPagerAdapter adapter = new FullScreenPagerAdapter(this, list);
        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(position);
    }
}
