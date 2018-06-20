package com.dexterlearning.dexapp;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Course {
    private String name;
    private String pdf;
    private int rating;
    private String year;

    public Course(){
    }

    public Course(String name, String pdf) {
        this.name = name;
        this.pdf = pdf;
    }

    public Course(String name, String pdf, int rating, String year) {
        this.name = name;
        this.pdf = pdf;
        this.rating = rating;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public String getPdf() {
        return pdf;
    }

    public int getRating() {
        return rating;
    }

    public String getYear() {
        return year;
    }
}
