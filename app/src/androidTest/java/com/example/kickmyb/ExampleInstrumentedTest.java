package com.example.kickmyb;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kickmyb.transfer.SignupRequest;

import java.io.IOException;

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
        Call<String> call = service.signUp(s);
        Response<String> response = call.execute();
    }

    @Test
    public void signInPostTest(){ //Connexion

    }
}