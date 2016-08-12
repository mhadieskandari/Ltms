package com.lgroup.ltms.com.lgroup.ltms.models;

/**
 * Created by Asus on 08/12/2016.
 */
public class Content {
    private long id;
    private String content;


    public Content() {
    }

    public Content(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
