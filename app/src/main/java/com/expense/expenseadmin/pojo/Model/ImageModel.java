package com.expense.expenseadmin.pojo.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageModel implements Parcelable {

    private String id;

    private String placeID;

    private String URL;

    public ImageModel() {
    }

    public ImageModel(String placeID, String URL) {
        this.placeID = placeID;
        this.URL = URL;
    }

    public ImageModel(String id, String placeID, String URL) {
        this.id = id;
        this.placeID = placeID;
        this.URL = URL;
    }

    protected ImageModel(Parcel in) {
        id = in.readString();
        placeID = in.readString();
        URL = in.readString();
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(placeID);
        parcel.writeString(URL);
    }
}
