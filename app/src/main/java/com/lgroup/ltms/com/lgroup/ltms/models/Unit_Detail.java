package com.lgroup.ltms.com.lgroup.ltms.models;

/**
 * Created by Work on 21/09/2015.
 */
public class Unit_Detail {
    private long id;
    private long uid;
    private String name;
    private int startpage;
    private int endpage;
    private int state;


    public Unit_Detail() {
    }

    public Unit_Detail(long id, String name, long uid) {
        this.id = id;
        this.uid = uid;
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public int getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public int getEndpage() {
        return endpage;
    }

    public int getStartpage() {
        return startpage;
    }

    public long getUid() {
        return uid;
    }

    public void setEndpage(int endpage) {
        this.endpage = endpage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStartpage(int startpage) {
        this.startpage = startpage;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Override
    public String toString(){
        return " از صفحه "+startpage + " تا صفحه ی "+endpage;
    }
}
