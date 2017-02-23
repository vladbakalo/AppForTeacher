package com.vladik_bakalo.appforteacher;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.vladik_bakalo.appforteacher.dbwork.DBWork;
import com.vladik_bakalo.appforteacher.dummy.StudentContent;
import com.vladik_bakalo.appforteacher.restwork.Course;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentListActivity extends AppCompatActivity implements StudentFragment.OnListFragmentInteractionListener, View.OnClickListener, DialogScreen.OnAlertDialogFilterInteractionListener {

    StudentFragment myRecyclerViewFragment;
    ImageView filterIcon;
    AlertDialog alertDialogFilter;
    final DialogScreen dialogScreen = new DialogScreen();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        //
        filterIcon = (ImageView)findViewById(R.id.filter_icon);
        myRecyclerViewFragment = (StudentFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentStudentList);
        filterIcon.setOnClickListener(this);
    }

    @Override
    public void onListFragmentInteraction(StudentContent.DummyItem item) {
        DBWork dbWork = new DBWork(getApplicationContext());
        Cursor courses = dbWork.getCursorOfCoursesByStudentId(item.id);
        List<Course> list = Course.getListCourses(courses);
        AlertDialog dialog = dialogScreen.getDialogCourses(this, list);
        dialog.show();
        dbWork.closeAllConnection();
    }

    @Override
    public void onClick(View view) {
        DBWork dbWork = new DBWork(getApplicationContext());
        Cursor coursesName = dbWork.getCursorOfCoursesName();
        alertDialogFilter = dialogScreen.getDialogFilter(this, coursesName);
        alertDialogFilter.show();
        dbWork.closeAllConnection();
    }

    @Override
    public void onAlertDialogFilterClear() {
        Toast.makeText(this, "Filter cleared", Toast.LENGTH_SHORT).show();
        DBWork dbWork = new DBWork(getApplicationContext());
        Cursor coursesName = dbWork.getCursorOfAllStudents();
        myRecyclerViewFragment.changeData(coursesName);
        dbWork.closeAllConnection();

    }

    @Override
    public void onAlertDialogDoFilter() {
        Spinner spinnerCourses = (Spinner) alertDialogFilter.findViewById(R.id.spinnerCourses);
        EditText editMark = (EditText) alertDialogFilter.findViewById(R.id.markCourse);
        long id = spinnerCourses.getSelectedItemId();
        Cursor course = (Cursor)spinnerCourses.getSelectedItem();
        Integer mark;
        if (!editMark.getText().toString().equals(""))
        {
            mark = Integer.parseInt(editMark.getText().toString());
            if (mark > 4)
            {
                Toast.makeText(this, "Mark has to be less then 5", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Filter by : " + course.getString(1) + " and " + editMark.getText().toString(), Toast.LENGTH_SHORT).show();
                DBWork dbWork = new DBWork(getApplicationContext());
                Cursor newData = dbWork.getStudentsByFilter(mark, (int)id);
                myRecyclerViewFragment.changeData(newData);
                dbWork.closeAllConnection();

            }
        }
        else
        {
            Toast.makeText(this, "Mark field is empty", Toast.LENGTH_SHORT).show();
        }


        course.close();
    }
}
