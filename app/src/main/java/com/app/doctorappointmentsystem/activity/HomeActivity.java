package com.app.doctorappointmentsystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.fragment.CategoryFragment;
import com.app.doctorappointmentsystem.fragment.ChooseDateFragment;
import com.app.doctorappointmentsystem.fragment.ChooseDoctorFragment;
import com.app.doctorappointmentsystem.fragment.ConfirmAppointmentFragment;
import com.app.doctorappointmentsystem.fragment.HistoryFragment;
import com.app.doctorappointmentsystem.fragment.LogoutFragment;
import com.app.doctorappointmentsystem.fragment.ShowNotificationFragment;
import com.app.doctorappointmentsystem.getter_setter.BookingData;
import com.app.doctorappointmentsystem.helper.Constant;
import com.app.doctorappointmentsystem.utils.CustomNotification;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout home_show_layout;
    Toolbar tool_bar;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer_layout;
    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawer_layout = findViewById(R.id.drawer_layout);
        tool_bar = findViewById(R.id.tool_bar);
        tool_bar.setTitleTextColor(getResources().getColor(R.color.white));
        tool_bar.setTitle("Dashboard");
        setSupportActionBar(tool_bar);
        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawer_layout, tool_bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        checkInternet(drawer_layout);

        SharedPreferences sp = getSharedPreferences("logged_in", Context.MODE_PRIVATE);
        String user_logged_in_id = sp.getString("logged_in_user_id", "");
        String logged_in_user_full_name = sp.getString("logged_in_user_full_name", "");
        String user_name = sp.getString("logged_in_user_name", "");

        NavigationView nav_view = findViewById(R.id.navigation_view);
        nav_view.setNavigationItemSelectedListener(this);
        View headerView = nav_view.getHeaderView(0);
        TextView user_full_name = headerView.findViewById(R.id.user_full_name);
        user_full_name.setText("Welcome "+logged_in_user_full_name);
        TextView tv_user_name = headerView.findViewById(R.id.user_name);
        tv_user_name.setText(user_name);

        /*Show Fragments Content*/
        Intent intent = getIntent();
        if(intent.hasExtra("change_fragment") && intent.getExtras().getString("change_fragment", "").equalsIgnoreCase("choose_date")){
            // Show choose date fragment
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.left_drawer_icon);
            // drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            /*toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // getSupportFragmentManager().popBackStack();
                    onBackPressed();
                }
            });*/

            tool_bar.setTitle(intent.getExtras().getString("department_name", ""));

            // Choose Date Fragment
            Bundle bundle = new Bundle();
            bundle.putString("department_name", intent.getExtras().getString("department_name", ""));
            bundle.putString("department_id", intent.getExtras().getString("department_id", ""));
            home_show_layout = findViewById(R.id.home_show_layout);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            ChooseDateFragment chooseDateFragment = new ChooseDateFragment();
            chooseDateFragment.setArguments(bundle);
            fmt.replace(R.id.home_show_layout, chooseDateFragment);
            fmt.addToBackStack(null);
            fmt.commit();
        }
        else if(intent.hasExtra("change_fragment") && intent.getExtras().getString("change_fragment", "").equalsIgnoreCase("choose_doctor")){
            tool_bar.setTitle("Doctor");
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.left_drawer_icon);
            home_show_layout = findViewById(R.id.home_show_layout);

            Bundle bundle = new Bundle();
            bundle.putString("department_id", intent.getExtras().getString("department_id", ""));
            bundle.putString("department_name", intent.getExtras().getString("department_name", ""));
            bundle.putString("appointment_date", intent.getExtras().getString("appointment_date", ""));

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            ChooseDoctorFragment chooseDateFragment = new ChooseDoctorFragment();
            chooseDateFragment.setArguments(bundle);
            fmt.replace(R.id.home_show_layout, chooseDateFragment);
            fmt.addToBackStack(null);
            fmt.commit();
        }
        else if(intent.hasExtra("change_fragment") && intent.getExtras().getString("change_fragment", "").equalsIgnoreCase("confirm_booking")){
            tool_bar.setTitle("Confirm appointment");
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.left_drawer_icon);
            home_show_layout = findViewById(R.id.home_show_layout);

            Bundle bundle = new Bundle();
            bundle.putString("user_id", user_logged_in_id);
            bundle.putString("doctor_id", intent.getStringExtra("doctor_id"));
            bundle.putString("appointment_date", intent.getStringExtra("appointment_date"));
            bundle.putString("appointment_time_slot_id", intent.getStringExtra("appointment_time_slot_id"));
            bundle.putString("doctor_name", intent.getStringExtra("doctor_name"));
            bundle.putString("dept_name", intent.getStringExtra("dept_name"));
            bundle.putString("appointment_time_form", intent.getStringExtra("appointment_time_form"));
            bundle.putString("appointment_time_to", intent.getStringExtra("appointment_time_to"));

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            ConfirmAppointmentFragment confirmAppointmentFragment = new ConfirmAppointmentFragment();
            confirmAppointmentFragment.setArguments(bundle);
            fmt.replace(R.id.home_show_layout, confirmAppointmentFragment);
            fmt.addToBackStack(null);
            fmt.commit();
        }
        else if(intent.hasExtra("change_fragment") && intent.getExtras().getString("change_fragment", "").equalsIgnoreCase("history")){
            tool_bar.setTitle("History");
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.left_drawer_icon);
            home_show_layout = findViewById(R.id.home_show_layout);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            HistoryFragment chooseDateFragment = new HistoryFragment();
            fmt.replace(R.id.home_show_layout, chooseDateFragment);
            fmt.addToBackStack(null);
            fmt.commit();
        }
        else if(intent.hasExtra("change_fragment") && intent.getExtras().getString("change_fragment", "").equalsIgnoreCase("notification")){
            tool_bar.setTitle("Notification");
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.left_drawer_icon);

            /*CustomNotification.showNotification(HomeActivity.this);*/
            Bundle bundle = new Bundle();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            ShowNotificationFragment showNotificationFragment = new ShowNotificationFragment();
            showNotificationFragment.setArguments(bundle);
            fmt.replace(R.id.home_show_layout, showNotificationFragment);
            fmt.addToBackStack(null);
            fmt.commit();
        }
        else{
            // Show dashboard
            home_show_layout = findViewById(R.id.home_show_layout);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            fmt.replace(R.id.home_show_layout, new CategoryFragment());
            // fmt.addToBackStack(null);
            fmt.commit();
        }

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getSupportFragmentManager().popBackStack();
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.right_side_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.notification);
        View notificationView = menuItem.getActionView();
        TextView textCartItemCount = (TextView) notificationView.findViewById(R.id.cart_number);

        // Count notification
        ArrayList<BookingData> show_notificationList = new ArrayList();
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference(Constant.firebase_user_appointment_db_constant);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPreferences sp = getSharedPreferences("logged_in", Context.MODE_PRIVATE);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    BookingData getNotifyData = ds.getValue(BookingData.class);
                    if(getNotifyData.getUserId().equalsIgnoreCase(sp.getString("logged_in_user_id", ""))){
                        show_notificationList.add(getNotifyData);
                    }
                }
                if(show_notificationList.size()>0){
                    int count = 0;
                    for (int i=0; i<show_notificationList.size(); i++){
                        if(show_notificationList.get(i).getUserId().equalsIgnoreCase(sp.getString("logged_in_user_id", ""))){
                            if(show_notificationList.get(i).getSeen().equalsIgnoreCase("0")){
                                count = count+1;
                            }
                        }
                    }
                    textCartItemCount.setText(String.valueOf(count));
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
                else{
                    // textCartItemCount.setText("0");
                    textCartItemCount.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        notificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(menuItem);
            }
        });

        // return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Toast.makeText(HomeActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer_layout.closeDrawer(GravityCompat.START,true);
        if(item.getItemId() == R.id.home){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            fmt.replace(R.id.home_show_layout, new CategoryFragment());
            fmt.commit();
            return true;
        }
        else if(item.getItemId() == R.id.profile){
            /*CustomNotification.showNotification(HomeActivity.this);*/
            return true;
        }
        else if(item.getItemId() == R.id.history){
            startActivity(new Intent(HomeActivity.this, HomeActivity.class).putExtra("change_fragment", "history"));
            /*tool_bar.setTitle("History");
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.left_drawer_icon);
            home_show_layout = findViewById(R.id.home_show_layout);

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            HistoryFragment chooseDateFragment = new HistoryFragment();
            fmt.replace(R.id.home_show_layout, chooseDateFragment);
            fmt.commit();*/
            return true;
        }
        else if(item.getItemId() == R.id.logout){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            fmt.replace(R.id.home_show_layout, new LogoutFragment());
            fmt.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.notification){
            startActivity(new Intent(HomeActivity.this, HomeActivity.class).putExtra("change_fragment", "notification"));
            /*tool_bar.setTitle("Notification");
            toggle.setDrawerIndicatorEnabled(false);
            toggle.setHomeAsUpIndicator(R.drawable.left_drawer_icon);

            *//*CustomNotification.showNotification(HomeActivity.this);*//*
            Bundle bundle = new Bundle();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fmt = fm.beginTransaction();
            ShowNotificationFragment showNotificationFragment = new ShowNotificationFragment();
            showNotificationFragment.setArguments(bundle);
            fmt.replace(R.id.home_show_layout, showNotificationFragment);
            fmt.commit();*/
            return true;
        }
        // return super.onOptionsItemSelected(item);
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.i("fragment_count", String.valueOf(getSupportFragmentManager().getBackStackEntryCount()));
        // super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() != 0){
            finish();
        }
        else{
            if (pressedTime + 2000 > System.currentTimeMillis()) {
                super.onBackPressed();
                finish();
            } else {
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
            pressedTime = System.currentTimeMillis();
        }
        /*if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();*/
    }
}