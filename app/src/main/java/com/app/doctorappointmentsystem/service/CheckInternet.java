package com.app.doctorappointmentsystem.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.doctorappointmentsystem.activity.MainActivity;
import com.app.doctorappointmentsystem.utils.InternetCheck;
import com.google.android.material.snackbar.Snackbar;

/*public class CheckInternet extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not created yet!!");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.post(periodicUpdate);
        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    public boolean isOnline(Context c){
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnectedOrConnecting()){
            Log.i("internet", "Connected");
            return true;
        }
        Log.i("internet", "Not Connected");
        return false;
    }

    Handler handler = new Handler();
    private Runnable periodicUpdate = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(periodicUpdate, 1000);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MainActivity.BroadcastStringForAction);
            broadcastIntent.putExtra("online_status", ""+isOnline(CheckInternet.this));
            sendBroadcast(broadcastIntent);
        }
    };
}*/

public class CheckInternet extends BroadcastReceiver{

    public static final String NETWORK_AVAILABLE_ACTION = "NetworkAvailable";
    public static final String IS_NETWORK_AVAILABLE = "isNetworkAvailable";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent networkStateIntent = new Intent(NETWORK_AVAILABLE_ACTION);
        // networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, isConnectedToInternet(context));
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, InternetCheck.isConnectedToInternet(context));
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent);
        /*try{
            if(isConnectedToInternet(context)){
                Toast.makeText(context, "Network connected", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context, "No Network connected", Toast.LENGTH_SHORT).show();
            }
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }*/
    }

    // This function move to utils package
    /*private boolean isConnectedToInternet(Context context){
        try{
            if (context != null){
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
            return false;
        }
        catch (Exception e){
            Log.i(CheckInternet.class.getName(), e.getMessage());
            return false;
        }
    }*/
}
