package com.geevisoft.itestapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Alvaro on 29/11/2015.
 */
public class Prefs {
    private static final String PREF_NAME = "iTest_prefs";
    static SharedPreferences sharedpreferences;

    private Prefs(Context c){
        sharedpreferences = ((Activity) c).getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    protected Prefs(Context c, String u, String p){
        new Prefs(c);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("User", u);
        editor.putString("Password", p);
        editor.putBoolean("Logged_in", true);
        editor.commit();
    }

    protected static void Log_off(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("User");
        editor.remove("Password");
        editor.remove("Logged_in");
        editor.commit();
    }

    protected static boolean getLogged_in(Context c){
        if (sharedpreferences==null)
            new Prefs(c);
        return sharedpreferences.getBoolean("Logged_in", false);
    }

    protected static String getUser(){
        return sharedpreferences.getString("User", "null");
    }

    protected static String getPassword(){
        return sharedpreferences.getString("Password", "null");
    }
}
