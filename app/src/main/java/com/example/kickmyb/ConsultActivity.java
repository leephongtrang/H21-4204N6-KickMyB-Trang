package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kickmyb.databinding.ActivityConsultBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;
import org.kickmyb.transfer.HomeItemResponse;
import org.kickmyb.transfer.TaskDetailResponse;

import java.io.IOException;
import java.security.Provider;
import java.text.DateFormat;
import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsultActivity extends BaseActivity {
    int pourcentage;
    TextView avancement;
    ServiceCookie service;
    TaskDetailResponse taskDetailResponse;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityConsultBinding binding = ActivityConsultBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        service = RetrofitCookie.get();
        currentActivity = "Consult";

        ProgressDialog progressDialog = ProgressDialog.show(ConsultActivity.this, "", getString(R.string.Loading));

        //region link TextView avec les id
        TextView textNom = findViewById(R.id.textView_nomTache);
        TextView echeance = findViewById(R.id.textView_dateEchance_consult);
        TextView jPasse = findViewById(R.id.textView_temps_consult);
        avancement = findViewById(R.id.editText_pourcentage_consult);
        //endregion

        DateFormat df = DateFormat.getDateInstance();
        int jPasseInt = getIntent().getIntExtra("jPasse", 0);

        long id = getIntent().getLongExtra("id", 0);


        Call<TaskDetailResponse> call = service.detail(id); //requête detail
        call.enqueue(new Callback<TaskDetailResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<TaskDetailResponse> call, Response<TaskDetailResponse> response) {
                if (response.isSuccessful()){
                    taskDetailResponse = response.body();
                    pourcentage = taskDetailResponse.percentageDone;
                    textNom.setText(taskDetailResponse.name);
                    jPasse.setText(jPasseInt + getString(R.string.Days));
                    avancement.setText(pourcentage + "%");
                    echeance.setText(getString(R.string.deadline) + " : " + df.format(taskDetailResponse.deadline));
                    progressDialog.cancel();
                }else {
                    try {
                        String temp = response.errorBody().string();//https://stackoverflow.com/questions/32519618/retrofit-2-0-how-to-get-deserialised-error-response-body
                        JSONObject jsonObject = new JSONObject(temp);
                        String temp2 = jsonObject.getString("error");

                        if (temp.equals("\"InternalAuthenticationServiceException\"") || temp2.equals("Forbidden")){
                            errorAuth();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    progressDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<TaskDetailResponse> call, Throwable t) {
                Log.e("test", "fail");
                progressDialog.cancel();
                errorConnexion();
            }
        });

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

        binding.btnConfimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = ProgressDialog.show(ConsultActivity.this, "", getString(R.string.Updating));
                Call<String> call1 = service.updateProgress(id, pourcentage);
                call1.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Intent i = new Intent(ConsultActivity.this, AccueilActivity.class);
                        startActivity(i);
                        progressDialog.cancel();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.cancel();
                    }
                });
            }
        });

        binding.btnAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConsultActivity.this, AccueilActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), getString(R.string.consult_conf), Toast.LENGTH_SHORT).show();
    }
}