package com.expense.expenseadmin.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.expense.expenseadmin.R;
import com.expense.expenseadmin.pojo.PlaceImage;
import com.expense.expenseadmin.view.activities.displayImage.DisplayImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FullScreenPagerAdapter extends PagerAdapter {

    private DisplayImage mContext;
    private ArrayList<String> mData;
    private LayoutInflater mLayoutInflater;
    private static final String TAG = "MyPagerAdapter";

    public FullScreenPagerAdapter(DisplayImage mContext, ArrayList<String> mData ) {
        this.mContext = mContext;
        this.mData = mData;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.place_image_item ,container , false);
        ImageView imageView = view.findViewById(R.id.place_images);
//        imageView.setImageResource(mData.get(position).getmImage());
        Picasso.get().load(mData.get(position)).into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object view) {
        container.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
