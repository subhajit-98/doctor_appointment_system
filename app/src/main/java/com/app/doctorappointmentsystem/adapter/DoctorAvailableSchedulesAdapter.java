package com.app.doctorappointmentsystem.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.getter_setter.DoctorAvailableSchedule;

import java.util.ArrayList;

public class DoctorAvailableSchedulesAdapter extends BaseAdapter {
    ArrayList<DoctorAvailableSchedule> availableSchedulesList;
    Context c;

    public DoctorAvailableSchedulesAdapter(ArrayList<DoctorAvailableSchedule> availableSchedulesList, Context c) {
        this.availableSchedulesList = availableSchedulesList;
        this.c = c;
    }

    @Override
    public int getCount() {
        return availableSchedulesList.size();
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
        LayoutInflater inf = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inf.inflate(R.layout.layout_show_doctor_schedule_time, null);
        TextView tv_show_time = view.findViewById(R.id.tv_show_time);
        tv_show_time.setText(availableSchedulesList.get(i).getSlotfrom()+" - "+availableSchedulesList.get(i).getSlotto());
        if(!availableSchedulesList.get(i).getStatus().equalsIgnoreCase("y")){
            tv_show_time.setBackgroundResource(R.drawable.show_timing_background_inactive);
        }
        return view;
    }
}
