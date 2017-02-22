package com.vladik_bakalo.appforteacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.vladik_bakalo.appforteacher.restwork.Course;

import java.util.List;

/**
 * Created by Владислав on 21.02.2017.
 */

public class DialogScreen {

    public static final int IDD_FILTER = 1;
    public static final int IDD_COURSES = 2;

    public static AlertDialog getDialog(Activity activity, int ID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        switch(ID) {
            case IDD_FILTER:
                View viewCourse = activity.getLayoutInflater().inflate(R.layout.filters, null); // Получаем layout по его ID
                builder.setView(viewCourse);
                builder.setTitle(R.string.filters_str);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //MainActivity.doSaveSettings(); // Переход в сохранение настроек MainActivity
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // Кнопка Отмена
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(true);
                return builder.create();
            case IDD_COURSES: // Диалог настроек
                View viewSetting = activity.getLayoutInflater().inflate(R.layout.courses, null); // Получаем layout по его ID
                builder.setView(viewSetting);
                builder.setTitle(R.string.courses_str);
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //MainActivity.doSaveSettings(); // Переход в сохранение настроек MainActivity
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() { // Кнопка Отмена
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(true);
                return builder.create();
            default:
                return null;
        }
    }
    public static AlertDialog getDialogCourses(Activity activity, List<Course> courses)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        View viewCourses = activity.getLayoutInflater().inflate(R.layout.courses, null); // Получаем layout по его ID
        //Fill listview of Courses
        ListView listCourses = (ListView) viewCourses.findViewById(R.id.listCourses);
        CoursesArrayAdapter coursesArrayAdapter = new CoursesArrayAdapter(activity, courses);
        listCourses.setAdapter(coursesArrayAdapter);
        //Set AVG Mark
        TextView viewMarkAVG = (TextView) viewCourses.findViewById(R.id.coursesMarkAVG);
        double AVG = Course.getMarkAVG(courses);
        viewMarkAVG.setText("Average mark : " + String.valueOf(AVG));
        //
        builder.setView(viewCourses);
        builder.setTitle(R.string.courses_str);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
            public void onClick(DialogInterface dialog, int whichButton) {
                //MainActivity.doSaveSettings(); // Переход в сохранение настроек MainActivity
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        return builder.create();
    }
}