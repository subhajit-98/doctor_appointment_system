package com.app.doctorappointmentsystem.utils;

public class EmailValidation {
    public static boolean isValidate(String email_id){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email_id.matches(emailPattern) && email_id.length() > 0){
            return true;
        }
        return false;
    }
}
