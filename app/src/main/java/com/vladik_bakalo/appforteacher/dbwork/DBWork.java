package com.vladik_bakalo.appforteacher.dbwork;

import android.content.ContentValues;
import android.content.Context;
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
    public void writeStudentsToDB(List<Student> students)
    {
        for (Student student : students) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(StudentDBHelper._ID, student.getId());
            contentValues.put(StudentDBHelper.CM_BIRTHDAY, student.getBirthDay());
            contentValues.put(StudentDBHelper.CM_FIRST_NAME, student.getFirstName());
            contentValues.put(StudentDBHelper.CM_LAST_NAME, student.getLastName());

            sqLiteDatabase.insert(StudentDBHelper.TBL_NAME_STUDENT, null, contentValues);
            for (Course course :
                    student.getCourseList()) {
                sqLiteDatabase.update(StudentDBHelper.TBL_NAME_COURSE, null, "name")

            }

        }

    }
}
