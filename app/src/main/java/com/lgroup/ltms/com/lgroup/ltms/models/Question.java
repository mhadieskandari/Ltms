package com.lgroup.ltms.com.lgroup.ltms.models;

/**
 * Created by Work on 21/09/2015.
 */
public class Question {
    private long id;
    private long uid;
    private String question;
    private String sel_1;
    private String sel_2;
    private String sel_3;
    private String sel_4;
    private String answer;
    private String truesel;
    private int state;

    public Question(long id,long uid,String question,String sel_1,String sel_2,String sel_3,String sel_4,String answer,String truesel,int state){
        this.id=id;
        this.uid=uid;
        this.question=question;
        this.sel_1=sel_1;
        this.sel_2=sel_2;
        this.sel_3=sel_3;
        this.sel_4=sel_4;
        this.truesel=truesel;
        this.answer=answer;
        this.state=state;
    }

    public Question(){

    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getState() {
        return state;
    }

    public long getUid() {
        return uid;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getSel_1() {
        return sel_1;
    }

    public String getSel_2() {
        return sel_2;
    }

    public String getSel_3() {
        return sel_3;
    }

    public String getSel_4() {
        return sel_4;
    }

    public String getTruesel() {
        return truesel;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setSel_1(String sel_1) {
        this.sel_1 = sel_1;
    }

    public void setSel_2(String sel_2) {
        this.sel_2 = sel_2;
    }

    public void setSel_3(String sel_3) {
        this.sel_3 = sel_3;
    }

    public void setSel_4(String sel_4) {
        this.sel_4 = sel_4;
    }

    public void setTruesel(String truesel) {
        this.truesel = truesel;
    }

}
