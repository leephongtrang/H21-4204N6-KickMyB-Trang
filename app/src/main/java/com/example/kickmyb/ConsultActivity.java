package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kickmyb.databinding.ActivityConsultBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;

import org.kickmyb.transfer.HomeItemResponse;
import org.kickmyb.transfer.TaskDetailResponse;

import java.security.Provider;
import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultActivity extends BaseActivity {
    int pourcentage;
    TextView avancement;
    ServiceCookie service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityConsultBinding binding = ActivityConsultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        currentActivity = "Consult";

        service = RetrofitCookie.get();

        Long id = getIntent().getLongExtra("id", 0);

        TaskDetailResponse taskDetailResponse;

        Call<TaskDetailResponse> call = service.detail(id); //requête detail
        call.enqueue(new Callback<TaskDetailResponse>() {
            @Override
            public void onResponse(Call<TaskDetailResponse> call, Response<TaskDetailResponse> response) {

            }

            @Override
            public void onFailure(Call<TaskDetailResponse> call, Throwable t) {

            }
        });


        pourcentage = 15;

        TextView textNom = findViewById(R.id.textView_nomTache);
        textNom.setText(getIntent().getStringExtra("tQ"));

        avancement = findViewById(R.id.editText_pourcentage_consult);
        avancement.setText(pourcentage + "%");

        TextView textView = findViewById(R.id.textView_temps_consult);
        textView.setText("50 jours ");

        binding.btnMoinsConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pourcentage > 0){
                    pourcentage--;
                    avancement.setText(pourcentage + "%");
                }
            }
        });

        binding.btnPlusConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pourcentage < 100) {
                    pourcentage++;
                    avancement.setText(pourcentage + "%");
                }
            }
        });
    }
}