package com.app.doctorappointmentsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.fragment.ProfileFragment;
import com.app.doctorappointmentsystem.fragment.SignInFragment;
import com.app.doctorappointmentsystem.fragment.SignUpFragment;

public class UserActivity extends BaseActivity {

    FrameLayout show_layout;
    TextView app_bar_title;
    FragmentManager fm = getSupportFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();

        show_layout = findViewById(R.id.show_layout);
        app_bar_title = findViewById(R.id.app_bar_title);

        checkInternet(show_layout);

        Intent intent = getIntent();

        if(intent.getStringExtra("show_fragment") != null && intent.getStringExtra("show_fragment").equals("sign_up")){
            app_bar_title.setText("Sign Up");
            ft.replace(R.id.show_layout, new SignUpFragment());
        }
        else if(intent.getStringExtra("show_fragment") != null && intent.getStringExtra("show_fragment").equals("profile")){
            app_bar_title.setText("Profile");
            ft.replace(R.id.show_layout, new ProfileFragment());
        }
        else{
            app_bar_title.setText("Sign In");
            ft.replace(R.id.show_layout, new SignInFragment());
        }
        ft.commit();
    }
}
