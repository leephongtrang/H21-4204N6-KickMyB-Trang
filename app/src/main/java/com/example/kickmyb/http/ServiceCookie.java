package com.example.kickmyb.http;

import org.kickmyb.transfer.AddTaskRequest;
import org.kickmyb.transfer.HomeItemResponse;
import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import java.sql.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface    ServiceCookie {

    @GET("exos/cookie/echo")
    Call<String> cookieEcho();

    @POST("api/id/signup") //inscription
    Call<SigninResponse> signUp(@Body SignupRequest s);

    @POST("api/id/signin") //connection
    Call<SigninResponse> singIn(@Body SigninRequest s);

    @POST("api/id/signout") //déconn
    Call<String> signout();

    @POST("api/add") //ajout tâche
    Call<String> addOne(@Body AddTaskRequest request);

    @GET("api/home") //acceuil
    Call<ArrayList<HomeItemResponse>> home();
}
