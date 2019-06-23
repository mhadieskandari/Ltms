package com.lgroup.ltms.com.lgroup.ltms.models;

/**
 * Created by Work on 21/09/2015.
 */
public class Setting {
    private long id;
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNameVal(String SettingName,String SettingValue) {
        this.name = SettingName;
        this.value=SettingValue;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }


}
