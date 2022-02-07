package com.example.kickmyb;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;

import com.example.kickmyb.databinding.ActivityCreationBinding;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class CreationActivity extends BaseActivity {
    LocalDateTime l;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCreationBinding binding  = ActivityCreationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        currentActivity = "Tache";

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
                EditText n = binding.editTextNomTacheCreation;
                Tache t = new Tache(n.getText().toString(), l);

                Intent intent = new Intent(CreationActivity.this, AccueilActivity.class);
                startActivity(intent);
            }
        });
    }
}