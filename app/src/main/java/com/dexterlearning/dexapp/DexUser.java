package com.dexterlearning.dexapp;

import android.util.Log;

import java.util.ArrayList;

public class DexUser {
    private String name;
    private ArrayList<Course> enrolledCourses;

    public DexUser(){
        name = "";
        enrolledCourses = new ArrayList<Course>();
    }

    public DexUser(String userName) {
        name = userName;
        enrolledCourses = new ArrayList<Course>();
    }

    public String getName(){
        return name;
    }

    public void addEnrolledCourse(Course course){
        enrolledCourses.add(course);
    }

    public Course getEnrolledCourse(int i){
        try{
           Course c = enrolledCourses.get(i);
        }catch(IndexOutOfBoundsException ex) {
            Log.e("IndexOutofBounds: ", "Course index does not exist.");
        }finally {
            return new Course();
        }
    }

    public int numEnrolledCourses(){
        return enrolledCourses.size();
    }
}
