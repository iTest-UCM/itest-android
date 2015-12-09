package com.geevisoft.itestapp.user;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alvaro on 10/11/2015.
 */
public class User implements Serializable {

    private String username;
    private String password;
    ArrayList<Subject> subjectList = new ArrayList<>();

    public User(String u, String p, ArrayList<Subject> s){
        this.username = u;
        this.password = p;
        this.subjectList = s;
    }

    public User(String u, String p){
        this(u, p, new ArrayList<Subject>());
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public ArrayList<Subject> getSubjectList(){
        return this.subjectList;
    }

    public ArrayList<String> getSubjectTitleList(){
        ArrayList<String> ret = new ArrayList<>();
        for (Subject s: this.subjectList)
            ret.add(s.getTitle());
        return ret;
    }

    public void addSubject(Subject s){
        this.subjectList.add(s);
    }

    public String getSubjectID(int pos){
        if(pos<this.subjectList.size())
            return this.subjectList.get(pos).getID();
        else
            return null;
    }

    public void updateSubject(int pos, Subject s){
        this.subjectList.set(pos, s);
    }
}
