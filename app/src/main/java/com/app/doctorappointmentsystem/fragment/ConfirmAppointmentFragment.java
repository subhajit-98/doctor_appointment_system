package com.app.doctorappointmentsystem.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.activity.HomeActivity;
import com.app.doctorappointmentsystem.getter_setter.BookingData;
import com.app.doctorappointmentsystem.helper.Constant;
import com.app.doctorappointmentsystem.utils.CustomAlertDialog;
import com.app.doctorappointmentsystem.utils.CustomNotification;
import com.app.doctorappointmentsystem.utils.DateFormatting;
import com.app.doctorappointmentsystem.utils.LoadingAlertDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmAppointmentFragment extends Fragment {

    String doctor_name, appointment_date, deptName, appointment_time_form, appointment_time_to;
    String doctor_id, appointment_time_slot_id, user_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button confirm = getActivity().findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookAppointment(doctor_id, user_id, appointment_time_slot_id, appointment_date);
            }
        });
        Button cancel = getActivity().findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCancelAlertDialog();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        doctor_name = getArguments().getString("doctor_name");
        appointment_date = getArguments().getString("appointment_date");
        deptName = getArguments().getString("dept_name");
        appointment_time_form = getArguments().getString("appointment_time_form");
        appointment_time_to = getArguments().getString("appointment_time_to");

        // IDS
        doctor_id = getArguments().getString("doctor_id");
        appointment_time_slot_id = getArguments().getString("appointment_time_slot_id");
        user_id = getArguments().getString("user_id");

        View v = inflater.inflate(R.layout.fragment_confirm_appointment, container, false);
        TextView tv_doctor_name = v.findViewById(R.id.doctor_name);
        TextView show_date = v.findViewById(R.id.show_date);
        TextView show_timing = v.findViewById(R.id.show_timing);
        TextView tv_dept_name = v.findViewById(R.id.tv_dept_name);
        tv_doctor_name.setText(doctor_name);
        show_date.setText(appointment_date);
        tv_dept_name.setText(deptName);
        show_timing.setText(appointment_time_form+" - "+appointment_time_to);
        return v;
    }

    private void bookAppointment(String doctorId, String userId, String timeSlotId, String bookingDate){
        // Log.i("data", doctorId+"-"+userId+"-"+timeSlotId+"-"+bookingDate);
        LoadingAlertDialog obj = new LoadingAlertDialog(getActivity(), "Checking database");
        obj.showLoading();

        StringRequest get_booking = new StringRequest(Request.Method.POST, Constant.doctor_booking, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject getResp = new JSONObject(response);
                    Log.i("show_data", String.valueOf(getResp));
                    if(getResp.has("Sucess") && getResp.getString("Sucess").equalsIgnoreCase("1")){
                        String message = getResp.getString("Message");
                        String bookingId = message.replaceAll("[^0-9]", "");
                        // Log.i("bookingId", bookingId);
                        CustomNotification.showNotification(getActivity(), "Doctor Appointment", doctor_name+" ready for your care.");
                        StoreBookingDataFireBase(bookingId, doctor_name, appointment_date, appointment_time_form, appointment_time_to, deptName, user_id);
                    }
                    else{
                        CustomAlertDialog error_alert_obj = new CustomAlertDialog("Booking error!", getResp.getString("Message")+". Please contact with us", false, getActivity());
                        error_alert_obj.showSimpleAlert();
                    }
                    obj.hideLoading();
                } catch (JSONException e) {
                    e.printStackTrace();
                    obj.hideLoading();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                obj.hideLoading();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hMap = new HashMap<>();
                hMap.put("doctorid", doctorId);
                hMap.put("userid", userId);
                hMap.put("timeslotid", timeSlotId);
                hMap.put("bookingdate", DateFormatting.changeDate(bookingDate));
                return hMap;
            }
        };
        Volley.newRequestQueue(getActivity()).add(get_booking);
    }

    private void StoreBookingDataFireBase(String bookingId, String doctorName, String bookingDate, String bookingTimeFrom, String bookingTimeTo, String bookingDept, String user_id){
        LoadingAlertDialog obj = new LoadingAlertDialog(getActivity(), "Checking database");
        obj.showLoading();
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference(Constant.firebase_user_appointment_db_constant);
        dr.child(bookingId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() == null){
                    dr.child(bookingId).setValue(new BookingData(bookingId, doctorName, bookingDate, bookingTimeFrom, bookingTimeTo, bookingDept, user_id, "0"));
                    obj.hideLoading();
                    bookingSuccessAlert(bookingId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                obj.hideLoading();
            }
        });
    }

    private void showCancelAlertDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("Confirm cancel");
        alertBuilder.setMessage("Are you sure to cancel this schedule?");
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertBuilder.setCancelable(true);
                getActivity().onBackPressed();
            }
        });
        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertBuilder.setCancelable(true);
            }
        });
        alertBuilder.show();
    }

    private void bookingSuccessAlert(String bookingId){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
        alertBuilder.setTitle("Booking success");
        alertBuilder.setMessage("Booking complected. Your booking no is "+bookingId+". Thank you for choosing us.");
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertBuilder.setCancelable(true);
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        alertBuilder.show();
    }
}