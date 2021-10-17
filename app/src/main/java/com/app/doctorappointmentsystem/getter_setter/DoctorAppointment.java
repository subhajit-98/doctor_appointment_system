package com.app.doctorappointmentsystem.getter_setter;

public class DoctorAppointment {
    String doctorName;
    String doctorId;
    String scheduleId;
    String startTime;
    String endTime;

    public DoctorAppointment(String doctorName, String doctorId, String scheduleId, String startTime, String endTime) {
        this.doctorName = doctorName;
        this.doctorId = doctorId;
        this.scheduleId = scheduleId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DoctorAppointment() {
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
