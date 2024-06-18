package com.bean;

import java.util.Date;

public class apply extends base {

    private lesson lesson;
    private int temp;
    private String reason;
    private Date applytime;
    private Date endtime;
    private String feedback = "1";
    private String name;
    private int id;

    public apply() {
        reason = null;
        feedback = null;
        name = null;
    }

    public apply(int id, String name, int temp, lesson lesson, String reason, Date applytime, Date endtime,
            String feedback) {
        this.id = id;
        this.name = name;
        this.temp = temp;
        this.lesson = lesson;
        this.reason = reason;
        this.applytime = applytime;
        this.endtime = endtime;
        this.feedback = feedback;
    }

    public lesson getlesson() {
        return this.lesson;
    }

    public int gettemp() {
        return this.temp;
    }

    public int getid() {
        return this.id;
    }

    public Date getapplytime() {
        return this.applytime;
    }

    public Date getendtime() {
        return this.endtime;
    }

    public String getreason() {
        return this.reason;
    }

    public String getfeedback() {
        return this.feedback;
    }

    public String getname() {
        return this.name;
    }

    public void setLesson(lesson lesson) {
        this.lesson = lesson;
    }

    public void settemp(int temp) {
        this.temp = temp;
    }

    public void setid(int id) {
        this.id = id;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setapplytime(Date applytime) {
        this.applytime = applytime;
    }

    public void setendtime(Date endtime) {
        this.endtime = endtime;
    }

    public void setreason(String reason) {
        this.reason = reason;
    }

    public void setfeedback(String feedback) {
        this.feedback = feedback;
    }

}
