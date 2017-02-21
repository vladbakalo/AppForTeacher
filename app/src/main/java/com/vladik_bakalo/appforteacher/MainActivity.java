package com.vladik_bakalo.appforteacher;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.vladik_bakalo.appforteacher.dbwork.DBWork;
import com.vladik_bakalo.appforteacher.restwork.Student;
import com.vladik_bakalo.appforteacher.restwork.StudentService;

import org.apache.commons.io.ByteOrderMark;
import org.apache.commons.io.input.BOMInputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vladik_bakalo.appforteacher.restwork.StudentService.retrofit;

public class MainActivity extends AppCompatActivity {

    StudentService apiService =
            retrofit.create(StudentService.class);
    @BindView(R.id.startButton)
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Call<List<Student>> call = apiService.getStudents();
                call.enqueue(new Callback<List<Student>>() {
                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                        Toast.makeText(MainActivity.this, "Yess", Toast.LENGTH_SHORT).show();
                        DBWork dbWork = new DBWork(getApplicationContext());
                        //dbWork.writeStudentsToDB(response.body());
                        Cursor cursor = dbWork.getCursorOfAllStudents();
                        cursor.close();
                        dbWork.closeAllConnection();
                        //Toast.makeText(MainActivity.this, response.body().size(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<Student>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Noo...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @OnClick(R.id.startButton)
    public void onClick() {
        Intent intent = new Intent(this, StudentListActivity.class);
        startActivity(intent);
    }
}
