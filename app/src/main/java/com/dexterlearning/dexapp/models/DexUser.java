package com.dexterlearning.dexapp.models;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class DexUser {
    private String name;
    private String address;
    private String email;
    private String password;
    private String notes;

    public DexUser(){

    }

    public DexUser(String name, String address,
                   String email, String password, String notes){
        this.name = name;
        this.address =  address;
        this.email = email;
        this.password = password;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNotes() {
        return notes;
    }

}
