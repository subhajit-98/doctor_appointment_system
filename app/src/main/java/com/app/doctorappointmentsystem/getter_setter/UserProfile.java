package com.app.doctorappointmentsystem.getter_setter;

public class UserProfile {
    String user_id;
    String user_first_name;
    String user_last_name;
    String user_gender;
    String user_phone_no;

    public UserProfile(String user_id, String user_first_name, String user_last_name, String user_gender, String user_phone_no) {
        this.user_id = user_id;
        this.user_first_name = user_first_name;
        this.user_last_name = user_last_name;
        this.user_gender = user_gender;
        this.user_phone_no = user_phone_no;
    }

    public UserProfile() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public String getUser_last_name() {
        return user_last_name;
    }

    public void setUser_last_name(String user_last_name) {
        this.user_last_name = user_last_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_phone_no() {
        return user_phone_no;
    }

    public void setUser_phone_no(String user_phone_no) {
        this.user_phone_no = user_phone_no;
    }
}
