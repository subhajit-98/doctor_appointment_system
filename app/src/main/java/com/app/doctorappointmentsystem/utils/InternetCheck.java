package com.app.doctorappointmentsystem.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class InternetCheck {
    public static boolean isConnectedToInternet(Context context){
        try{
            if (context != null){
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
            return false;
        }
        catch (Exception e){
            Log.i(com.app.doctorappointmentsystem.service.CheckInternet.class.getName(), e.getMessage());
            return false;
        }
    }
}
