package com.app.doctorappointmentsystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.app.doctorappointmentsystem.activity.HomeActivity;
import com.app.doctorappointmentsystem.activity.UserActivity;
import com.app.doctorappointmentsystem.helper.Constant;
import com.app.doctorappointmentsystem.getter_setter.UserProfile;
import com.app.doctorappointmentsystem.utils.CustomAlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInFragment extends Fragment {

    TextInputLayout layout_user_name, layout_pwd;
    TextInputEditText et_user_name, et_pwd;
    ImageView fb_login;
    CheckBox check_remember;
    Button btn_sign_up;
    ProgressBar sign_in_loader;
    String user_name, pwd;
    String get_user_id, get_user_name, get_user_email, get_pwd;

    FirebaseDatabase fd = FirebaseDatabase.getInstance();
    DatabaseReference dr = fd.getReference(Constant.firebase_user_profile_db_constant);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layout_user_name = getActivity().findViewById(R.id.layout_user_name);
        layout_pwd = getActivity().findViewById(R.id.layout_pwd);
        et_user_name = getActivity().findViewById(R.id.et_user_name);
        et_pwd = getActivity().findViewById(R.id.et_pwd);
        check_remember = getActivity().findViewById(R.id.check_remember);
        sign_in_loader = getActivity().findViewById(R.id.sign_in_loader);
        btn_sign_up = getActivity().findViewById(R.id.btn_sign_up);

        fb_login = getActivity().findViewById(R.id.fb_login);
        fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomAlertDialog obj = new CustomAlertDialog("Error!", "Login with facebook is under develop.", false, getActivity());
                obj.showSimpleAlert();
            }
        });

        // Error reset
        layout_user_name.setError(null);
        layout_pwd.setError(null);

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_sign_up.setVisibility(View.GONE);
                sign_in_loader.setVisibility(View.VISIBLE);
                Boolean isValidate = true;
                user_name = et_user_name.getText().toString();
                pwd = et_pwd.getText().toString();
                if(user_name.equals("")){
                    isValidate = false;
                    layout_user_name.setError("User name required");
                }
                if(pwd.equals("")){
                    isValidate = false;
                    layout_pwd.setError("Password required");
                }
                if(check_remember.isChecked()){
                    Log.i("remember", "Checked");
                }

                if(isValidate){
                    signInUser(user_name, pwd);
                }
                else{
                    btn_sign_up.setVisibility(View.VISIBLE);
                    sign_in_loader.setVisibility(View.GONE);
                }
            }
        });

        // Redirect to signup page
        TextView sign_up = getActivity().findViewById(R.id.sign_up);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra("show_fragment", "sign_up");
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    private void signInUser(String user_name, String password){
        StringRequest sign_in_request = new StringRequest(Request.Method.POST, Constant.sign_in_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject user_details = new JSONObject(response);
                    if(user_details.has("UserDetails") && !user_details.getString("UserDetails").equalsIgnoreCase("null")){
                        JSONObject get_user_details = user_details.getJSONObject("UserDetails");
                        get_user_id = get_user_details.getString("userid");
                        get_user_name = get_user_details.getString("username");
                        get_user_email = get_user_details.getString("email");
                        get_pwd = get_user_details.getString("pwd");

                        // Save Log In
                        if(check_remember.isChecked()){
                            SharedPreferences logIn_sp = getActivity().getSharedPreferences("log_in_save", Context.MODE_PRIVATE);
                            SharedPreferences.Editor logIn_sp_edit = logIn_sp.edit();
                            logIn_sp_edit.putString("save_login_user_id", get_user_id);
                            logIn_sp_edit.putString("save_login_user_name", get_user_name);
                            logIn_sp_edit.putString("save_login_user_email", get_user_email);
                            logIn_sp_edit.commit();
                        }

                        // Logged In
                        SharedPreferences sp = getActivity().getSharedPreferences("logged_in", Context.MODE_PRIVATE);
                        SharedPreferences.Editor spe = sp.edit();
                        spe.putString("logged_in_user_id", get_user_id);
                        spe.putString("logged_in_user_name", get_user_name);
                        spe.putString("logged_in_user_email", get_user_email);


                        dr.child(get_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.getValue() != null){
                                    UserProfile prof = snapshot.getValue(UserProfile.class);
                                    Log.i("full_name", prof.getUser_first_name()+prof.getUser_last_name());
                                    spe.putString("logged_in_user_full_name", prof.getUser_first_name()+prof.getUser_last_name());
                                    startActivity(new Intent(getContext(), HomeActivity.class));
                                }
                                else{
                                    // Goto Profile page
                                    Intent intent = new Intent(getActivity(), UserActivity.class);
                                    intent.putExtra("show_fragment", "profile");
                                    startActivity(intent);
                                }
                                spe.commit();
                                getActivity().finish();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        Log.i("user_details", String.valueOf(get_user_details));
                    }
                    else{
                        layout_user_name.setError("User details not found");
                    }
                    btn_sign_up.setVisibility(View.VISIBLE);
                    sign_in_loader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
                btn_sign_up.setVisibility(View.VISIBLE);
                sign_in_loader.setVisibility(View.GONE);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hmap = new HashMap<>();
                hmap.put("username", user_name);
                hmap.put("pwd", password);
                return hmap;
                // return super.getParams();
            }
        };
        Volley.newRequestQueue(getContext()).add(sign_in_request);
    }
}