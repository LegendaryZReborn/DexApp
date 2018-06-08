package com.dexterlearning.dexapp;

public class Course {
    String name;
    String url;

    public Course(){
        name = "";
        url = "";
    }

    public Course(String name, String url){
        name = "";
        url = "";
    }

    public String getCourseUrl(){
        return url;
    }

    public String getCourseName(){
        return name;
    }

}
