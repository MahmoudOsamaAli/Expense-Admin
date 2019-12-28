package com.expense.expenseadmin.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.expense.expenseadmin.R;
import com.expense.expenseadmin.pojo.Model.PlaceModel;
import com.expense.expenseadmin.view.activities.placeDetails.PlaceDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SelectedCategoryAdapter extends RecyclerView.Adapter<SelectedCategoryAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<PlaceModel> data;
    private int lastPosition = -1;

    public SelectedCategoryAdapter(Context mContext, ArrayList<PlaceModel> data) {
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
        try {
            holder.mName.setText(data.get(position).getName());
            holder.mDescriptionTV.setText(data.get(position).getDescription());
            Picasso.get().load(data.get(position).getImagesURL().get(0)).into(holder.mImage);
//            setAnimation(holder.parent , position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.zoom_out);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView mName, mDescriptionTV;
        ImageView mImage;
        ConstraintLayout parent;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            try {
                parent = itemView.findViewById(R.id.parent);
                mName = itemView.findViewById(R.id.place_name);
                mImage = itemView.findViewById(R.id.place_image);
                mDescriptionTV = itemView.findViewById(R.id.description_preview);

                itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, PlaceDetails.class);
                    intent.putExtra(mContext.getString(R.string.place_intent_lbl), data.get(getAdapterPosition()));
                    mContext.startActivity(intent);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
