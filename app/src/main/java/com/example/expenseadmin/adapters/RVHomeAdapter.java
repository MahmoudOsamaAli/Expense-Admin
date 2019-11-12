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
import com.example.expenseadmin.view.activities.selectedCategory.SelectedCategory;
import com.example.expenseadmin.pojo.HomeViewModel;

import java.util.ArrayList;

public class RVHomeAdapter extends RecyclerView.Adapter<RVHomeAdapter.MyHolder> {

    private Context mContext;
    private ArrayList<HomeViewModel> mData;

    public RVHomeAdapter(Context mContext, ArrayList<HomeViewModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item
                , parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mText.setText(mData.get(position).getmText());
        holder.mImage.setImageResource(mData.get(position).getmImageId());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView mText;
        ImageView mImage;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.category_text);
            mImage = itemView.findViewById(R.id.category_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String label = mText.getText().toString();
                    Intent i = new Intent(mContext , SelectedCategory.class);
                    i.putExtra("label" , label);
                    mContext.startActivity(i);
                }
            });
        }
    }
}
