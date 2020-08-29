package com.hvtechnologies.playschool;

public class SeeAttendanceStudentWiseClass {

    public String BatchName;
    public String AbsentOrPresent;
    public String DateIssued;
    public String Time;

    public SeeAttendanceStudentWiseClass(String batchName, String absentOrPresent, String dateIssued, String time) {
        BatchName = batchName;
        AbsentOrPresent = absentOrPresent;
        DateIssued = dateIssued;
        Time = time;
    }

    public String getBatchName() {
        return BatchName;
    }

    public void setBatchName(String batchName) {
        BatchName = batchName;
    }

    public String getAbsentOrPresent() {
        return AbsentOrPresent;
    }

    public void setAbsentOrPresent(String absentOrPresent) {
        AbsentOrPresent = absentOrPresent;
    }

    public String getDateIssued() {
        return DateIssued;
    }

    public void setDateIssued(String dateIssued) {
        DateIssued = dateIssued;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


}

