package com.vladik_bakalo.appforteacher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.vladik_bakalo.appforteacher.dummy.StudentContent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentListActivity extends AppCompatActivity implements StudentFragment.OnListFragmentInteractionListener {

    @BindView(R.id.filter_icon)
    ImageView filterIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        ButterKnife.bind(this);
    }

    @Override
    public void onListFragmentInteraction(StudentContent.DummyItem item) {
        Toast.makeText(this, item.toString(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.filter_icon)
    public void onClick() {

    }
}
