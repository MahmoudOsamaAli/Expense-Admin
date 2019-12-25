package com.expense.expenseadmin.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.expense.expenseadmin.configs.App;
import com.expense.expenseadmin.pojo.Model.ImageModel;
import com.expense.expenseadmin.pojo.Model.LocationModel;
import com.expense.expenseadmin.pojo.Model.PlaceModel;

import java.util.ArrayList;

public class DBProcess {


    private Context c;
    private App mAppContext;

    public DBProcess(Context context) {
        this.c = context;
        this.mAppContext = (App) this.c.getApplicationContext();

    }

    public boolean deleteTable(String tableName) {
        return ((getTableCount(tableName) <= 0)) ||
                (mAppContext.dbConnect().delete(tableName, null, null) > 0);/* No. of deleted rows*/
    }

    private int getTableCount(String tableName) {

        String countLbl = "COUNT";
        String sqlStm = "SELECT COUNT(*)  AS " + countLbl + " FROM " + tableName;
        try {
            Cursor cursor = mAppContext.dbConnect().rawQuery(sqlStm, null);
            int tableCount = 0;
            if (cursor != null) {
                cursor.moveToFirst();
                tableCount = cursor.getInt(cursor.getColumnIndex(countLbl));
                cursor.close();
            }
            return tableCount;
        } catch (Exception ex) {
            ex.getMessage();
            return 0;
        }
    }

    private void insertLocation(LocationModel locationModel) {

        try {
            ContentValues cv = new ContentValues();
            cv.put(DBConfig.LocationsTable.COLUMN_ID, locationModel.getId());
            cv.put(DBConfig.LocationsTable.COLUMN_PLACE_ID, locationModel.getPlaceID());
            cv.put(DBConfig.LocationsTable.COLUMN_COUNTRY, locationModel.getCountry());
            cv.put(DBConfig.LocationsTable.COLUMN_CITY, locationModel.getCity());
            cv.put(DBConfig.LocationsTable.COLUMN_STREET, locationModel.getStreet());
            cv.put(DBConfig.LocationsTable.COLUMN_LATITUDE, locationModel.getLatitude());
            cv.put(DBConfig.LocationsTable.COLUMN_LONGITUDE, locationModel.getLongitude());

            mAppContext.dbConnect().insert(DBConfig.LocationsTable.TABLE_NAME, null, cv);
            mAppContext.dbDisconnect();

        } catch (Exception e) {
            e.printStackTrace();
            mAppContext.dbDisconnect();
        }
    }

    private void insertImage(String imageModel, String placeID) {

        try {
            ContentValues cv = new ContentValues();
            cv.put(DBConfig.ImagesTable.COLUMN_PLACE_ID, placeID);
            cv.put(DBConfig.ImagesTable.COLUMN_URL, imageModel);

            mAppContext.dbConnect().insert(DBConfig.ImagesTable.TABLE_NAME, null, cv);
            mAppContext.dbDisconnect();
        } catch (Exception e) {
            e.printStackTrace();
            mAppContext.dbDisconnect();
        }
    }

    public void insertPlace(ArrayList<PlaceModel> placeModels) {

        try {
            for (PlaceModel placeModel : placeModels) {
                ContentValues cv = new ContentValues();

                cv.put(DBConfig.PlacesTable.COLUMN_ID, placeModel.getId());
                cv.put(DBConfig.PlacesTable.COLUMN_Name, placeModel.getName());
                cv.put(DBConfig.PlacesTable.COLUMN_CATEGORY, placeModel.getCategory());
                cv.put(DBConfig.PlacesTable.COLUMN_DESCRIPTION, placeModel.getDescription());
                cv.put(DBConfig.PlacesTable.COLUMN_PHONE_NUMBER, placeModel.getPhoneNumber());
                cv.put(DBConfig.PlacesTable.COLUMN_FACEBOOK_URL, placeModel.getFacebookUrl());
                cv.put(DBConfig.PlacesTable.COLUMN_TWITTER_URL, placeModel.getTwitterUrl());
                cv.put(DBConfig.PlacesTable.COLUMN_WEBSITE_URL, placeModel.getWebsiteUrl());
                cv.put(DBConfig.PlacesTable.COLUMN_LIKES_COUNT, placeModel.getLikesCount());
                cv.put(DBConfig.PlacesTable.COLUMN_OKAY_COUNT, placeModel.getOkayCount());
                cv.put(DBConfig.PlacesTable.COLUMN_DISLIKES_COUNT, placeModel.getDislikesCount());

                mAppContext.dbConnect().insert(DBConfig.PlacesTable.TABLE_NAME, null, cv);
                mAppContext.dbDisconnect();

                for (LocationModel locationModel : placeModel.getLocationModels()) {
                    insertLocation(locationModel);
                }

                for (String imageModel : placeModel.getImagesURL()) {
                    insertImage(imageModel, placeModel.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mAppContext.dbDisconnect();
        }
    }


}
