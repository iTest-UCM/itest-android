package com.geevisoft.itestapp.fakeData;

import java.util.ArrayList;

/**
 * Created by Alvaro on 10/11/2015.
 */
public class FakeData {
    ArrayList<User> data = new ArrayList<>();

    public FakeData(){
        User u = new User("Manolo", "123", new ArrayList<Subject>());

        // Subject 1
        Subject s1 = new Subject("Asignatura 1", new ArrayList<Test>());
        s1.getTestList().add(new Test("Parcial 1", "4", "10", "10/11/2015 11:00:00", "10/11/2015 11:09:00", "00:09:00"));
        s1.getTestList().add(new Test("Parcial 2", "5.3", "7", "15/11/2015 11:00:00", "15/11/2015 11:15:00", "00:15:00"));
        s1.getTestList().add(new Test("Final", "21/11/2015 08:30:00", "21/11/2015 08:55:00", "00:25:00"));
        u.getSubjectList().add(s1);

        // Subject 2
        Subject s2 = new Subject("Asignatura 2", new ArrayList<Test>());
        s2.getTestList().add(new Test("Parcial 1", "6.35", "10", "20/11/2015 09:00:00", "20/11/2015 10:00:00", "01:00:00"));
        s2.getTestList().add(new Test("Parcial 2", "7.8", "10", "25/11/2015 12:00:00", "25/11/2015 12:25:00", "00:25:00"));
        s2.getTestList().add(new Test("Parcial 3", "03/12/2015 14:00:00", "03/12/2015 15:30:00", "01:30:00"));
        s2.getTestList().add(new Test("Final 3", "12/01/2016 18:00:00", "12/01/2016 19:10:00", "01:10:00"));
        u.getSubjectList().add(s2);

        data.add(u);
    }

    public User getUser(String u, String p){
        for (User us: data){
            if (us.getUser().equals(u) && us.getPassword().equals(p))
                return us;
        }
        return null; // Wrong user or password
    }

    public ArrayList<User> getData(){
        return this.data;
    }

}
