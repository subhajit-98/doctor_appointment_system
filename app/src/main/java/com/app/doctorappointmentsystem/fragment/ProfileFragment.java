package com.app.doctorappointmentsystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.activity.HomeActivity;
import com.app.doctorappointmentsystem.helper.Constant;
import com.app.doctorappointmentsystem.getter_setter.UserProfile;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    TextInputLayout layout_user_f_name, layout_user_l_name, layout_phone_no;
    TextInputEditText et_user_f_name, et_user_l_name, et_phone_no;
    RadioGroup check_group_gender;
    RadioButton check_male, check_female, check_other;
    ImageView profile_blank_img;
    Button btn_sign_up;
    String first_name, last_name, gender, phone_no;

    FirebaseDatabase fd;
    DatabaseReference dr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        layout_user_f_name = getActivity().findViewById(R.id.layout_user_f_name);
        et_user_f_name = getActivity().findViewById(R.id.et_user_f_name);
        layout_user_l_name = getActivity().findViewById(R.id.layout_user_l_name);
        et_user_l_name = getActivity().findViewById(R.id.et_user_l_name);
        check_group_gender = getActivity().findViewById(R.id.check_group_gender);
        check_male = getActivity().findViewById(R.id.check_male);
        check_female = getActivity().findViewById(R.id.check_female);
        check_other = getActivity().findViewById(R.id.check_other);
        layout_phone_no = getActivity().findViewById(R.id.layout_phone_no);
        et_phone_no = getActivity().findViewById(R.id.et_phone_no);
        profile_blank_img = getActivity().findViewById(R.id.profile_blank_img);
        btn_sign_up = getActivity().findViewById(R.id.btn_sign_up);

        check_group_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(check_male.isChecked()){
                    profile_blank_img.setImageDrawable(getResources().getDrawable(R.drawable.male_default));
                    gender = "male";
                }
                if(check_female.isChecked()){
                    profile_blank_img.setImageDrawable(getResources().getDrawable(R.drawable.female_default));
                    gender = "female";
                }
                if(check_other.isChecked()){
                    profile_blank_img.setImageDrawable(getResources().getDrawable(R.drawable.other_default));
                    gender = "other";
                }
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isValidate = true;
                // Set Errors null
                layout_user_f_name.setError(null);
                layout_user_l_name.setError(null);
                layout_phone_no.setError(null);

                first_name = et_user_f_name.getText().toString();
                last_name = et_user_l_name.getText().toString();
                phone_no = et_phone_no.getText().toString();

                if(first_name.equals("")){
                    layout_user_f_name.setError("First name required");
                    isValidate = false;
                }
                if(last_name.equals("")){
                    layout_user_l_name.setError("Last name required");
                    isValidate = false;
                }
                if(phone_no.equals("")){
                    layout_phone_no.setError("Phone number required");
                    isValidate = false;
                }
                if (isValidate) {
                    SharedPreferences sp = getActivity().getSharedPreferences("logged_in", Context.MODE_PRIVATE);
                    String loggedIn_user_id = sp.getString("logged_in_user_id", "");
                    createProfileFirebase(loggedIn_user_id, first_name, last_name, gender, phone_no);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sp = getActivity().getSharedPreferences("logged_in", Context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        TextInputEditText et_user_name = v.findViewById(R.id.et_user_name);
        et_user_name.setText(sp.getString("logged_in_user_name", ""));
        return v;
        // return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void createProfileFirebase(String user_id, String first_name, String last_name, String gender, String phone_no){
        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference(Constant.firebase_user_profile_db_constant);
        dr.child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue() != null){
                    Toast.makeText(getActivity(), "Profile already created", Toast.LENGTH_SHORT).show();
                }
                else{
                    dr.child(user_id).setValue(new UserProfile(user_id, first_name, last_name, gender, phone_no));
                    Toast.makeText(getActivity(), "Profile created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}