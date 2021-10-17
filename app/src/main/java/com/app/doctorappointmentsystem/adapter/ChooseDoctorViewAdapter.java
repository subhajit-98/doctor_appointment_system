package com.app.doctorappointmentsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.doctorappointmentsystem.R;
import com.app.doctorappointmentsystem.fragment.DoctorScheduleBottomFragment;
import com.app.doctorappointmentsystem.getter_setter.DoctorAppointment;

import java.util.ArrayList;

public class ChooseDoctorViewAdapter extends RecyclerView.Adapter<ChooseDoctorViewAdapter.ViewHolder> {
    private ArrayList<DoctorAppointment> doctorSchedule = new ArrayList();
    Context c;
    private String bookingDate;
    private String deptName;

    public ChooseDoctorViewAdapter(ArrayList<DoctorAppointment> doctorSchedule, Context c, String bookingDate, String deptName) {
        this.doctorSchedule = doctorSchedule;
        this.c = c;
        this.bookingDate = bookingDate;
        this.deptName = deptName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.layout_show_doctor_schedule, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_doctor_name.setText(doctorSchedule.get(position).getDoctorName());
        holder.t_v_timing.setText(doctorSchedule.get(position).getStartTime()+" - "+doctorSchedule.get(position).getEndTime());
    }

    @Override
    public int getItemCount() {
        return doctorSchedule.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_doctor_name, t_v_timing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_doctor_name = itemView.findViewById(R.id.tv_doctor_name);
            t_v_timing = itemView.findViewById(R.id.t_v_timing);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Toast.makeText(view.getContext(), doctorSchedule.get(getAdapterPosition()).getDoctorId(), Toast.LENGTH_SHORT).show();
            DoctorScheduleBottomFragment obj = new DoctorScheduleBottomFragment(doctorSchedule, getAdapterPosition(), bookingDate, deptName);
            obj.show(((FragmentActivity) c).getSupportFragmentManager(), obj.getTag());
        }
    }
}
