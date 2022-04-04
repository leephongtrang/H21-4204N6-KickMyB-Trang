package com.example.kickmyb.http;

import org.kickmyb.CustomGson;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitCookie {

    private static ServiceCookie instance;

    public static ServiceCookie get(){
        if (instance == null) { //  ca sera le cas au tout premier appel
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(CustomGson.getIt()))
                    .client(client())
                    //.baseUrl("https://kickmyb-server.herokuapp.com/")
                    //.baseUrl("http://10.0.2.2:8080/")
                    .baseUrl("https://trang-leephong-server.herokuapp.com/")
                    .build();

            instance = retrofit.create(ServiceCookie.class);
            return instance;
        } else {
            return instance;
        }
    }

    private static OkHttpClient client() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        CookieJar jar = new SessionCookieJar();
        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(jar)
                .addInterceptor(interceptor)
                .build();
        return client;

    }

}
