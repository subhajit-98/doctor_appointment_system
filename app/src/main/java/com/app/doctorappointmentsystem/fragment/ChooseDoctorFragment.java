package com.app.doctorappointmentsystem.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.adapter.ChooseDoctorViewAdapter;
import com.app.doctorappointmentsystem.getter_setter.DoctorAppointment;
import com.app.doctorappointmentsystem.helper.Constant;
import com.app.doctorappointmentsystem.utils.DateFormatting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseDoctorFragment extends Fragment {

    ArrayList<DoctorAppointment> doctor_array_list;
    RecyclerView recycle_show_doctor;
    String dept_id, date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_choose_doctor, container, false);
        TextView show_department = v.findViewById(R.id.show_department);
        TextView show_appointment_date = v.findViewById(R.id.show_appointment_date);
        recycle_show_doctor = v.findViewById(R.id.recycle_show_doctor);
        dept_id = getArguments().getString("department_id");
        show_department.setText(getArguments().getString("department_name"));
        date = getArguments().getString("appointment_date");
        show_appointment_date.setText(date);
        getDoctorList(dept_id, DateFormatting.changeDate(date));
        return v;
    }

    private void getDoctorList(String dept_id, String booking_date){
        StringRequest doctor_list = new StringRequest(Request.Method.POST, Constant.doctor_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    doctor_array_list = new ArrayList<DoctorAppointment>();
                    JSONObject doctor_list_object = new JSONObject(response);
                    if(doctor_list_object.has("Sucess") && doctor_list_object.getString("Sucess").equalsIgnoreCase("1")){
                        if(doctor_list_object.has("DoctorSchedule")){
                            JSONArray schedule_array = doctor_list_object.getJSONArray("DoctorSchedule");
                            for(int i=0; i<schedule_array.length(); i++){
                                JSONObject getOneDoctorObject = schedule_array.getJSONObject(i);
                                JSONObject getDoctor = getOneDoctorObject.getJSONObject("doctorde");
                                JSONObject getDoctorScheduleObject = getOneDoctorObject.getJSONObject("schdet");

                                // Create getter setter object
                                DoctorAppointment doctor_object = new DoctorAppointment();
                                doctor_object.setDoctorId(getDoctor.getString("doctorid"));
                                doctor_object.setDoctorName(getDoctor.getString("doctorname"));
                                doctor_object.setScheduleId(getDoctorScheduleObject.getString("scheduleid"));
                                doctor_object.setStartTime(getDoctorScheduleObject.getString("starttime"));
                                doctor_object.setEndTime(getDoctorScheduleObject.getString("endtime"));
                                doctor_array_list.add(doctor_object);
                            }
                        }
                        if(doctor_array_list.size()>0){
                            ChooseDoctorViewAdapter doctorSchedule = new ChooseDoctorViewAdapter(doctor_array_list, getActivity(), date, getArguments().getString("department_name"));
                            recycle_show_doctor.setLayoutManager(new LinearLayoutManager(getContext()));
                            recycle_show_doctor.setAdapter(doctorSchedule);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hMap = new HashMap<>();
                hMap.put("departmentid", dept_id);
                hMap.put("bookingdate", booking_date);
                return hMap;
            }
        };
        Volley.newRequestQueue(getContext()).add(doctor_list);
    }
}