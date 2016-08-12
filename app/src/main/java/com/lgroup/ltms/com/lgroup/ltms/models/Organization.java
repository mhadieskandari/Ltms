package com.lgroup.ltms.com.lgroup.ltms.models;

/**
 * Created by Asus on 12/30/2015.
 */
public class Organization {
    private long id;
    private String title;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return getTitle();
    }
}
