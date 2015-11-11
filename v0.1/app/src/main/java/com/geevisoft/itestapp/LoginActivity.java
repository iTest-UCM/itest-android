package com.geevisoft.itestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.geevisoft.itestapp.fakeData.FakeData;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    Button button_enter;
    EditText et_user, et_password;
    TextView textView_wrongUserOrPassword;
    FakeData fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fd = new FakeData();
        et_user = (EditText) findViewById(R.id.et_login_user);
        et_password = (EditText) findViewById(R.id.et_login_pasword);
        textView_wrongUserOrPassword = (TextView) findViewById(R.id.textView_wrongUserOrPassword);
        button_enter = (Button) findViewById((R.id.bt_login_enter));
        button_enter.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.bt_login_enter){
            if (true) { // There is Internet connection
                if (fd.getUser(et_user.getText().toString(), et_password.getText().toString())!=null) { // LoginActivity succeed
                    if (textView_wrongUserOrPassword.getVisibility() == View.VISIBLE)
                        textView_wrongUserOrPassword.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(this, ProfileActivity.class);
                    intent.putExtra("user", et_user.getText().toString());
                    intent.putExtra("password", et_password.getText().toString());
                    startActivity(intent);  // Change to profile activity
                    et_user.setText("");
                    et_password.setText("");
                    finish();
                } else { // Wrong user or password
                    textView_wrongUserOrPassword.setVisibility(View.VISIBLE);
                }
            } else { // There is no Internet connection
                Toast.makeText(getApplicationContext(), "Comprueba tu conexión a Internet",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
