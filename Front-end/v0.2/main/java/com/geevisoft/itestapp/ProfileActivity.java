package com.geevisoft.itestapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.geevisoft.itestapp.user.User;

public class ProfileActivity extends ActionBarActivity implements View.OnClickListener {
    private ImageButton imageButton;
    private ListView lv_subjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        User user = (User) getIntent().getSerializableExtra("User");
        imageButton = (ImageButton) findViewById(R.id.ib_profile_exit);
        imageButton.setOnClickListener(this);
        loadSubjectsInfo(user);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ib_profile_exit)
            (new DialogExit()).show(this); // Show exit dialog
    }

    private void loadSubjectsInfo(final User user){
        ListAdapter adapter = new SubjectAdapter(this,
                R.layout.list_view_subject_profile,
                user.getSubjectList());
        lv_subjectList = (ListView) findViewById(R.id.lv_profile_subjectList);
        lv_subjectList.setAdapter(adapter);

        final Context c = this;
        lv_subjectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                (new ServerRequest(c)).getSubjectPanel(user, position, user.getSubjectID(position));
            }
        });
    }

}
