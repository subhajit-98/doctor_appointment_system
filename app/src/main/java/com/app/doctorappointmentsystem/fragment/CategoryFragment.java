package com.app.doctorappointmentsystem.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.activity.HomeActivity;
import com.app.doctorappointmentsystem.adapter.DepartmentViewAdapter;
import com.app.doctorappointmentsystem.adapter.DoctorViewAdapter;
import com.app.doctorappointmentsystem.helper.Constant;
import com.app.doctorappointmentsystem.getter_setter.DoctorDepartment;
import com.app.doctorappointmentsystem.getter_setter.DoctorList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryFragment extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList<DoctorDepartment> dept_list = new ArrayList<DoctorDepartment>();
    ArrayList<DoctorList> doctor_list = new ArrayList<DoctorList>();
    GridView dept_grid_view;
    ListView doctor_list_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_category, container, false);
        View v = inflater.inflate(R.layout.fragment_category, container, false);
        dept_grid_view = v.findViewById(R.id.dept_grid_view);
        getAllDepartment();
        dept_grid_view.setOnItemClickListener(this);
        doctor_list_view = v.findViewById(R.id.doctor_list_view);
        showDoctorList();
        doctor_list_view.setOnItemClickListener(this);
        return v;
    }

    private void getAllDepartment(){
        JsonObjectRequest get_dept = new JsonObjectRequest(Request.Method.GET, Constant.department_list, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.has("DepartmentDetails") && response.getString("Sucess").equalsIgnoreCase("1")){
                        JSONArray dept_list_array = response.getJSONArray("DepartmentDetails");
                        for(int i = 0; i < dept_list_array.length(); i++){
                            JSONObject get_dept_list_object = dept_list_array.getJSONObject(i);
                            DoctorDepartment dept_obj = new DoctorDepartment();
                            dept_obj.setDept_id(get_dept_list_object.getString("departmentid"));
                            dept_obj.setDept_name(get_dept_list_object.getString("departmentname"));
                            dept_list.add(dept_obj);
                        }
                    }
                    if(dept_list.size() > 0){
                        DepartmentViewAdapter adapter = new DepartmentViewAdapter(getActivity(), dept_list);
                        dept_grid_view.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(getContext()).add(get_dept);
    }

    private void showDoctorList(){
        StringRequest get_doctor = new StringRequest(Request.Method.POST, Constant.doctor_list, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.has("Sucess") && obj.get("Sucess").equals("1") && obj.has("DoctorSchedule")){
                        JSONArray doctorSchedule = obj.getJSONArray("DoctorSchedule");
                        for (int i=0; i<doctorSchedule.length(); i++){
                            JSONObject getDoctor = doctorSchedule.getJSONObject(i).getJSONObject("doctorde");
                            DoctorList doctor_list_obj = new DoctorList();
                            doctor_list_obj.setDoctor_id(getDoctor.getString("doctorid"));
                            doctor_list_obj.setDoctor_name(getDoctor.getString("doctorname"));
                            doctor_list_obj.setDept_id(getDoctor.getString("departmentid"));
                            doctor_list.add(doctor_list_obj);
                        }
                    }
                    if(doctor_list.size()>0){
                        DoctorViewAdapter adapter = new DoctorViewAdapter(getActivity(), doctor_list);
                        doctor_list_view.setAdapter(adapter);
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
                hMap.put("departmentid", "2");
                hMap.put("bookingdate", "2021/05/01");
                return hMap;
            }
        };
        Volley.newRequestQueue(getContext()).add(get_doctor);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        // Toast.makeText(getActivity(), String.valueOf(dept_list.get(i).getDept_id()), Toast.LENGTH_SHORT).show();
        if(adapterView.getId() == R.id.dept_grid_view) {
            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.putExtra("department_name", dept_list.get(i).getDept_name());
            intent.putExtra("department_id", dept_list.get(i).getDept_id());
            intent.putExtra("change_fragment", "choose_date");
            startActivity(intent);
        }
        if(adapterView.getId() == R.id.doctor_list_view){
            DashBoardDoctorDetailsBottomFragment obj = new DashBoardDoctorDetailsBottomFragment(doctor_list.get(i).getDoctor_name());
            obj.show(getActivity().getSupportFragmentManager(), obj.getTag());
        }
    }
}