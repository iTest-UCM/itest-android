package com.geevisoft.itestapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.geevisoft.itestapp.fakeData.FakeData;
import com.geevisoft.itestapp.fakeData.Subject;

public class SubjectActivity extends ActionBarActivity {
    FakeData fd;
    TextView tv_subject_title;
    Subject s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        fd = new FakeData();
        Intent i = getIntent();
        int position = i.getIntExtra("subject", -1);
        s = fd.getUser(i.getStringExtra("user"),i.getStringExtra("password")).getSubjectList().get(position);
        tv_subject_title = (TextView) findViewById(R.id.tv_subject_subjectTitle);
        tv_subject_title.setText(s.getTitle());
    }
}
