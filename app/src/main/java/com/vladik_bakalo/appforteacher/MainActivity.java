package com.vladik_bakalo.appforteacher;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

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

//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    URL oracle = new URL("https://ddapp-sfa-api-dev.azurewebsites.net/api/test/students");
//                    BufferedReader in = null;
//                    in = new BufferedReader(
//                            new InputStreamReader(oracle.openStream()));
//                    String inputLine;
//                    while ((inputLine = in.readLine()) != null)
//                        System.out.println(inputLine);
//                    in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    parse(getApplicationContext());
//                } catch (XmlPullParserException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                String str = "dfsdf";
//            }
//        });


        //
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                Serializer serializer = new Persister();
//
//                File source = doWork();
//
//                try {
//                    ResponseData example = serializer.read(ResponseData.class, source);
//                    Toast.makeText(MainActivity.this, "Yess!", Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });


    }

    public void parse(Context ctx) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser xpp = factory.newPullParser();

        InputStream in = new FileInputStream(doWork());

        xpp.setInput(in, "UTF-8");

        int eventType = xpp.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = xpp.getName();

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    Log.d("debug", "Entering tag: " + tagName);

                    break;
                case XmlPullParser.TEXT:
                    Log.d("debug", "Text inside: " + xpp.getText());

                    break;
                case XmlPullParser.END_TAG:
                    Log.d("debug", "Ending tag: " + tagName);

                    break;

            }

            eventType = xpp.next();
        }

    }

    public File doWork() {

        InputStreamReader inputStream = null;

        OutputStreamWriter outputStream = null;
        File myFile = null;
        //
        HttpURLConnection conn = null;

        try {
            File filesDir = getApplicationContext().getFilesDir();
            myFile = new File(filesDir, "holder-new.xml");
            // read this file into InputStream
            URL url = new URL("https://ddapp-sfa-api-dev.azurewebsites.net/api/test/students");
            //inputStream = new URL("https://ddapp-sfa-api-dev.azurewebsites.net/api/test/students").openStream();
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            conn.setUseCaches(true);
            conn.addRequestProperty("Content-Type", "text/xml; charset=utf-8");
            inputStream = new InputStreamReader(new BOMInputStream(conn.getInputStream(), ByteOrderMark.UTF_8));
            // write the inputStream to a FileOutputStream
            outputStream = new OutputStreamWriter(new FileOutputStream(myFile));
            //write header
//            String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
//
//            outputStream.write(str.getBytes());

            //
            int read = 0;
            char[] bytes = new char[1024];

            while ((read = inputStream.read()) != -1) {
                outputStream.write(read);
                Log.i("---------1-1-1--1-1", bytes.toString());
            }

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return myFile;
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
