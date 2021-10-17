package com.app.doctorappointmentsystem.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.adapter.ShowNotificationAdapter;
import com.app.doctorappointmentsystem.getter_setter.BookingData;
import com.app.doctorappointmentsystem.helper.Constant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryFragment extends Fragment {

    ArrayList<BookingData> notification = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        ConstraintLayout no_notification = v.findViewById(R.id.no_notification);
        LinearLayout show_notification = v.findViewById(R.id.show_notification);
        ListView notification_list = v.findViewById(R.id.notification_list);
        no_notification.setVisibility(View.GONE);
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference(Constant.firebase_user_appointment_db_constant);
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notification.clear();
                SharedPreferences sp = getActivity().getSharedPreferences("logged_in", Context.MODE_PRIVATE);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    BookingData getNotifyData = ds.getValue(BookingData.class);
                    if(getNotifyData.getUserId().equalsIgnoreCase(sp.getString("logged_in_user_id", ""))){
                        notification.add(getNotifyData);
                    }
                }
                if(notification.size()>0){
                    show_notification.setVisibility(View.VISIBLE);
                    Collections.reverse(notification);
                    ShowNotificationAdapter adapter = new ShowNotificationAdapter(notification, getActivity());
                    notification_list.setAdapter(adapter);
                }
                else{
                    no_notification.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }
}