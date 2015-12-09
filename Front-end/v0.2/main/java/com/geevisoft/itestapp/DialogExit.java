package com.geevisoft.itestapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

public class DialogExit extends Activity implements View.OnClickListener {

    private static CheckBox cb_closeSession;

    @Override
    public void onClick(View v) {

    }

    public void show(final Activity act) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act);
        LayoutInflater inflater = act.getLayoutInflater();
        View exit_d = inflater.inflate(R.layout.dialog_exit, null);
        cb_closeSession = (CheckBox) exit_d.findViewById(R.id.cb_closeSession);
        alertDialogBuilder.setView(exit_d);
        alertDialogBuilder.setTitle("¿Salir?");
        alertDialogBuilder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (cb_closeSession.isChecked())
                    Prefs.Log_off();
                act.finish();
                System.exit(0);
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
