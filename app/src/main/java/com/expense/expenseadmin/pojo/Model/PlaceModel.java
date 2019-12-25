package com.expense.expenseadmin.pojo.Model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class PlaceModel implements Parcelable {

    private String id;

    private String name;

    private String category;

    private String phoneNumber;

    private String description;

    private String facebookUrl;

    private String twitterUrl;

    private String websiteUrl;

    private String instagramUrl;

    private ArrayList<LocationModel> locationModels;

    private int likesCount;

    private int okayCount;

    private int dislikesCount;

    private ArrayList<String> imagesURL;

    public PlaceModel() {
    }

    public PlaceModel(String id, String name, String category, String phoneNumber, String description, String facebookUrl, String twitterUrl,String instagramUrl, String websiteUrl, int likesCount, int okayCount, int dislikesCount, ArrayList<LocationModel> locationModels, ArrayList<String> imagesURL) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.facebookUrl = facebookUrl;
        this.twitterUrl = twitterUrl;
        this.instagramUrl = instagramUrl;
        this.websiteUrl = websiteUrl;
        this.likesCount = likesCount;
        this.okayCount = okayCount;
        this.dislikesCount = dislikesCount;
        this.locationModels = locationModels;
        this.imagesURL = imagesURL;
    }

    public PlaceModel(String name, String category, String phoneNumber, String description, String facebookUrl, String twitterUrl,String instagramUrl ,String websiteUrl, ArrayList<LocationModel> locationModels, int likesCount, int okayCount, int dislikesCount, ArrayList<String> imagesURL) {
        this.name = name;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.description = description;
        this.facebookUrl = facebookUrl;
        this.twitterUrl = twitterUrl;
        this.instagramUrl = instagramUrl;
        this.websiteUrl = websiteUrl;
        this.locationModels = locationModels;
        this.likesCount = likesCount;
        this.okayCount = okayCount;
        this.dislikesCount = dislikesCount;
        this.imagesURL = imagesURL;
    }

    protected PlaceModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        category = in.readString();
        phoneNumber = in.readString();
        description = in.readString();
        facebookUrl = in.readString();
        twitterUrl = in.readString();
        websiteUrl = in.readString();
        likesCount = in.readInt();
        okayCount = in.readInt();
        dislikesCount = in.readInt();
        locationModels = in.createTypedArrayList(LocationModel.CREATOR);
        imagesURL = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(category);
        parcel.writeString(phoneNumber);
        parcel.writeString(description);
        parcel.writeString(facebookUrl);
        parcel.writeString(twitterUrl);
        parcel.writeString(websiteUrl);
        parcel.writeInt(likesCount);
        parcel.writeInt(okayCount);
        parcel.writeInt(dislikesCount);
        parcel.writeTypedList(locationModels);
        parcel.writeStringList(imagesURL);
    }

    public static final Creator<PlaceModel> CREATOR = new Creator<PlaceModel>() {
        @Override
        public PlaceModel createFromParcel(Parcel in) {
            return new PlaceModel(in);
        }

        @Override
        public PlaceModel[] newArray(int size) {
            return new PlaceModel[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }

    public ArrayList<LocationModel> getLocationModels() {
        return locationModels;
    }

    public void setLocationModels(ArrayList<LocationModel> locationModels) {
        this.locationModels = locationModels;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getOkayCount() {
        return okayCount;
    }

    public void setOkayCount(int okayCount) {
        this.okayCount = okayCount;
    }

    public int getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(int dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public ArrayList<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(ArrayList<String> imagesURL) {
        this.imagesURL = imagesURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
