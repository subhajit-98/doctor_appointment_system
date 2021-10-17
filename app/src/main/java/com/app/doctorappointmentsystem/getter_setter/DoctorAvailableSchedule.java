package com.app.doctorappointmentsystem.getter_setter;

public class DoctorAvailableSchedule {
    String timeslotid;
    String status;
    String slotfrom;
    String slotto;

    public String getTimeslotid() {
        return timeslotid;
    }

    public void setTimeslotid(String timeslotid) {
        this.timeslotid = timeslotid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlotfrom() {
        return slotfrom;
    }

    public void setSlotfrom(String slotfrom) {
        this.slotfrom = slotfrom;
    }

    public String getSlotto() {
        return slotto;
    }

    public void setSlotto(String slotto) {
        this.slotto = slotto;
    }
}
