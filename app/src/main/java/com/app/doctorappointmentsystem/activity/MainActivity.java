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
import android.util.Log;
import android.widget.TextView;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.service.CheckInternet;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    public static TextView textView;

    public static final String BroadcastStringForAction = "checkinternet";
    private IntentFilter mIntentFilter;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        /*mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BroadcastStringForAction);
        Intent serviceIntent = new Intent(this, CheckInternet.class);
        startService(serviceIntent);*/

        IntentFilter intentFilter = new IntentFilter(CheckInternet.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                String networkStatus = isNetworkAvailable ? "Connected" : "Disconnected" ;

                Snackbar.make(textView, networkStatus, Snackbar.LENGTH_LONG).show();
            }
        }, intentFilter);

        broadcastReceiver = new CheckInternet();
        registerNetworkBroadcastReciver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
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