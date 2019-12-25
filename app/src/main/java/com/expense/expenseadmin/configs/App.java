package com.expense.expenseadmin.configs;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.expense.expenseadmin.data.sqlite.DBHelper;
import com.expense.expenseadmin.data.sqlite.DBProcess;

import java.util.Locale;

public class App extends Application {

    private final String TAG = App.class.getName();
    //    public CurrentLoggedUser mLoggedUser = null;
    private SQLiteDatabase mDataBase = null;
    public static Handler mHandler;
    public static HandlerThread mHandlerThread;
    public static DBProcess mDBDbProcess;

    static {
        String THREAD_NAME = "Expense Thread";
        mHandlerThread = new HandlerThread(THREAD_NAME);
        mHandlerThread.start();
        mHandler = new Handler(mHandlerThread.getLooper());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            applyUsDefaultLang();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        //MultiDex.install(base);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Log.v(TAG, "Configuration changed," + newConfig.toString());
        try {
            applyUsDefaultLang();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyUsDefaultLang() {
        try {
            Locale.setDefault(Locale.US); // to show numbers in english not arabic.
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.v(TAG, ex.getMessage());
        }
    }

    public void setCurrentLoggedUser(long inventLocId, String username/*, String password*/) {
        try {
//            mLoggedUser = new CurrentLoggedUser(inventLocId, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SQLiteDatabase dbConnect() {
        try {
            if (mDataBase == null) {
                DBHelper helper = new DBHelper(getApplicationContext());
                mDataBase = helper.getWritableDatabase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDataBase;
    }

    public void dbDisconnect() {
        try {
            if (mDataBase != null) mDataBase.close();
            mDataBase = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
