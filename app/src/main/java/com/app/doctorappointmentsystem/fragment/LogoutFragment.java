package com.app.doctorappointmentsystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.activity.HomeActivity;
import com.app.doctorappointmentsystem.activity.UserActivity;

public class LogoutFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences logIn_sp = getActivity().getSharedPreferences("log_in_save", Context.MODE_PRIVATE);
        SharedPreferences.Editor login_ed = logIn_sp.edit();
        login_ed.clear();
        login_ed.commit();

        SharedPreferences sp = getActivity().getSharedPreferences("logged_in", Context.MODE_PRIVATE);
        SharedPreferences.Editor sp_edit = sp.edit();
        sp_edit.clear();
        sp_edit.commit();

        new CountDownTimer(1000, 2000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                startActivity(new Intent(getActivity(), UserActivity.class));
                getActivity().finish();
            }
        }.start();
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }
}