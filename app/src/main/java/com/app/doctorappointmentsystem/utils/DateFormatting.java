package com.app.doctorappointmentsystem.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatting {
    public static String changeDate(String givenDate){
        String finalString = null;
        try {
            String start_dt = "2011-01-01";
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // yyyy-MM-DD
            Date date = null;
            date = (Date)formatter.parse(givenDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy/MM/dd"); // MM-dd-yyyy
            finalString = newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return finalString;
    }
}
