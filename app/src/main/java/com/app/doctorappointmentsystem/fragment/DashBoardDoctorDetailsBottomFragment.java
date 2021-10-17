package com.app.doctorappointmentsystem.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.doctorappointmentsystem.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DashBoardDoctorDetailsBottomFragment extends BottomSheetDialogFragment {

    private String getDoctorName;

    public DashBoardDoctorDetailsBottomFragment(String getDoctorName) {
        this.getDoctorName = getDoctorName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dash_board_doctor_details_bottom, container, false);
        TextView doctor_name = v.findViewById(R.id.doctor_name);
        doctor_name.setText(getDoctorName);
        return v;
    }
}