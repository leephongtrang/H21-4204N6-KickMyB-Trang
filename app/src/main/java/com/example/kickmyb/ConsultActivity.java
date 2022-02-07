package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kickmyb.databinding.ActivityConsultBinding;

import java.time.LocalDateTime;

public class ConsultActivity extends BaseActivity {
    int pourcentage;
    TextView avancement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityConsultBinding binding = ActivityConsultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Long tDD = getIntent().getLongExtra("tDD", 0);
        Long tDF = getIntent().getLongExtra("tDF", 0);

        pourcentage = 15;

        TextView textNom = findViewById(R.id.textView_nomTache);
        textNom.setText(getIntent().getStringExtra("tQ"));

        avancement = findViewById(R.id.editText_pourcentage_consult);
        avancement.setText(pourcentage + "%");

        TextView textView = findViewById(R.id.textView_temps_consult);
        textView.setText("50 jours du calandrier des bananiers.");

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