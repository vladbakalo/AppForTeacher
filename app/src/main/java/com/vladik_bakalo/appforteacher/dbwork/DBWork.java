package com.vladik_bakalo.appforteacher.dbwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vladik_bakalo.appforteacher.restwork.Course;
import com.vladik_bakalo.appforteacher.restwork.Student;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Владислав on 21.02.2017.
 */

public class DBWork {
    private SQLiteDatabase sqLiteDatabase;
    private StudentDBHelper sqLiteOpenHelper;

    public DBWork(Context context) {
        sqLiteOpenHelper = new StudentDBHelper(context);
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void deleteAllStudentData() {
        sqLiteDatabase.execSQL("delete from " + sqLiteOpenHelper.TBL_NAME_STUDENT);
        sqLiteDatabase.execSQL("delete from " + sqLiteOpenHelper.TBL_NAME_COURSE);
        sqLiteDatabase.execSQL("delete from " + sqLiteOpenHelper.TBL_NAME_STUDENTandCOURSE);
    }

    public void writeStudentsToDB(List<Student> students) {
        for (Student student : students) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(StudentDBHelper._ID, student.getId());
            contentValues.put(StudentDBHelper.CM_BIRTHDAY, Integer.parseInt(student.getBirthDay()));
            contentValues.put(StudentDBHelper.CM_FIRST_NAME, student.getFirstName());
            contentValues.put(StudentDBHelper.CM_LAST_NAME, student.getLastName());

            sqLiteDatabase.insert(StudentDBHelper.TBL_NAME_STUDENT, null, contentValues);
            for (Course course :
                    student.getCourseList()) {
                //Check for exist Course
                long courseId = 0;
                Cursor cursor = sqLiteDatabase.query(StudentDBHelper.TBL_NAME_COURSE,
                        new String[]{StudentDBHelper._ID}, StudentDBHelper.CM_COURSE_NAME + " = ? ",
                        new String[]{course.getCourseName()}, null, null, null);
                if (cursor.moveToFirst()) {
                    courseId = cursor.getLong(0);
                }
                cursor.close();
                //CourseId for adding to StudentAndCourse table

                if (courseId == 0) {

                    //If Course isn't exist
                    ContentValues contentValuesForCourse = new ContentValues();
                    contentValuesForCourse.put(StudentDBHelper.CM_COURSE_NAME, course.getCourseName());
                    courseId = sqLiteDatabase.insert(StudentDBHelper.TBL_NAME_COURSE, null, contentValuesForCourse);
                }
                //Add to CourseandStudent table
                ContentValues contentValuesForCourseAndStudent = new ContentValues();
                contentValuesForCourseAndStudent.put(StudentDBHelper.CM_COURSE_ID, courseId);
                contentValuesForCourseAndStudent.put(StudentDBHelper.CM_STUDENT_ID, student.getId());
                contentValuesForCourseAndStudent.put(StudentDBHelper.CM_MARK, course.getMark());
                sqLiteDatabase.insert(sqLiteOpenHelper.TBL_NAME_STUDENTandCOURSE,
                        null, contentValuesForCourseAndStudent);
            }

        }

    }

    public Cursor getCursorOfAllStudents() {
        Cursor cursor = sqLiteDatabase.query(sqLiteOpenHelper.TBL_NAME_STUDENT,
                new String[]{
                        sqLiteOpenHelper._ID,
                        sqLiteOpenHelper.CM_FIRST_NAME,
                        sqLiteOpenHelper.CM_LAST_NAME,
                        sqLiteOpenHelper.CM_BIRTHDAY}, null, null, null, null, null);
        return cursor;
    }
    public Cursor getCursorOfCoursesByStudentId(String studentId)
    {
        String table = sqLiteOpenHelper.TBL_NAME_STUDENTandCOURSE
                + " as SC inner join "
                + sqLiteOpenHelper.TBL_NAME_COURSE
                + " as CO on SC."
                + sqLiteOpenHelper.CM_COURSE_ID + " = CO." + sqLiteOpenHelper._ID;
        Cursor cursor = sqLiteDatabase.query(table,
                new String[]{"CO." + sqLiteOpenHelper.CM_COURSE_NAME + " as CourseName",
                "SC."+sqLiteOpenHelper.CM_MARK + "as Mark"},
                "SC." + sqLiteOpenHelper.CM_STUDENT_ID + " = ?",
                new String[]{studentId}, null, null, null);
        return cursor;
    }
    public void closeAllConnection() {
        if (sqLiteOpenHelper != null) {
            sqLiteOpenHelper.close();
        }
    }
}
