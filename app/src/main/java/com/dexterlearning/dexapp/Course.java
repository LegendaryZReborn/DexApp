package com.dexterlearning.dexapp;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Course {
    String name;
    Object date;
    String url;

    public Course(){
    }

    public Course(String name, Object date, String url){
        this.name = name;
        this.date = date;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public Object getDate() {
        return date	;
    }

    public long getDateLong(){
        return (long)date;
    }

    public String getUrl() {
        return url;
    }
}
