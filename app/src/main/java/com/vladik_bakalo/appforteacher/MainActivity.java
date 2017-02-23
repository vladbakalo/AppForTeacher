package com.vladik_bakalo.appforteacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.vladik_bakalo.appforteacher.dbwork.DBWork;
import com.vladik_bakalo.appforteacher.restwork.Student;
import com.vladik_bakalo.appforteacher.restwork.StudentService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vladik_bakalo.appforteacher.restwork.StudentService.retrofit;

public class MainActivity extends AppCompatActivity {
    public static final String IS_STARTED_BEFOER = "isStartedBefore";
    StudentService apiService =
            retrofit.create(StudentService.class);
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), StudentListActivity.class);
                startActivity(intent);
            }
        });
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean(IS_STARTED_BEFOER)) {
                return;
            }
        }

        //Set progress dialog window
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloadin data... ");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        getDataFromApiAndWriteToDB();


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_STARTED_BEFOER, true);
    }

    private void getDataFromApiAndWriteToDB() {
        progressDialog.show();
        Call<List<Student>> call = apiService.getStudents();
        call.enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                //Toast.makeText(MainActivity.this, "Data downloaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                new WrittingDataToDBTask().execute(response.body());
                //Toast.makeText(MainActivity.this, response.body().size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public class WrittingDataToDBTask extends AsyncTask<List<Student>, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Writting to DB...");
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(List<Student>... lists) {
            DBWork dbWork = new DBWork(getApplicationContext());
            dbWork.deleteAllStudentData();
            dbWork.writeStudentsToDB(lists[0]);
            dbWork.closeAllConnection();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }
    }

}
