package com.app.doctorappointmentsystem.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.fragment.IntroFirstFragment;
import com.app.doctorappointmentsystem.fragment.IntroSrcondFragment;
import com.app.doctorappointmentsystem.fragment.IntroThirdFragment;

public class IntroActivity extends AppCompatActivity {

    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getSupportActionBar().hide();
        this.getWindow().setStatusBarColor(this.getResources().getColor(R.color.secondary));

        Intent intent = getIntent();

        if(intent.getStringExtra("show_fragment") == null){
            ft.replace(R.id.intro_layout, new IntroFirstFragment());
        }
        else if(intent.getStringExtra("show_fragment").equals("second")){
            ft.replace(R.id.intro_layout, new IntroSrcondFragment());
        }
        else if(intent.getStringExtra("show_fragment").equals("third")){
            ft.replace(R.id.intro_layout, new IntroThirdFragment());
        }
        ft.commit();
    }
}