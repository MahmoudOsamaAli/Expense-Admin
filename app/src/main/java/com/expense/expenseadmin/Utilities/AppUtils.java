package com.expense.expenseadmin.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Base64;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.expense.expenseadmin.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    public static boolean isEmailValid(String email) {

        boolean isValid = false;
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean inNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager != null) {
                Network nw = connectivityManager.getActiveNetwork();
                NetworkCapabilities actNw = connectivityManager.getNetworkCapabilities(nw);
                if (actNw != null) {
                    if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }
                    if (actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    }
                    //for other device how are able to connect with Ethernet
                    return actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            } else {
                return false;
            }
        } else {
            if (connectivityManager != null) {
                NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
                if (nwInfo != null) {
                    return nwInfo.isConnected();
                }
            } else {
                return false;
            }
        }
        return false;
    }

    public static MaterialDialog showProgressDialog(Context context, String message) {
        return new MaterialDialog.Builder(context)
                //.title(getString(R.string.str_dialog_waiting_msg))
                .content(message)
                .cancelable(false)
                .autoDismiss(false)
                .progress(true, 100)
                .show();
    }

    public static void showAlertDialog(Context context, String message) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                .autoDismiss(false)
                .cancelable(false)
                //.title(title)
                .content(message)
                .positiveText(R.string.str_ok_lbl)
                .positiveColorRes(R.color.red)
                .onPositive((dialog, which) -> dialog.dismiss())
                .build();
        materialDialog.getTitleView().setTextSize(context.getResources().getDimension(R.dimen._8ssp));
        if (!materialDialog.isShowing()) materialDialog.show();
    }

    public static MaterialDialog showAlertDialogWithCustomView(Context context, int layoutResID) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                .autoDismiss(false)
                .cancelable(false)
                .customView(layoutResID, false)
//                .positiveText(R.string.str_ok_lbl)
                .onPositive((dialog, which) -> dialog.dismiss())
                .positiveColorRes(R.color.red)
                .build();
        materialDialog.getTitleView().setTextSize(context.getResources().getDimension(R.dimen._8ssp));
        if (!materialDialog.isShowing()) materialDialog.show();
        return materialDialog;
    }

    public interface CallBack {
        void OnPositiveClicked(MaterialDialog dlg);

        void OnNegativeClicked(MaterialDialog dlg);
    }

    public static void showConfirmationDialog(Context context, String message, String positiveTXT,
                                              String negativeTXT, CallBack callBack) {
        MaterialDialog materialDialog = new MaterialDialog.Builder(context)
                .autoDismiss(false)
                .cancelable(false)
                //.title(title)
                .content(message)
                .positiveText(positiveTXT)
                .positiveColorRes(R.color.red)
                .negativeText(negativeTXT)
                .negativeColorRes(R.color.red)
                .onPositive((dialog, which) -> callBack.OnPositiveClicked(dialog))
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                    callBack.OnNegativeClicked(dialog);
                })
                .build();
        materialDialog.getTitleView().setTextSize(context.getResources().getDimension(R.dimen._8ssp));
        if (!materialDialog.isShowing()) materialDialog.show();
    }

    /*Convert image file to Base64 string...*/
    public static String convertImgFileToBase64(File imgFile) {
        String imgBase64 = "";
        try {
            byte[] bytes = new byte[(int) imgFile.length()];
            FileInputStream fis = new FileInputStream(imgFile);
            BufferedInputStream buf = new BufferedInputStream(fis);
            boolean isRead = buf.read(bytes, 0, bytes.length) > 0;
            buf.close();
            if (isRead)
                imgBase64 = Base64.encodeToString(bytes, Base64.DEFAULT);
            //imgBase64 = imgBase64.replaceAll(System.getProperty("line.separator"), "");// to remove\n
        } catch (Exception e) {
            //
        }
        return "data:image/jpg;base64," + imgBase64;
    }

    public static void showToast(Context context, String message) {
        try {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
