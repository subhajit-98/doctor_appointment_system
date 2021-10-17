package com.app.doctorappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.getter_setter.DoctorList;

import java.util.ArrayList;

public class DoctorViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<DoctorList> doctor_list;

    public DoctorViewAdapter(Context c, ArrayList doctor_list) {
        context = c;
        this.doctor_list = doctor_list;
    }

    @Override
    public int getCount() {
        return doctor_list.size();
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
        LayoutInflater inf = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inf.inflate(R.layout.layout_show_doctor_list_home, null);
        TextView doctor_name = view.findViewById(R.id.doctor_name);
        doctor_name.setText(doctor_list.get(i).getDoctor_name());
        return view;
    }
}
