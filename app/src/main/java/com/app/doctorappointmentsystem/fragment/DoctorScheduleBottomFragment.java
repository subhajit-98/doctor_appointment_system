package com.app.doctorappointmentsystem.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.activity.HomeActivity;
import com.app.doctorappointmentsystem.adapter.DoctorAvailableSchedulesAdapter;
import com.app.doctorappointmentsystem.getter_setter.DoctorAppointment;
import com.app.doctorappointmentsystem.getter_setter.DoctorAvailableSchedule;
import com.app.doctorappointmentsystem.helper.Constant;
import com.app.doctorappointmentsystem.utils.CustomAlertDialog;
import com.app.doctorappointmentsystem.utils.DateFormatting;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//public class DoctorScheduleBottomFragment extends Fragment {
public class DoctorScheduleBottomFragment extends BottomSheetDialogFragment implements AdapterView.OnItemClickListener {
    private ArrayList<DoctorAppointment> doctorSchedule = new ArrayList();
    private int position;
    private String bookingDate;
    private String deptName;
    GridView grid_view_show_appointment;
    ArrayList<DoctorAvailableSchedule> availableSchedulesList;


    public DoctorScheduleBottomFragment(ArrayList<DoctorAppointment> doctorSchedule, int position, String bookingDate, String deptName) {
        this.doctorSchedule = doctorSchedule;
        this.position = position;
        this.bookingDate = bookingDate;
        this.deptName = deptName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Toast.makeText(getActivity(), position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Log.i("position", String.valueOf(position));
        View v =inflater.inflate(R.layout.fragment_doctor_schedule_bottom, container, false);
        TextView t_v_doctor_name = v.findViewById(R.id.t_v_doctor_name);
        TextView t_v_appointment_date = v.findViewById(R.id.t_v_appointment_date);
        TextView dept_name = v.findViewById(R.id.dept_name);
        ImageView close_btn = v.findViewById(R.id.close_btn);
        t_v_doctor_name.setText(doctorSchedule.get(position).getDoctorName());
        t_v_appointment_date.setText(bookingDate);
        dept_name.setText(deptName);
        grid_view_show_appointment = v.findViewById(R.id.grid_view_show_appointment);
        grid_view_show_appointment.setOnItemClickListener(this);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorScheduleBottomFragment.super.dismiss();
            }
        });

        getAppointmentSchedule_data(doctorSchedule.get(position).getScheduleId(), bookingDate, doctorSchedule.get(position).getDoctorId());
        return v;
    }

    private void getAppointmentSchedule_data(String scheduleId, String bookingDate, String doctorId){
        StringRequest appoint_date = new StringRequest(Request.Method.POST, Constant.doctor_appointment_schedule, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject doctorSchedule = new JSONObject(response);
                    if(doctorSchedule.has("Sucess") && doctorSchedule.getString("Sucess").equalsIgnoreCase("1") && doctorSchedule.has("availableschedule")){
                        JSONArray doctorScheduleArray = doctorSchedule.getJSONArray("availableschedule");
                        availableSchedulesList = new ArrayList<DoctorAvailableSchedule>();
                        for(int i=0; i<doctorScheduleArray.length(); i++){
                            DoctorAvailableSchedule doctorAvailableSchedule = new DoctorAvailableSchedule();
                            JSONObject get_obj = doctorScheduleArray.getJSONObject(i);
                            doctorAvailableSchedule.setTimeslotid(get_obj.getString("timeslotid"));
                            doctorAvailableSchedule.setStatus(get_obj.getString("status"));
                            doctorAvailableSchedule.setSlotfrom(get_obj.getString("slotfrom"));
                            doctorAvailableSchedule.setSlotto(get_obj.getString("slotto"));
                            availableSchedulesList.add(doctorAvailableSchedule);
                        }
                    }
                    if(availableSchedulesList.size()>0){
                        DoctorAvailableSchedulesAdapter adapter = new DoctorAvailableSchedulesAdapter(availableSchedulesList, getContext());
                        grid_view_show_appointment.setAdapter(adapter);
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
                hMap.put("scheduleid", scheduleId);
                hMap.put("date", DateFormatting.changeDate(bookingDate));
                hMap.put("doctorid", doctorId);
                return hMap;
            }
        };
        Volley.newRequestQueue(getActivity()).add(appoint_date);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(availableSchedulesList.get(i).getStatus().equalsIgnoreCase("n")){
            // Show alert
            CustomAlertDialog obj = new CustomAlertDialog("Error Booking!", "This timing already booked. Please choose another timing.", false, getActivity());
            obj.showSimpleAlert();
        }
        else{
            // Toast.makeText(getActivity(), availableSchedulesList.get(i).getStatus(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("doctor_name", doctorSchedule.get(position).getDoctorName());
            intent.putExtra("doctor_id", doctorSchedule.get(position).getDoctorId());
            intent.putExtra("appointment_date", bookingDate);
            intent.putExtra("appointment_time_slot_id", availableSchedulesList.get(i).getTimeslotid());
            intent.putExtra("appointment_time_form", availableSchedulesList.get(i).getSlotfrom());
            intent.putExtra("appointment_time_to", availableSchedulesList.get(i).getSlotto());
            intent.putExtra("dept_name", deptName);
            intent.putExtra("change_fragment", "confirm_booking");
            startActivity(intent);
        }
    }
}