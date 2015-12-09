package com.geevisoft.itestapp.user;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alvaro on 10/11/2015.
 */
public class Subject implements Serializable {

    private String ID;
    private String title;
    ArrayList<Test> testList = new ArrayList<>();

    public Subject(String ID, String t, ArrayList<Test> tests){
        this.ID = ID;
        this.title = t;
        this.testList = tests;
    }

    public Subject(String ID, String t) {
        this(ID, t, new ArrayList<Test>());
    }

    public String getID() {
        return this.ID;
    }

    public String getTitle(){
        return this.title;
    }


    public String[][] getThreeNextTests(){
        String[][] ret = new String[3][2];
        int i = 0;
        int j = 0;
        while(i<3){ // We need three tests entries (empty or not)
            if (j<this.testList.size()){ // There may be less tests than 3
                Test t = this.testList.get(j);
                if (t.getDone()==false) { // We only need undone tests
                    ret[i][0] = t.getTitle();
                    ret[i][1] = t.getStartDate();
                    i++;
                }
                j++;
            } else {
                ret[i][0] = "";
                ret[i][1] = "";
                i++;
            }
        }
        return ret;
    }

    public ArrayList<Test> getDoneTests(){
        ArrayList<Test> tests = new ArrayList<>();
        for (Test t: this.testList)
            if (t.getDone()==true)
                tests.add(t);
        return tests;
    }

    public ArrayList<Test> getNextTests(){
        ArrayList<Test> tests = new ArrayList<>();
        for (Test t: this.testList)
            if (t.getDone()==false)
                tests.add(t);
        return tests;
    }

    public void addTest(Test t){
        this.testList.add(t);
    }
}
