package com.geevisoft.itestapp.fakeData;

import java.util.ArrayList;

/**
 * Created by Alvaro on 10/11/2015.
 */
public class Subject {

    private String title;
    ArrayList<Test> testList = new ArrayList<>();

    public Subject(String t, ArrayList<Test> tests){
        this.title = t;
        this.testList = tests;
    }


    public String getTitle(){
        return this.title;
    }

    public ArrayList<Test> getTestList(){
        return this.testList;
    }

    public String[][] getNextTests(){
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
}
