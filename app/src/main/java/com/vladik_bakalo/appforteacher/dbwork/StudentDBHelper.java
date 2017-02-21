package com.vladik_bakalo.appforteacher.dbwork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Владислав on 20.02.2017.
 */

public class StudentDBHelper extends SQLiteOpenHelper implements BaseColumns {
    public static final String DB_NAME = "students_db";
    public static final int DB_VERSION = 1;
    //Tables
    public static final String TBL_NAME_STUDENT = "tbl_Student";
    public static final String TBL_NAME_COURSE = "tbl_Course";
    public static final String TBL_NAME_STUDENTandCOURSE = "tbl_StudentandCourse";
    //Columns
    //STUDENT table
    public static final String CM_FIRST_NAME = "FIRST_NAME";
    public static final String CM_BIRTHDAY = "BIRTHDAY";
    public static final String CM_LAST_NAME = "LAST_NAME";
    //COURSE table
    public static final String CM_COURSE_NAME = "COURSE_NAME";
    //STUDENTandCOURSE table
    public static final String CM_COURSE_ID = "COURSE_ID";
    public static final String CM_STUDENT_ID = "STUDENT_ID";
    public static final String CM_MARK = "MARK";

    public StudentDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TBL_NAME_STUDENT + "("
                + _ID + " TEXT PRIMARY KEY, "
                + CM_FIRST_NAME + " TEXT, "
                + CM_LAST_NAME + " TEXT, "
                + CM_BIRTHDAY  + " INTEGER);");
        sqLiteDatabase.execSQL("CREATE TABLE " + TBL_NAME_COURSE + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CM_COURSE_NAME + " TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE " + TBL_NAME_STUDENTandCOURSE + "("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CM_COURSE_ID + " INTEGER, "
                + CM_STUDENT_ID + " TEXT, "
                + CM_MARK + " REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
