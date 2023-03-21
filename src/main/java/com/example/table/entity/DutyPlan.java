package com.example.table.entity;

public class DutyPlan {
    private int id;
    private String name;
    private String phone;
    private String olandate;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOlandate() {
        return olandate;
    }

    public void setOlandate(String olandate) {
        this.olandate = olandate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
