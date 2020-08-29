package com.hvtechnologies.playschool;

public class SeeAttendanceDateWiseClass {


    public String ClassName ;
    public String BatchName ;
    public String Date ;
    public String Time ;
    public String DateVal;
    public String DateKey;

    public SeeAttendanceDateWiseClass(String className, String batchName, String date, String time , String dtval , String dtkey) {
        ClassName = className;
        BatchName = batchName;
        Date = date;
        Time = time;
        DateVal = dtval;
        DateKey = dtkey;
    }

    public String getDateVal() {
        return DateVal;
    }

    public void setDateVal(String dateVal) {
        DateVal = dateVal;
    }

    public String getDateKey() {
        return DateKey;
    }

    public void setDateKey(String dateKey) {
        DateKey = dateKey;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getBatchName() {
        return BatchName;
    }

    public void setBatchName(String batchName) {
        BatchName = batchName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
