package com.app.doctorappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.getter_setter.BookingData;

import java.util.ArrayList;

public class ShowNotificationAdapter extends BaseAdapter {
    ArrayList<BookingData> notificationList = new ArrayList<>();
    Context c;

    public ShowNotificationAdapter(ArrayList<BookingData> notificationList, Context c) {
        this.notificationList = notificationList;
        this.c = c;
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater lf = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
        View v = lf.inflate(R.layout.layout_show_notification, null);
        TextView doctor_name = v.findViewById(R.id.doctor_name);
        TextView show_date_time = v.findViewById(R.id.show_date_time);
        TextView booking_id = v.findViewById(R.id.booking_id);
        doctor_name.setText(notificationList.get(i).getDoctorName());
        show_date_time.setText(notificationList.get(i).getBookingDate()+" ("+notificationList.get(i).getBookingTimeFrom()+" - "+notificationList.get(i).getBookingTimeTo()+")");
        booking_id.setText(notificationList.get(i).getBookingId());
        return v;
    }
}
