package com.example.expenseadmin.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expenseadmin.R;
import com.example.expenseadmin.view.activities.placeDetails.PlaceDetails;
import com.example.expenseadmin.pojo.RestaurantModel;

import java.util.ArrayList;

public class SelectedCategoryAdapter extends RecyclerView.Adapter<SelectedCategoryAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<RestaurantModel> data;

    public SelectedCategoryAdapter(Context mContext, ArrayList<RestaurantModel> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_selected_item
                , parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mName.setText(data.get(position).getmName());
        holder.mImage.setImageResource(data.get(position).getmImage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView mName;
        ImageView mImage;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.place_name);
            mImage = itemView.findViewById(R.id.place_image);
            itemView.setOnClickListener(v ->
                    mContext.startActivity(new Intent(mContext , PlaceDetails.class)));
        }
    }
}
