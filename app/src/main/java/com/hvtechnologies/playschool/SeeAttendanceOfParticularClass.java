package com.hvtechnologies.playschool;

public class SeeAttendanceOfParticularClass {

    public String StudentName ;
    public String Marked ;

    public SeeAttendanceOfParticularClass(String studentName, String marked) {
        StudentName = studentName;
        Marked = marked;

    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getMarked() {
        return Marked;
    }

    public void setMarked(String marked) {
        Marked = marked;
    }
}
