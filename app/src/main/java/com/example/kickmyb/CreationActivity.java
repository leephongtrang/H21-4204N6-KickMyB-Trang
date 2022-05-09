package com.example.kickmyb;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kickmyb.databinding.ActivityCreationBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;

import org.kickmyb.transfer.AddTaskRequest;

import java.io.IOException;
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
                if (nom.getText().toString().isEmpty()){
                    binding.textInputLayoutTaskName.setError(getString(R.string.TaskNameEmpty));
                }
                else if (nom.getText().toString().trim().length() <= 1){
                    binding.textInputLayoutTaskName.setError(getString(R.string.TaskNameTooShort));
                }
                else { binding.textInputLayoutTaskName.setError(null);}
                if (l == null){
                    Toast t = Toast.makeText(getApplicationContext(), R.string.SelectDate, Toast.LENGTH_SHORT);
                    t.show();
                }
                else if (l.isBefore(LocalDateTime.now())){
                    Toast t = Toast.makeText(getApplicationContext(), R.string.SelectDateFutur, Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    progressDialog = ProgressDialog.show(CreationActivity.this, "", getString(R.string.Creating));
                    AddTaskRequest addTaskRequest = new AddTaskRequest();
                    addTaskRequest.deadline = Date.from(l.atZone(ZoneId.systemDefault()).toInstant());
                    addTaskRequest.name = nom.getText().toString();

                    Call<String> call = service.addOne(addTaskRequest);
                    Log.e("test",addTaskRequest.name +" " +addTaskRequest.deadline);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()){
                                Intent intent = new Intent(CreationActivity.this, AccueilActivity.class);
                                startActivity(intent);
                                progressDialog.cancel();
                            }
                            else{
                                try {
                                    String temp = response.errorBody().string();
                                    Log.e("error", temp);
                                    if (temp.equals("\"Forbidden\"")){
                                        progressDialog.cancel();
                                        Log.e("errorCatch", "");
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                progressDialog.cancel();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            progressDialog.cancel();
                            errorConnexion();
                        }
                    });
                    progressDialog.cancel();
                }
            }
        });
    }
}