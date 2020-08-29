package com.hvtechnologies.playschool;

public class AttendanceClass {



    private String NameOfStudent ;
    private String AbsentOrPresent ;
    private String StudentUserId ;
    private String StudentId ;
    private String Num;

    public AttendanceClass(String nameOfStudent, String absentOrPresent, String studentUserId, String studentId, String num) {
        NameOfStudent = nameOfStudent;
        AbsentOrPresent = absentOrPresent;
        StudentUserId = studentUserId;
        StudentId = studentId;
        Num = num;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }


    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getNameOfStudent() {
        return NameOfStudent;
    }

    public void setNameOfStudent(String nameOfStudent) {
        NameOfStudent = nameOfStudent;
    }

    public String getAbsentOrPresent() {
        return AbsentOrPresent;
    }

    public void setAbsentOrPresent(String absentOrPresent) {
        AbsentOrPresent = absentOrPresent;
    }

    public String getStudentUserId() {
        return StudentUserId;
    }

    public void setStudentUserId(String studentUserId) {
        StudentUserId = studentUserId;
    }


}
