package com.expense.expenseadmin.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expense.expenseadmin.R;
import com.expense.expenseadmin.pojo.Model.LocationModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyHolder> {


    private Context mContext;
    private ArrayList<LocationModel> data;
    private String placeName;
    private ArrayList<Double> distances;

    public LocationAdapter(Context mContext, ArrayList<LocationModel> data, ArrayList<Double> mDistances, String placeName) {
        this.mContext = mContext;
        this.data = data;
        this.placeName = placeName;
        this.distances = mDistances;
    }
    public LocationAdapter(Context mContext, ArrayList<LocationModel> data, String placeName) {
        this.mContext = mContext;
        this.data = data;
        this.placeName = placeName;
    }

    @NonNull
    @Override
    public LocationAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item
                , parent, false);
        return new LocationAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.MyHolder holder, int position) {
        holder.mStreet.setText(data.get(position).getStreet());
        holder.mCity.setText(data.get(position).getCity());
        if(distances != null ) {
            String distance = String.valueOf(distances.get(position));
            holder.mDistance.setText(distance);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.street)
        TextView mStreet;
        @BindView(R.id.city)
        TextView mCity;
        @BindView(R.id.distance_value)
        TextView mDistance;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(view -> {
                try {
                    placeName = placeName.replace("", "+");
                    // Creates an Intent that will load a map of San Francisco
                    double lat = data.get(getAdapterPosition()).getLatitude();
                    double lang = data.get(getAdapterPosition()).getLongitude();

                    Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + lang + "?q=" + lat + "," + lang + "(" + placeName + ")");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    itemView.getContext().startActivity(mapIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

