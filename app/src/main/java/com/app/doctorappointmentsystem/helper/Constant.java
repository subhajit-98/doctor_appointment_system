package com.app.doctorappointmentsystem.helper;

public class Constant {
    static String base_url = "http://14.99.214.220";
    static String api_url = base_url+"/drbookingapp/bookingapp.asmx";

    public static String sign_up_url = api_url+"/UserRegistration";
    public static String sign_in_url = api_url+"/UserLogin";

    public static String department_list = api_url+"/GetDepartment";
    public static String doctor_list = api_url+"/GetDoctor";
    public static String doctor_appointment_schedule = api_url+"/GetAppointmentSchedule";
    public static String doctor_booking = api_url+"/BookingDr";

    public static String firebase_user_profile_db_constant = "profile";
    public static String firebase_user_appointment_db_constant = "appointment";
}
