package com.dexterlearning.dexapp.models;

public class SignedInUser {
    private static final SignedInUser instance = new SignedInUser();
    public String userName;

    //Private constructor prevents instantiation from other classes
    private SignedInUser(){
    }

    public static SignedInUser getInstance(){
        return instance;
    }
}
