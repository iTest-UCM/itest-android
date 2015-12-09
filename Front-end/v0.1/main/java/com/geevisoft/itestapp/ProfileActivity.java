package com.geevisoft.itestapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.geevisoft.itestapp.fakeData.FakeData;

public class ProfileActivity extends ActionBarActivity implements View.OnClickListener {
    boolean logged_in = false;
    String user;
    String password;
    ImageButton imageButton;
    ListView lv_subjectList;
    FakeData fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fd = new FakeData();
        Intent i = getIntent();
        this.user = i.getStringExtra("user");
        this.password = i.getStringExtra("password");
        this.logged_in = true;
        imageButton = (ImageButton) findViewById(R.id.ib_profile_exit);
        imageButton.setOnClickListener(this);
        loadSubjectsInfo();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ib_profile_exit)
            (new DialogExit()).show(this); // Show exit dialog
    }

    public void loadSubjectsInfo(){
        ListAdapter adapter = new SubjectAdapter(this,
                R.layout.list_view_subject_profile,
                fd.getUser(user,password).getSubjectList());
        lv_subjectList = (ListView) findViewById(R.id.lv_profile_subjectList);
        lv_subjectList.setAdapter(adapter);
        final Context  c = this;
        lv_subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(c, SubjectActivity.class);
                i.putExtra("subject", position); // Should be an ID linked to the subject
                i.putExtra("user", user);
                i.putExtra("password", password);
                startActivity(i);
            }
        });
    }

}
