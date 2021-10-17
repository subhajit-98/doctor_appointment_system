package com.app.doctorappointmentsystem.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.activity.HomeActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class ChooseDateFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextInputLayout layout_select_date = getActivity().findViewById(R.id.layout_select_date);
        TextInputEditText et_show_date = getActivity().findViewById(R.id.et_show_date);
        ImageView calender_logo = getActivity().findViewById(R.id.calender_logo);
        Button btn_check_doctor = getActivity().findViewById(R.id.btn_check_doctor);
        calender_logo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar calender_obj = Calendar.getInstance();
                Calendar minDate = Calendar.getInstance();
                minDate.add(calender_obj.DATE, 1);
                Calendar maxDate = Calendar.getInstance();
                maxDate.add(calender_obj.DATE, 14);
                int d = calender_obj.get(Calendar.DAY_OF_MONTH);
                int m = calender_obj.get(Calendar.MONTH);
                int y = calender_obj.get(Calendar.YEAR);

                DatePickerDialog dpd = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et_show_date.setText(i2+"/"+(i1+1)+"/"+i);
                    }
                }, y, m, d);
                dpd.getDatePicker().setMinDate(minDate.getTimeInMillis());
                dpd.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                dpd.show();
                dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                dpd.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
            }
        });

        btn_check_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout_select_date.setError(null);
                String selected_date = et_show_date.getText().toString();
                if(selected_date.equalsIgnoreCase("")){
                    layout_select_date.setError("Please select date");
                }
                else{
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("department_name", getArguments().getString("department_name"));
                    intent.putExtra("department_id", getArguments().getString("department_id"));
                    intent.putExtra("appointment_date", selected_date);
                    intent.putExtra("change_fragment", "choose_doctor");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_select, container, false);
    }
}