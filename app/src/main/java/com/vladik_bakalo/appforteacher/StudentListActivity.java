package com.vladik_bakalo.appforteacher;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.vladik_bakalo.appforteacher.dbwork.DBWork;
import com.vladik_bakalo.appforteacher.dummy.StudentContent;
import com.vladik_bakalo.appforteacher.restwork.Course;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentListActivity extends AppCompatActivity implements StudentFragment.OnListFragmentInteractionListener, View.OnClickListener {


    ImageView filterIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        //
        filterIcon = (ImageView)findViewById(R.id.filter_icon);
        filterIcon.setOnClickListener(this);
    }

    @Override
    public void onListFragmentInteraction(StudentContent.DummyItem item) {
        DBWork dbWork = new DBWork(getApplicationContext());
        Cursor courses = dbWork.getCursorOfCoursesByStudentId(item.id);
        List<Course> list = Course.getListCourses(courses);
        AlertDialog dialog = DialogScreen.getDialogCourses(this, list);
        dialog.show();
        dbWork.closeAllConnection();
    }

    @Override
    public void onClick(View view) {
        DBWork dbWork = new DBWork(getApplicationContext());
        Cursor coursesName = dbWork.getCursorOfCoursesName();
        AlertDialog dialog = DialogScreen.getDialogFilter(this, coursesName);
        dialog.show();
        dbWork.closeAllConnection();
    }
}
