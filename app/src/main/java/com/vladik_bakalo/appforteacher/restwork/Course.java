package com.vladik_bakalo.appforteacher.restwork;

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

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + name + '\'' +
                ", mark=" + mark +
                '}';
    }
}
