package com.geevisoft.itestapp.fakeData;

/**
 * Created by Alvaro on 10/11/2015.
 */
public class Test {

    private String title;
    private String mark;
    private String maxMark;
    private String startDate;
    private String endDate;
    private String time;
    private boolean done;

    public Test(String t, String m, String mm, String s, String e, String time){
        this(t,s,e,time);
        this.mark = m;
        this.maxMark = mm;
        this.done = true;
    }


    public Test(String t, String s, String e, String time){
        this.title = t;
        this.startDate = s;
        this.endDate = e;
        this.time = time;
        this.done = false;
    }


    public String getTitle(){
        return this.title;
    }

    public String getStartDate(){
        return this.startDate;
    }

    public String getEndDate(){
        return this.endDate;
    }

    public String getTime(){
        return this.time;
    }

    public String getMark() {
        return this.mark;
    }

    public String getMaxMark(){
        return this.maxMark;
    }

    public boolean getDone(){
        return this.done;
    }
}
