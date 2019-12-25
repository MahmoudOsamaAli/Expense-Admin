package com.expense.expenseadmin.view.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.expense.expenseadmin.R;
import com.expense.expenseadmin.Utilities.AppUtils;
import com.expense.expenseadmin.view.activities.editPlace.EditActivityListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPhotosRecAdapter extends RecyclerView.Adapter<EditPhotosRecAdapter.viewHolder> {

    private ArrayList<File> data;
    private Context context;
    private ArrayList<String> imageUrls;
    EditActivityListener mListener;

    public EditPhotosRecAdapter(ArrayList<File> data, ArrayList<String> imageUrls, Context context, EditActivityListener listener) {
        this.data = data;
        this.context = context;
        this.imageUrls = imageUrls;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public EditPhotosRecAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag_add_place_photos_rv_row_item, parent, false);

        return new EditPhotosRecAdapter.viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull EditPhotosRecAdapter.viewHolder holder, int position) {

        try {
            if (position < imageUrls.size()) {
                Picasso.get().load(imageUrls.get(position)).into(holder.mImageIV);
            } else {
                Picasso.get().load(data.get(imageUrls.size() - position)).into(holder.mImageIV);
//                holder.mImageIV.setImageURI(Uri.fromFile(data.get(position)));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (data.size() + imageUrls.size());
    }

    class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.frag_add_place_photos_rv_ri_image_view)
        ImageView mImageIV;

        @BindView(R.id.frag_add_place_photos_rv_ri_delete_ic)
        ImageView mDeleteIC;

        viewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            mDeleteIC.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.equals(mDeleteIC)) {
                AppUtils.showConfirmationDialog(context, context.getString(R.string.are_you_sure_you_want_to_delete_this_photo), context.getString(R.string.yes), context.getString(R.string.cancel), new AppUtils.CallBack() {
                    @Override
                    public void OnPositiveClicked(MaterialDialog dlg) {
                        if (getAdapterPosition() < imageUrls.size()) {
                            imageUrls.remove(getAdapterPosition());
                        } else {
                            data.remove(imageUrls.size() - getAdapterPosition());
                        }
                        notifyDataSetChanged();
                        dlg.dismiss();
                    }

                    @Override
                    public void OnNegativeClicked(MaterialDialog dlg) {
                        dlg.dismiss();
                    }
                });
            }
        }
    }
}