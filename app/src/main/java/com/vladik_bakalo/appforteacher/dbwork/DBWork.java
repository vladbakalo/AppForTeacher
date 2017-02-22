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
import java.util.ArrayList;
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
        List<BufferCourse> existCourses = new ArrayList<>();
        for (Student student : students) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(Long.parseLong(student.getBirthDay()));
            ContentValues contentValues = new ContentValues();
            contentValues.put(StudentDBHelper._ID, student.getId());
            contentValues.put(StudentDBHelper.CM_BIRTHDAY, dateFormat.format(date));
            contentValues.put(StudentDBHelper.CM_FIRST_NAME, student.getFirstName());
            contentValues.put(StudentDBHelper.CM_LAST_NAME, student.getLastName());

            sqLiteDatabase.insert(StudentDBHelper.TBL_NAME_STUDENT, null, contentValues);
            for (Course course :
                    student.getCourseList()) {
                //Check for exist Course
                long courseId = 0;
//                Cursor cursor = sqLiteDatabase.query(StudentDBHelper.TBL_NAME_COURSE,
//                        new String[]{StudentDBHelper._ID}, StudentDBHelper.CM_COURSE_NAME + " = ? ",
//                        new String[]{course.getCourseName()}, null, null, null);
//                if (cursor.moveToFirst()) {
//                    courseId = cursor.getLong(0);
//                }
//                cursor.close();
                if(!existCourses.contains(new BufferCourse(0, course.getCourseName())))
                {
                    ContentValues contentValuesForCourse = new ContentValues();
                    contentValuesForCourse.put(StudentDBHelper.CM_COURSE_NAME, course.getCourseName());
                    courseId = sqLiteDatabase.insert(StudentDBHelper.TBL_NAME_COURSE, null, contentValuesForCourse);
                    existCourses.add(new BufferCourse((int)courseId, course.getCourseName()));
                }
                else
                {
                    for (BufferCourse buffCourse :
                            existCourses) {
                        if (buffCourse.getName().equals(course.getCourseName()))
                        {
                            courseId = buffCourse.getId();
                            break;
                        }
                    }
                }
                //CourseId for adding to StudentAndCourse table
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
    public  Cursor getStudentsByFilter(Integer mark, String courseName)
    {
        String table = sqLiteOpenHelper.TBL_NAME_STUDENTandCOURSE
                + " as SC inner join "
                + sqLiteOpenHelper.TBL_NAME_COURSE
                + " as CO on SC."
                + sqLiteOpenHelper.CM_COURSE_ID + " = CO." + sqLiteOpenHelper._ID
                + " inner join " + sqLiteOpenHelper.TBL_NAME_STUDENT
                + " as ST on SC." + sqLiteOpenHelper.CM_STUDENT_ID + " = ST."
                + sqLiteOpenHelper.CM_STUDENT_ID;

        Cursor cursor = sqLiteDatabase.query(table,
                new String[]{
                        "ST." + sqLiteOpenHelper._ID + " as st_id",
                        "ST." + sqLiteOpenHelper.CM_FIRST_NAME + " as st_name",
                        "ST." + sqLiteOpenHelper.CM_LAST_NAME + " as st_last",
                        "ST." + sqLiteOpenHelper.CM_BIRTHDAY + " as st_birth"}, null, null, null, null, null);
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
                "SC." + sqLiteOpenHelper.CM_MARK + " as Mark"},
                "SC." + sqLiteOpenHelper.CM_STUDENT_ID + " = ?",
                new String[]{studentId}, null, null, null);
        return cursor;
    }
    public void closeAllConnection() {
        if (sqLiteOpenHelper != null) {
            sqLiteOpenHelper.close();
        }
    }
    private class BufferCourse{
        int id;
        String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BufferCourse(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object obj) {
            return ((BufferCourse)obj).getName().equals(this.getName())?true:false;
        }
    }
}
