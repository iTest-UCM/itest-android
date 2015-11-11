package com.geevisoft.itestapp.fakeData;

import java.util.ArrayList;

/**
 * Created by Alvaro on 10/11/2015.
 */
public class User {

    private String user;
    private String password;
    ArrayList<Subject> subjectList = new ArrayList<>();

    public User(String u, String p, ArrayList<Subject> s){
        this.user = u;
        this.password = p;
        this.subjectList = s;
    }


    public String getUser(){
        return this.user;
    }

    public String getPassword(){
        return this.password;
    }

    public ArrayList<Subject> getSubjectList(){
        return this.subjectList;
    }
}
