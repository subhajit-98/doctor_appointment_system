package com.app.doctorappointmentsystem.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.activity.IntroActivity;
import com.app.doctorappointmentsystem.activity.UserActivity;

public class IntroThirdFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button start = getActivity().findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sf = getActivity().getSharedPreferences("intro_screen", Context.MODE_PRIVATE);
                SharedPreferences.Editor sfe = sf.edit();
                sfe.putBoolean("introScreen", false);
                sfe.commit();
                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra("show_fragment", "sign_up");
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro_third, container, false);
    }
}