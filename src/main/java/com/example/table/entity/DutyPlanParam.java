package com.example.table.entity;

import java.util.List;

public class DutyPlanParam {
    private List<DutyPlan> list;
    private String begintime;
    private String endtime;
    private int delsign;

    public List<DutyPlan> getList() {
        return list;
    }

    public void setList(List<DutyPlan> list) {
        this.list = list;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getDelsign() {
        return delsign;
    }

    public void setDelsign(int delsign) {
        this.delsign = delsign;
    }
}
