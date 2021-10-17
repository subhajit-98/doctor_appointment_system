package com.app.doctorappointmentsystem.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.activity.UserActivity;
import com.app.doctorappointmentsystem.helper.Constant;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    TextInputLayout layout_user_name, layout_email_id, layout_password, layout_confirm_pwd;
    TextInputEditText et_user_name, et_email_id, et_password, et_confirm_pwd;
    Button btn_sign_up;
    ProgressBar sign_up_loader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layout_user_name = getActivity().findViewById(R.id.layout_user_name);
        layout_email_id = getActivity().findViewById(R.id.layout_email_id);
        layout_password = getActivity().findViewById(R.id.layout_password);
        layout_confirm_pwd = getActivity().findViewById(R.id.layout_confirm_pwd);
        et_user_name = getActivity().findViewById(R.id.et_user_name);
        et_email_id = getActivity().findViewById(R.id.et_email_id);
        et_password = getActivity().findViewById(R.id.et_password);
        et_confirm_pwd = getActivity().findViewById(R.id.et_confirm_pwd);
        sign_up_loader = getActivity().findViewById(R.id.sign_up_loader);
        btn_sign_up = getActivity().findViewById(R.id.btn_sign_up);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name, email_id, pwd, c_pwd;
                Boolean isValidate = true;
                user_name = et_user_name.getText().toString().toLowerCase();
                email_id = et_email_id.getText().toString().toLowerCase();
                pwd = et_password.getText().toString();
                c_pwd = et_confirm_pwd.getText().toString();
                // Log.i("signUp", String.valueOf(user_name+email_id+pwd+c_pwd));
                sign_up_loader.setVisibility(View.VISIBLE);
                btn_sign_up.setVisibility(View.GONE);
                if(user_name.equals("")){
                    isValidate = false;
                    layout_user_name.setError("User name required");
                }
                if(email_id.equals("")){
                    isValidate = false;
                    layout_email_id.setError("Email id required");
                }
                if(pwd.equals("")){
                    isValidate = false;
                    layout_password.setError("Password required");
                }
                if(c_pwd.equals("")){
                    isValidate = false;
                    layout_confirm_pwd.setError("Confirm password required");
                }
                if(!pwd.equals(c_pwd)){
                    isValidate = false;
                    layout_confirm_pwd.setError("Password mismatch");
                }
                if(isValidate){
                    newSignUp(user_name, email_id, pwd);
                }
                sign_up_loader.setVisibility(View.GONE);
                btn_sign_up.setVisibility(View.VISIBLE);
            }
        });

        TextView sign_in = getActivity().findViewById(R.id.sign_in);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra("show_fragment", "sign_in");
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    private void newSignUp(String username, String emailId, String password){
        sign_up_loader.setVisibility(View.VISIBLE);
        btn_sign_up.setVisibility(View.GONE);
        StringRequest sign_up_obj = new StringRequest(Request.Method.POST, Constant.sign_up_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    if(res.getString("Sucess").equalsIgnoreCase("1")){
                        SignInAlert();
                    }
                    else{
                        layout_user_name.setError("User name already taken");
                        sign_up_loader.setVisibility(View.GONE);
                        btn_sign_up.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("volley_error", String.valueOf(error));
                Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String, String> hMap = new HashMap<>();
                hMap.put("username", username);
                hMap.put("email", emailId);
                hMap.put("pwd", password);
                hMap.put("fname", "username");
                hMap.put("lname", "username");
                hMap.put("address", "username");
                hMap.put("phoneno", "username");
                return hMap;
                // return super.getParams();
            }
        };

        Volley.newRequestQueue(getActivity()).add(sign_up_obj);
    }

    public void SignInAlert(){
        AlertDialog.Builder alert_obj = new AlertDialog.Builder(getActivity());
        alert_obj.setTitle("Success");
        alert_obj.setMessage("Sign up successfully. Please sign in and continue");
        alert_obj.setCancelable(false);
        alert_obj.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alert_obj.setCancelable(true);
                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra("show_fragment", "sign_in");
                startActivity(intent);
                getActivity().finish();
            }
        });
        alert_obj.show();
    }
}