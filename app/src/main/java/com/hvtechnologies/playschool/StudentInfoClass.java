package com.hvtechnologies.playschool;

public class StudentInfoClass {

    String StdName , PName , Num ,  StdId , SUid ;


    public StudentInfoClass(String stdName, String PName, String num, String stdId, String SUid) {
        StdName = stdName;
        this.PName = PName;
        Num = num;
        StdId = stdId;
        this.SUid = SUid;
    }


    public String getStdName() {
        return StdName;
    }

    public void setStdName(String stdName) {
        StdName = stdName;
    }

    public String getPName() {
        return PName;
    }

    public void setPName(String PName) {
        this.PName = PName;
    }

    public String getNum() {
        return Num;
    }

    public void setNum(String num) {
        Num = num;
    }

    public String getStdId() {
        return StdId;
    }

    public void setStdId(String stdId) {
        StdId = stdId;
    }

    public String getSUid() {
        return SUid;
    }

    public void setSUid(String SUid) {
        this.SUid = SUid;
    }
}
