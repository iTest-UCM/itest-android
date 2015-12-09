package com.geevisoft.itestapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

public class DialogExit extends Activity implements View.OnClickListener {

    CheckBox cb_closeSession;

    @Override
    public void onClick(View v) {

    }

    public void show(Activity act) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act);
        LayoutInflater inflater = act.getLayoutInflater();
        alertDialogBuilder.setView(inflater.inflate(R.layout.dialog_exit,null));
        alertDialogBuilder.setTitle("¿Salir?");
        alertDialogBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cb_closeSession = (CheckBox) findViewById(R.id.cb_closeSession);
                if (cb_closeSession.isChecked()){
                    // Cerramos sesion
                }
                finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // If user cancels, nothing gets done
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
