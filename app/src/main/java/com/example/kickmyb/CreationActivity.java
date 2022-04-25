package com.example.kickmyb;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import com.example.kickmyb.databinding.ActivityCreationBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;

import org.kickmyb.transfer.AddTaskRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreationActivity extends BaseActivity {
    LocalDateTime l;
    ServiceCookie service;
    EditText nom;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreationBinding binding  = ActivityCreationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        currentActivity = "Tache";

        service = RetrofitCookie.get();
        nom = binding.editTextNomTacheCreation;

        binding.calendarDateFCreation.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                l = LocalDateTime.of(year, month + 1, dayOfMonth, 00, 00);
            }
        });

        binding.btnAjoutTacheCreation.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //if (nom.toString().equals("") | nom){
                    progressDialog = ProgressDialog.show(CreationActivity.this, "", getString(R.string.Creating));
                    AddTaskRequest addTaskRequest = new AddTaskRequest();
                    addTaskRequest.deadline = Date.from(l.atZone(ZoneId.systemDefault()).toInstant());
                    addTaskRequest.name = nom.getText().toString();

                    Call<String> call = service.addOne(addTaskRequest);
                    Log.e("test",addTaskRequest.name +" " +addTaskRequest.deadline);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Intent intent = new Intent(CreationActivity.this, AccueilActivity.class);
                            startActivity(intent);
                            progressDialog.cancel();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDialog.cancel();
                        }
                    });
                }
            //}
        });
    }
}