package com.vladik_bakalo.appforteacher;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.vladik_bakalo.appforteacher.dbwork.StudentDBHelper;
import com.vladik_bakalo.appforteacher.dummy.StudentContent;
import com.vladik_bakalo.appforteacher.restwork.Course;

import java.util.List;

/**
 * Created by Владислав on 21.02.2017.
 */

public class DialogScreen {

    OnAlertDialogFilterInteractionListener dListner;
    public interface OnAlertDialogFilterInteractionListener
    {
        void onAlertDialogFilterClear();
        void onAlertDialogDoFilter();
    }
    public AlertDialog getDialogFilter(Activity activity, Cursor courses) {
        if (activity instanceof StudentFragment.OnListFragmentInteractionListener) {
            dListner = (OnAlertDialogFilterInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnAlertDialogFilterInteractionListener");
        }
        //
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        View viewFilter = activity.getLayoutInflater().inflate(R.layout.filters, null); // Получаем layout по его ID
        Spinner spinner = (Spinner) viewFilter.findViewById(R.id.spinnerCourses);
        fillSpinerByCourses(spinner, courses, activity);
        builder.setView(viewFilter);
        builder.setTitle(R.string.filters_str);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() { // Кнопка ОК
            public void onClick(DialogInterface dialog, int whichButton) {
                //MainActivity.doSaveSettings(); // Переход в сохранение настроек MainActivity
                dListner.onAlertDialogDoFilter();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.clear_str, new DialogInterface.OnClickListener() { // Кнопка Отмена
            public void onClick(DialogInterface dialog, int which) {
                dListner.onAlertDialogFilterClear();
                dialog.dismiss();
            }
        });
        builder.setCancelable(true);
        AlertDialog window = builder.create();
        window.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        return window;


    }
    public static void fillSpinerByCourses(Spinner spinnerCourses, Cursor courses, Context context)
    {
        String[] colums = new String[]{StudentDBHelper.CM_COURSE_NAME};
        int[] toViews = new int[]{android.R.id.text1};
        SimpleCursorAdapter simpleCursorAdapter =
                new SimpleCursorAdapter(context, android.R.layout.simple_spinner_item, courses, colums, toViews, 0);
        simpleCursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCourses.setAdapter(simpleCursorAdapter);
    }
    public AlertDialog getDialogCourses(Activity activity, List<Course> courses) {
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