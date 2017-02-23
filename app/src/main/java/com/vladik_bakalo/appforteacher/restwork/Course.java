package com.vladik_bakalo.appforteacher.restwork;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владислав on 20.02.2017.
 */
public class Course {
    String name;
    int mark;

    public String getCourseName() {
        return name;
    }

    public void setCourseName(String courseName) {
        this.name = courseName;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
    public static double getMarkAVG(List<Course> courses)
    {
        int sum = 0;
        int count = 0;
        for (Course course :
                courses) {
            sum += course.getMark();
            count++;
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
                item.setMark(coursesCursor.getInt(1));
                courses.add(item);
            }while (coursesCursor.moveToNext());
        }
        if (coursesCursor != null)
            coursesCursor.close();
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
