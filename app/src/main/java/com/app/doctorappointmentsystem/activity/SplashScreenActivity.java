package com.app.doctorappointmentsystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.getter_setter.UserProfile;
import com.app.doctorappointmentsystem.helper.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        ImageView circleImageView3 = findViewById(R.id.circleImageView3);

        Animation img_animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.splash_screen_logo);
        circleImageView3.startAnimation(img_animation);

        Button button_get_started = findViewById(R.id.button_get_started);
        button_get_started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sf = getSharedPreferences("intro_screen", Context.MODE_PRIVATE);
                if(sf.getBoolean("introScreen", true)){
                    startActivity(new Intent(SplashScreenActivity.this, IntroActivity.class));
                }
                else{
                    startActivity(new Intent(SplashScreenActivity.this, UserActivity.class));
                }
                finish();
            }
        });

        // checkInternet(button_get_started);
        new CountDownTimer(1000, 4000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                // Check is user remember user id pwd
                SharedPreferences logIn_sp = getSharedPreferences("log_in_save", Context.MODE_PRIVATE);
                if(!logIn_sp.getString("save_login_user_id", "").equalsIgnoreCase("")){
                    // Logged In
                    FirebaseDatabase fd = FirebaseDatabase.getInstance();
                    DatabaseReference dr = fd.getReference(Constant.firebase_user_profile_db_constant);

                    SharedPreferences sp = getSharedPreferences("logged_in", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spe = sp.edit();
                    spe.putString("logged_in_user_id", logIn_sp.getString("save_login_user_id", ""));
                    spe.putString("logged_in_user_name", logIn_sp.getString("save_login_user_name", ""));
                    spe.putString("logged_in_user_email", logIn_sp.getString("save_login_user_email", ""));

                    dr.child(logIn_sp.getString("save_login_user_id", "")).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue() != null){
                                UserProfile prof = snapshot.getValue(UserProfile.class);
                                Log.i("full_name", prof.getUser_first_name()+prof.getUser_last_name());
                                spe.putString("logged_in_user_full_name", prof.getUser_first_name()+prof.getUser_last_name());
                                startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                            }
                            /*else{
                                // Goto Profile page
                                Intent intent = new Intent(SplashScreenActivity.this, UserActivity.class);
                                intent.putExtra("show_fragment", "profile");
                                startActivity(intent);
                            }*/
                            spe.commit();
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    button_get_started.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }
}