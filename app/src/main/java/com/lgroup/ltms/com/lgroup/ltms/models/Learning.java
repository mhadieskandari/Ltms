package com.lgroup.ltms.com.lgroup.ltms.models;

/**
 * Created by Work on 21/09/2015.
 */
public class Learning {

    private long id;
    private long udid;
    private long contentid;
    private int startpage;
    private int pagenumber;
    private String content;
    private String descryption;
    private int state;

    public Learning() {
    }

    public Learning(long id, long udid, long contentid, int startpage) {
        this.id = id;
        this.udid = udid;
        this.contentid = contentid;
        this.startpage = startpage;
    }

    public long getContentid() {
        return contentid;
    }

    public void setContentid(long contentid) {
        this.contentid = contentid;
    }

    public int getStartpage() {
        return startpage;
    }

    public void setStartpage(int startpage) {
        this.startpage = startpage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getPagenumber() {
        return pagenumber;
    }

    public int getState() {
        return state;
    }

    public long getUdid() {
        return udid;
    }

    public String getContent() {
        return content;
    }

    public String getDescryption() {
        return descryption;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDescryption(String descryption) {
        this.descryption = descryption;
    }

    public void setPagenumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUdid(long udid) {
        this.udid = udid;
    }

}
