package com.app.doctorappointmentsystem.getter_setter;

public class BookingData {
    String bookingId, doctorName, bookingDate, bookingTimeFrom, bookingTimeTo, bookingDept, userId, seen;

    public BookingData(String bookingId, String doctorName, String bookingDate, String bookingTimeFrom, String bookingTimeTo, String bookingDept, String
                       userId, String seen) {
        this.bookingId = bookingId;
        this.doctorName = doctorName;
        this.bookingDate = bookingDate;
        this.bookingTimeFrom = bookingTimeFrom;
        this.bookingTimeTo = bookingTimeTo;
        this.bookingDept = bookingDept;
        this.userId = userId;
        this.seen = seen;
    }

    public BookingData() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingTimeFrom() {
        return bookingTimeFrom;
    }

    public void setBookingTimeFrom(String bookingTimeFrom) {
        this.bookingTimeFrom = bookingTimeFrom;
    }

    public String getBookingTimeTo() {
        return bookingTimeTo;
    }

    public void setBookingTimeTo(String bookingTimeTo) {
        this.bookingTimeTo = bookingTimeTo;
    }

    public String getBookingDept() {
        return bookingDept;
    }

    public void setBookingDept(String bookingDept) {
        this.bookingDept = bookingDept;
    }
}
