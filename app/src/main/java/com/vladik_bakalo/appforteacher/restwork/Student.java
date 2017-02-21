package com.vladik_bakalo.appforteacher.restwork;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Владислав on 20.02.2017.
 */

public class Student {
    @SerializedName("firstName")
    @Expose
    String firstName;

    @SerializedName("lastName")
    @Expose
    String lastName;

    @SerializedName("id")
    @Expose
    String id;

    @SerializedName("birthday")
    @Expose
    String birthDay;

    @SerializedName("courses")
    @Expose
    List<Course> courseList;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

//    @Override
//    public String toString() {
//        return "Student{" +
//                "firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", id='" + id + '\'' +
//                ", birthDay=" + birthDay +
//                ", courseList=" + courseList +
//                '}';
//    }
}
