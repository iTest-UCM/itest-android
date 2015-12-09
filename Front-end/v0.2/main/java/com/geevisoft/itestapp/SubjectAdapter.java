package com.geevisoft.itestapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.geevisoft.itestapp.user.Subject;

import java.util.ArrayList;

/**
 * Created by Alvaro on 10/11/2015.
 */
public class SubjectAdapter extends ArrayAdapter<Subject> {

    private Context context;
    private int layoutId;
    private ArrayList<Subject> subjectsList = null;

    public SubjectAdapter(Context context, int resource, ArrayList<Subject> subjectsList) {
        super(context, resource, subjectsList);
        this.context = context;
        this.layoutId = resource;
        this.subjectsList = subjectsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        SubjectHolder holder = null;

        if (row==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutId, parent, false);

            holder = new SubjectHolder();
            holder.iv_rightArrow = (ImageView) row.findViewById(R.id.iv_rightArrow);
            holder.tv_subjectTitle = (TextView) row.findViewById(R.id.tv_subjectTitle);
            holder.tv_testTitle1 = (TextView) row.findViewById(R.id.tv_testTitle1);
            holder.tv_testDate1 = (TextView) row.findViewById(R.id.tv_testDate1);
            holder.tv_testTitle2 = (TextView) row.findViewById(R.id.tv_testTitle2);
            holder.tv_testDate2 = (TextView) row.findViewById(R.id.tv_testDate2);
            holder.tv_testTitle3 = (TextView) row.findViewById(R.id.tv_testTitle3);
            holder.tv_testDate3 = (TextView) row.findViewById(R.id.tv_testDate3);
            row.setTag(holder);
        } else
            holder = (SubjectHolder) row.getTag();

        Subject subject = subjectsList.get(position);
        String [][] tests = subject.getThreeNextTests();

        // Subject Title
        holder.tv_subjectTitle.setText(subject.getTitle());

        // Test 1
        if (tests[0][0]!=""){
            holder.tv_testTitle1.setText(tests[0][0]);
            holder.tv_testDate1.setText("(" + tests[0][1] + ")");
        } else {
            holder.tv_testTitle1.setVisibility(View.INVISIBLE);
            holder.tv_testDate1.setVisibility(View.INVISIBLE);
        }

        // Test 2
        if (tests[1][0]!=""){
            holder.tv_testTitle2.setText(tests[1][0]);
            holder.tv_testDate2.setText("(" + tests[1][1] + ")");
        } else {
            holder.tv_testTitle2.setVisibility(View.INVISIBLE);
            holder.tv_testDate2.setVisibility(View.INVISIBLE);
        }

        // Test 3
        if (tests[2][0]!=""){
            holder.tv_testTitle3.setText(tests[2][0]);
            holder.tv_testDate3.setText("(" + tests[2][1] + ")");
        } else {
            holder.tv_testTitle3.setVisibility(View.INVISIBLE);
            holder.tv_testDate3.setVisibility(View.INVISIBLE);
        }

        return row;
    }

    private static class SubjectHolder {
        ImageView iv_rightArrow;
        TextView tv_subjectTitle;
        TextView tv_testTitle1, tv_testDate1;
        TextView tv_testTitle2, tv_testDate2;
        TextView tv_testTitle3, tv_testDate3;
    }
}
