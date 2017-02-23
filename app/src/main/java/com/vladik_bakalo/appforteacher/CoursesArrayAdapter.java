package com.vladik_bakalo.appforteacher;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vladik_bakalo.appforteacher.restwork.Course;

import java.util.List;

/**
 * Class is used in listview(DialogCourseView)
 */

public class CoursesArrayAdapter extends ArrayAdapter<Course> {
    public CoursesArrayAdapter(Context context,  List<Course> courses) {
        super(context, R.layout.rowcourselayout, courses);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.rowcourselayout, null);
        }

        Course p = getItem(position);

        TextView tCourseName = (TextView) v.findViewById(R.id.courseName);
        TextView tCourseMark = (TextView) v.findViewById(R.id.courseMark);
        tCourseName.setText(p.getCourseName());
        tCourseMark.setText(String.valueOf(p.getMark()));

        return v;

    }
}
