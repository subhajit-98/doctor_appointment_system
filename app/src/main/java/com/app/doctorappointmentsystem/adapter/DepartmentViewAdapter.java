package com.app.doctorappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.getter_setter.DoctorDepartment;

import java.util.ArrayList;

public class DepartmentViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<DoctorDepartment> dept_list;
    int[] dept_logo = {R.drawable.general_medicine, R.drawable.cardiology, R.drawable.orthopedic, R.drawable.gastrologi, R.drawable.neurology, R.drawable.skin, R.drawable.child_specialist, R.drawable.gynecologist};

    public DepartmentViewAdapter(Context c, ArrayList dept_list){
        context = c;
        this.dept_list = dept_list;
    }

    @Override
    public int getCount() {
        return this.dept_list.size();
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.show_department_gride, null);
        ImageView dept_image = view.findViewById(R.id.dept_image);
        dept_image.setImageResource(dept_logo[i]);
        TextView show = view.findViewById(R.id.show);
        show.setText(dept_list.get(i).getDept_name());
        return view;
    }
}
