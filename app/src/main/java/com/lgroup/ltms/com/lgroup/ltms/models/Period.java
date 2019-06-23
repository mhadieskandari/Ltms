package com.lgroup.ltms.com.lgroup.ltms.models;

/**
 * Created by Work on 21/09/2015.
 */
public class Period {
    private int id;
    private String title;
    private String descryption;
    private String date_start;
    private String date_end;
    private int test_time;
    private String licence;
    private int state;


    public void setVals(int id,String title,String descryption,String date_start,String date_end,int test_time,String licence,int state){
        this.id=id;
        this.title=title;
        this.descryption=descryption;
        this.date_start=date_start;
        this.date_end=date_end;
        this.test_time=test_time;
        this.licence=licence;
        this.state=state;
    }

    public String getDate_end() {
        return date_end;
    }

    public int getId() {
        return id;
    }

    public String getDate_start() {
        return date_start;
    }

    public String getDescryption() {
        return descryption;
    }

    public int getState() {
        return state;
    }

    public String getState(String tmp) {
        if(state==0)
            return "دوره جدید";
        else {
            return "ارسال شده";
        }
    }

    public String getLicence() {
        return licence;
    }

    public String getTitle() {
        return title;
    }

    public int getTest_time() {
        return test_time;
    }

    public void setDate_end(String date_end) {
        this.date_end = date_end;
    }

    public void setDate_start(String date_start) {
        this.date_start = date_start;
    }

    public void setDescryption(String descryption) {
        this.descryption = descryption;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setTest_time(int test_time) {
        this.test_time = test_time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return title;
    }

}
