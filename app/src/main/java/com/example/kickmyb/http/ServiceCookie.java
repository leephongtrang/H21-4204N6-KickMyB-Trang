package com.example.kickmyb.http;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ServiceCookie {

    @GET("exos/cookie/echo")
    Call<String> cookieEcho();

    @POST("api/id/signup")
    Call<String> signUp(SignupRequest s);

    @POST("api/id/")
    Call<SigninResponse> singIn(SigninRequest signInR);
}
