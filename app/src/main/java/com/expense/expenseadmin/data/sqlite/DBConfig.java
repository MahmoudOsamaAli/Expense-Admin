package com.expense.expenseadmin.data.sqlite;

import android.provider.BaseColumns;

import java.util.ArrayList;

public class DBConfig {


    private static final String DROP_TABLE_LBL = "DROP TABLE IF EXISTS ";
    private static final String CREATE_TABLE_LBL = "CREATE TABLE IF NOT EXISTS ";

    private static final String TEXT = " TEXT";
    private static final String DATE_TIME = " DATETIME";
    private static final String DATE = " DATE";
    private static final String INTEGER = " INTEGER";
    private static final String REAL = " REAL";
    private static final String NUMERIC = " NUMERIC";
    private static final String PrimaryKeyAutoIncrement = "PRIMARY KEY AUTOINCREMENT,";

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor public.
    public DBConfig() {
    }


    /* Locations Table...*/
    static class LocationsTable implements BaseColumns {
        static final String TABLE_NAME = "LocationsTable";
        static final String COLUMN_ID = "ID";
        static final String COLUMN_PLACE_ID = "PlaceID";
        static final String COLUMN_COUNTRY = "Country";
        static final String COLUMN_CITY = "City";
        static final String COLUMN_STREET = "Street";
        static final String COLUMN_LATITUDE = "latitude";
        static final String COLUMN_LONGITUDE = "Longitude";
    }

    /* Places Table...*/
    static class PlacesTable implements BaseColumns {
        static final String TABLE_NAME = "LocationsTable";
        static final String COLUMN_ID = "ID";
        static final String COLUMN_Name = "Name";
        static final String COLUMN_CATEGORY = "Category";
        static final String COLUMN_PHONE_NUMBER = "PhoneNo";
        static final String COLUMN_DESCRIPTION = "Description";
        static final String COLUMN_FACEBOOK_URL = "Facebook";
        static final String COLUMN_TWITTER_URL = "Twitter";
        static final String COLUMN_WEBSITE_URL = "Website";
        static final String COLUMN_LIKES_COUNT = "Likes";
        static final String COLUMN_OKAY_COUNT = "Okay";
        static final String COLUMN_DISLIKES_COUNT = "Dislikes";
    }

    /* Places Table...*/
    static class RequestsTable implements BaseColumns {
        static final String TABLE_NAME = "LocationsTable";
        static final String COLUMN_ID = "ID";
        static final String COLUMN_User_Name = "UserName";
        static final String COLUMN_DATE = "Date";
        static final String COLUMN_PLACE_ID = "PlaceID";
    }

    static class ImagesTable implements BaseColumns {
        static final String TABLE_NAME = "ImagesTable";
        static final String COLUMN_ID = "ID";
        static final String COLUMN_URL = "URL";
        static final String COLUMN_PLACE_ID = "PlaceID";
    }


    private static final String SQL_CREATE_LOCATIONS_TABLE = "CREATE TABLE " + LocationsTable.TABLE_NAME + " (" +
            LocationsTable.COLUMN_ID + TEXT + "," +
            LocationsTable.COLUMN_PLACE_ID + INTEGER + "," +
            LocationsTable.COLUMN_COUNTRY + TEXT + "," +
            LocationsTable.COLUMN_CITY + TEXT + "," +
            LocationsTable.COLUMN_STREET + TEXT + "," +
            LocationsTable.COLUMN_LATITUDE + REAL + "," +
            LocationsTable.COLUMN_LONGITUDE + REAL + ");";


    private static final String SQL_CREATE_PLACES_TABLE = "CREATE TABLE " + PlacesTable.TABLE_NAME + " (" +
            PlacesTable.COLUMN_ID + INTEGER + "," +
            PlacesTable.COLUMN_Name + TEXT + "," +
            PlacesTable.COLUMN_CATEGORY + TEXT + "," +
            PlacesTable.COLUMN_DESCRIPTION + TEXT + "," +
            PlacesTable.COLUMN_PHONE_NUMBER + TEXT + "," +
            PlacesTable.COLUMN_FACEBOOK_URL + TEXT + "," +
            PlacesTable.COLUMN_TWITTER_URL + TEXT + "," +
            PlacesTable.COLUMN_WEBSITE_URL + TEXT + "," +
            PlacesTable.COLUMN_LIKES_COUNT + INTEGER + "," +
            PlacesTable.COLUMN_OKAY_COUNT + INTEGER + "," +
            PlacesTable.COLUMN_DISLIKES_COUNT + INTEGER + ");";

    private static final String SQL_CREATE_REQUESTS_TABLE = "CREATE TABLE " + RequestsTable.TABLE_NAME + " (" +
            RequestsTable.COLUMN_ID + INTEGER + "," +
            RequestsTable.COLUMN_PLACE_ID + INTEGER + "," +
            RequestsTable.COLUMN_DATE + TEXT + "," +
            RequestsTable.COLUMN_User_Name + TEXT + ");";

    private static final String SQL_CREATE_IMAGES_TABLE = "CREATE TABLE " + ImagesTable.TABLE_NAME + " (" +
            ImagesTable.COLUMN_ID + INTEGER + PrimaryKeyAutoIncrement +
            ImagesTable.COLUMN_PLACE_ID + INTEGER + "," +
            ImagesTable.COLUMN_URL + TEXT + ");";

    static class TablesSqlStatements {

        static ArrayList<String> getAllSqlCreationStatements() {
            ArrayList<String> listTablesCreationStatementSql = new ArrayList<>();
            listTablesCreationStatementSql.add(SQL_CREATE_LOCATIONS_TABLE);
            listTablesCreationStatementSql.add(SQL_CREATE_PLACES_TABLE);
            listTablesCreationStatementSql.add(SQL_CREATE_REQUESTS_TABLE);
            listTablesCreationStatementSql.add(SQL_CREATE_IMAGES_TABLE);

            return listTablesCreationStatementSql;
        }

        static String deleteTable(String tableName) {
            return DROP_TABLE_LBL + tableName;
        }
    }
}
