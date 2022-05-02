package com.example.kickmyb;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kickmyb.transfer.AddTaskRequest;
import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import java.io.IOException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.kickmyb", appContext.getPackageName());
    }

    @Test
    public void signUpPostTest() throws IOException { //Inscription
        ServiceCookie service = RetrofitCookie.get();
        SignupRequest s = new SignupRequest() {{username = "lee"; password = "123456789";}};
        Call<SigninResponse> call = service.signUp(s);
        Response<SigninResponse> response = call.execute();
        Log.e("popo", response.body().username);
    }

    @Test
    public void signInPostTest() throws IOException { //Connexion
        ServiceCookie serviceCookie = RetrofitCookie.get();
        SigninRequest s = new SigninRequest() {{username = "lee"; password = "123456789";}};
        Call<SigninResponse> call = serviceCookie.singIn(s);
        Response<SigninResponse> r = call.execute();
        Log.e("popi", r.body().username);
    }
}