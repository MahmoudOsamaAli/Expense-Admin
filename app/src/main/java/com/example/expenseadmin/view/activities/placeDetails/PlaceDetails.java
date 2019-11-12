package com.example.expenseadmin.view.activities.placeDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.expenseadmin.R;
import com.example.expenseadmin.adapters.LocationAdapter;
import com.example.expenseadmin.adapters.MyPagerAdapter;
import com.example.expenseadmin.data.Data;
import com.example.expenseadmin.pojo.PlaceImage;
import com.example.expenseadmin.pojo.locationModel;
import com.mancj.slimchart.SlimChart;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class PlaceDetails extends AppCompatActivity implements PlaceDetailsView{

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.circle)
    CircleIndicator circleIndicator;
    @BindView(R.id.seekBar_likes)
    SeekBar seekBar1;
    @BindView(R.id.seekBar_okays)
    SeekBar seekBar2;
    @BindView(R.id.seekBar_dislikes)
    SeekBar seekBar3;
    @BindView(R.id.likes_text)
    TextView likeText;
    @BindView(R.id.okays_text)
    TextView okeyText;
    @BindView(R.id.dislikes_text)
    TextView disLikeText;
    @BindView(R.id.RV_places_location)
    RecyclerView recyclerView;
    private MyPagerAdapter myPager;
    private PlaceDetailsPresenter presenter;

    private static final String TAG = "PlaceDetails";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        try {
            presenter = new PlaceDetailsPresenter(this);
            presenter.getLocationsList();
            presenter.getPlaceImagesList();
            presenter.getRatings();
            presenter.getChartRating();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGetLocations(ArrayList<locationModel> list) {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        LocationAdapter adapter = new LocationAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGetPlaceImages(ArrayList<PlaceImage> list) {
        myPager = new MyPagerAdapter(this, list );
        viewPager.setAdapter(myPager);
        viewPager.setCurrentItem(myPager.getCount() - 1);
        circleIndicator.setViewPager(viewPager);
    }

    @Override
    public void onGetSeekBarRating(ArrayList<Integer> list) {
        int val1 = list.get(0);
        Log.i(TAG, "onGetSeekBarRating: value 1 = " + val1);
        int val2 = list.get(1);
        int val3 = list.get(2);
        setSeekBars(val1 , val2 , val3);
    }

    @Override
    public void onGetChartRating(double value) {
        SlimChart slimChart = findViewById(R.id.slimChart);

        int[] colors = new int[4];
        colors[0] = Color.rgb(185, 185, 185);
        colors[1] = Color.rgb(23, 246, 9);
        slimChart.setColors(colors);

        final float[] stats = new float[2];
        stats[0] = 100;
        stats[1] = (float) value * 10;
        slimChart.setStats(stats);
        //Play animation
        slimChart.setStartAnimationDuration(1500);
        slimChart.playStartAnimation();

        slimChart.setText(Double.toString(value));
        slimChart.setRoundEdges(true);
    }

    private void setSeekBars(int val1 , int val2 , int val3){
        Log.i(TAG, "setSeekBars: value 1 = " + val1);
        seekBar1.setMax(100);
        seekBar2.setMax(100);
        seekBar3.setMax(100);
        String likes = val1 + getResources().getString(R.string.likes_format);
        String okays = val2 + getResources().getString(R.string.okay_format);
        String dislikes = val3 + getResources().getString(R.string.dislikes_format);
        likeText.setText(likes);
        okeyText.setText(okays);
        disLikeText.setText(dislikes);
        ValueAnimator anim1 = ValueAnimator.ofInt(0, val1);
        anim1.setDuration(1500);
        ValueAnimator anim2 = ValueAnimator.ofInt(0, val2);
        anim1.setDuration(1500);
        ValueAnimator anim3 = ValueAnimator.ofInt(0, val3);
        anim1.setDuration(1500);
        anim1.addUpdateListener(animation -> seekBar1.setProgress((Integer) animation.getAnimatedValue()));
        anim1.start();
        anim2.addUpdateListener(animation -> seekBar2.setProgress((Integer) animation.getAnimatedValue()));
        anim2.start();
        anim3.addUpdateListener(animation -> seekBar3.setProgress((Integer) animation.getAnimatedValue()));
        anim3.start();
    }
}
