package com.lgroup.ltms.com.lgroup.ltms.models;

/**
 * Created by Work on 21/09/2015.
 */
public class Unit {

    private long id;
    private int Pid;
    private String name;
    private String descryption;
    private int state;

    public Unit(long id,int pid,String name, String descryption,int state){
        this.id=id;
        this.Pid=pid;
        this.name=name;
        this.descryption=descryption;
        this.state=state;
    }

    public  Unit(){

    }

    public String getDescryption() {
        return descryption;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public int getPid() {
        return Pid;
    }

    public int getState() {
        return state;
    }
    public String getStateName() {
        if(state==0){

            return "ناتمام";
        }
        else
        {
            return "پایان یافته";
        }
    }

    public String getName() {
        return name;
    }

    public void setDescryption(String descryption) {
        this.descryption = descryption;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    @Override
    public String toString(){
        return name;
    }

}
