package com.app.doctorappointmentsystem.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingAlertDialog {
    Context c;
    String message = "Loading. Please wait...";
    ProgressDialog progressDialog;

    public LoadingAlertDialog(Context c, String message) {
        this.c = c;
        this.message = message;
    }

    public void showLoading(){
        progressDialog = progressDialog.show(c, "",message, true);
    }

    public void hideLoading(){
        progressDialog.hide();
    }
}
