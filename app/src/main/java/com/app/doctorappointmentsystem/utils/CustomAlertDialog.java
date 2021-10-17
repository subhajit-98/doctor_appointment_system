package com.app.doctorappointmentsystem.utils;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class CustomAlertDialog {
    String alertTitle, alertBody;
    Boolean isNegativeBtn=false;
    Context c;

    public CustomAlertDialog(String alertTitle, String alertBody, Boolean isNegativeBtn, Context c) {
        this.alertTitle = alertTitle;
        this.alertBody = alertBody;
        this.isNegativeBtn = isNegativeBtn;
        this.c = c;
    }

    public void showSimpleAlert(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(c);
        alertBuilder.setTitle(alertTitle);
        alertBuilder.setMessage(alertBody);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertBuilder.setCancelable(true);
            }
        });
        if(isNegativeBtn){
            alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertBuilder.setCancelable(true);
                }
            });
        }
        alertBuilder.show();
    }
}
