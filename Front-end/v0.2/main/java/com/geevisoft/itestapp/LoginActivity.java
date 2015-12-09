package com.geevisoft.itestapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {
    private Button button_enter;
    private EditText et_user, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Prefs.getLogged_in(this))
            (new ServerRequest(this)).sendLogin(Prefs.getUser(), Prefs.getPassword());
        else {
            //this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_login);
            et_user = (EditText) findViewById(R.id.et_login_user);
            et_password = (EditText) findViewById(R.id.et_login_pasword);
            button_enter = (Button) findViewById((R.id.bt_login_enter));
            button_enter.setOnClickListener(this);
            et_password.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                            (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        onClick((View) findViewById(R.id.bt_login_enter));
                        return true;
                    }
                    return false;
                }
            });
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_login_enter)
            (new ServerRequest(this)).sendLogin(et_user.getText().toString().replaceAll("\\s",""), /* Removing whitespaces */
                    et_password.getText().toString().replaceAll("\\s",""));
    }
}