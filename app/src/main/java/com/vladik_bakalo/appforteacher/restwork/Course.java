package com.vladik_bakalo.appforteacher.restwork;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 20.02.2017.
 */
public class Course {
    String name;
    double mark;

    public String getCourseName() {
        return name;
    }

    public void setCourseName(String courseName) {
        this.name = courseName;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }
    public static double getMarkAVG(List<Course> courses)
    {
        double sum = 0;
        int count = 0;
        for (Course course :
                courses) {
            sum += course.getMark();
        }
        return (sum / count);
    }
    public static List<Course> getListCourses(Cursor cursor)
    {
        Cursor coursesCursor = cursor;
        List<Course> courses = new ArrayList<>();
        if (coursesCursor.moveToFirst())
        {
            Course item;
            do {
                item = new Course();
                item.setCourseName(coursesCursor.getString(0));
                item.setMark(coursesCursor.getDouble(1));
                courses.add(item);
            }while (coursesCursor.moveToNext());
        }
        return courses;
    }
    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + name + '\'' +
                ", mark=" + mark +
                '}';
    }
}
