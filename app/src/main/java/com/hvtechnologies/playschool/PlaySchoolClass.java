package com.hvtechnologies.playschool;

public class PlaySchoolClass {

    String Name , Id , Uid , Address , Email , Phone ;

    public PlaySchoolClass(String name, String id, String uid, String address, String email, String phone) {
        Name = name;
        Id = id;
        Uid = uid;
        Address = address;
        Email = email;
        Phone = phone;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
