package com.geevisoft.itestapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.geevisoft.itestapp.user.Subject;
import com.geevisoft.itestapp.user.Test;
import com.geevisoft.itestapp.user.User;

public class SubjectActivity extends Activity implements AdapterView.OnItemClickListener {

    private User user;

    private TextView tv_subject_title;
    private LinearLayout lv_subject_doTests, lv_subject_nextTests,
            lv_subject_reviseTests, lv_subject_doneTests;
    private LayoutInflater inflater;

    private DrawerLayout mDrawer;
    private ListView mDrawerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);

        Intent i = getIntent();
        int position = i.getIntExtra("subject", -1);
        user = (User) i.getSerializableExtra("User");
        Subject s = user.getSubjectList().get(position);
        tv_subject_title = (TextView) findViewById(R.id.tv_subject_subjectTitle);
        tv_subject_title.setText(s.getTitle());
        inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        loadDoTestsDetails(s);
        loadNextTestsDetails(s);
        loadReviseTestsDetails(s);
        loadDoneTestsDetails(s);

        loadMenu(user);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        (new ServerRequest(this)).getSubjectPanel(user, position, user.getSubjectID(position));
        mDrawer.closeDrawers();
        this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if (mDrawer.isDrawerOpen(mDrawerOptions)){
                    mDrawer.closeDrawers();
                }else{
                    mDrawer.openDrawer(mDrawerOptions);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Methods which load elements
    private void loadDoTestsDetails(Subject s){
        lv_subject_doTests = (LinearLayout) findViewById(R.id.linear_lv_doTests);
        final Context c = this;
        for (Test t: s.getNextTests()){
            View mLinearView = inflater.inflate(R.layout.linear_lv_tests_details, null);

            TextView tv_title = (TextView) mLinearView.findViewById(R.id.tv_subject_test_title);
            TextView tv_date = (TextView) mLinearView.findViewById(R.id.tv_subject_test_sec_element);

            tv_title.setText(t.getTitle());
            tv_date.setText("(" + t.getStartDate() + ")");

            lv_subject_doTests.addView(mLinearView);

            final String texto = t.getTitle();
            mLinearView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Here it should go to activity_doTest
                    Toast.makeText(c, "Examen: " + texto, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadNextTestsDetails(Subject s){
        lv_subject_nextTests = (LinearLayout) findViewById(R.id.linear_lv_nextTests);
        for (Test t: s.getNextTests()){
            View mLinearView = inflater.inflate(R.layout.linear_lv_tests_details, null);

            TextView tv_title = (TextView) mLinearView.findViewById(R.id.tv_subject_test_title);
            TextView tv_date = (TextView) mLinearView.findViewById(R.id.tv_subject_test_sec_element);

            tv_title.setText(t.getTitle());
            tv_date.setText("(" + t.getStartDate() + ")");

            lv_subject_nextTests.addView(mLinearView);
        }
    }

    private void loadReviseTestsDetails(Subject s){
        lv_subject_reviseTests = (LinearLayout) findViewById(R.id.linear_lv_reviseTests);
        final Context c = this;
        for (Test t: s.getDoneTests()){
            View mLinearView = inflater.inflate(R.layout.linear_lv_tests_details, null);

            TextView tv_title = (TextView) mLinearView.findViewById(R.id.tv_subject_test_title);
            TextView tv_mark = (TextView) mLinearView.findViewById(R.id.tv_subject_test_sec_element);

            tv_title.setText(t.getTitle());
            tv_mark.setText("(Nota: " + t.getMark() + "/" + t.getMaxMark() + ")");

            lv_subject_reviseTests.addView(mLinearView);

            final String texto = t.getTitle();
            mLinearView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Here it should go to activity_reviseTest
                    Toast.makeText(c, "Examen: " + texto, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadDoneTestsDetails(Subject s){
        lv_subject_doneTests = (LinearLayout) findViewById(R.id.linear_lv_doneTests);
        for (Test t: s.getDoneTests()){
            View mLinearView = inflater.inflate(R.layout.linear_lv_done_tests_details, null);

            TextView tv_title = (TextView) mLinearView.findViewById(R.id.tv_done_tests_testTitle);
            TextView tv_mark = (TextView) mLinearView.findViewById(R.id.tv_done_tests_testMark);
            TextView tv_date = (TextView) mLinearView.findViewById(R.id.tv_done_tests_testDate);
            TextView tv_time = (TextView) mLinearView.findViewById(R.id.tv_done_tests_testTime);

            tv_title.setText(t.getTitle());
            tv_mark.setText(t.getMark() + "/" + t.getMaxMark());
            tv_date.setText(t.getStartDate());
            tv_time.setText(t.getTime());

            lv_subject_doneTests.addView(mLinearView);
        }
    }

    private void loadMenu(User user){
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerOptions = (ListView) findViewById(R.id.left_drawer);
        mDrawer = (DrawerLayout) findViewById(R.id.cv_menu);

        mDrawerOptions.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1, user.getSubjectTitleList()));
        mDrawerOptions.setOnItemClickListener(this);
    }
}
