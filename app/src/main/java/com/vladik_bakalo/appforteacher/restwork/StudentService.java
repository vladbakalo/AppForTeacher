package com.vladik_bakalo.appforteacher.restwork;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Владислав on 20.02.2017.
 */

public interface StudentService {
    public static final String BASE_URL = "https://ddapp-sfa-api-dev.azurewebsites.net/api/test/";

    @GET("students")
    Call<List<Student>> getStudents();
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}
