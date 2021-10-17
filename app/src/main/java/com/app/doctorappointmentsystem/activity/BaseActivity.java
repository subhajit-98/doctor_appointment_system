package com.app.doctorappointmentsystem.activity;

import static com.app.doctorappointmentsystem.service.CheckInternet.IS_NETWORK_AVAILABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.service.CheckInternet;
import com.google.android.material.snackbar.Snackbar;

public class BaseActivity extends AppCompatActivity {

    // LinearLayout base_activity_layout;
    BroadcastReceiver broadcastReceiver;
    Boolean isOnlineCheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // base_activity_layout = findViewById(R.id.base_activity_layout);

        // checkInternet(base_activity_layout);
    }

    public void checkInternet(View view){
        IntentFilter intentFilter = new IntentFilter(CheckInternet.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);

                /*String networkStatus = isNetworkAvailable ? "Connected" : "Disconnected" ;
                Snackbar.make(view, networkStatus, Snackbar.LENGTH_LONG).show();*/

                if(!isNetworkAvailable) {
                    isOnlineCheck = false;
                    Snackbar.make(view, "Offline", Snackbar.LENGTH_LONG).show();
                }

                if((!isOnlineCheck) && (isNetworkAvailable)){
                    Snackbar.make(view, "Online", Snackbar.LENGTH_LONG).show();
                    isOnlineCheck = true;
                }

            }
        }, intentFilter);

        broadcastReceiver = new CheckInternet();
        registerNetworkBroadcastReciver();
    }

    protected void registerNetworkBroadcastReciver(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }
}