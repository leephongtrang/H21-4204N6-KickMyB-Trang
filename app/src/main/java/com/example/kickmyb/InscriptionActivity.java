package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.kickmyb.databinding.ActivityConnexionBinding;
import com.example.kickmyb.databinding.ActivityInscriptionBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;

import org.kickmyb.transfer.SignupRequest;

public class InscriptionActivity extends AppCompatActivity {
    SignupRequest s;
    EditText username;
    EditText password;
    EditText confPass;
    ServiceCookie service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityInscriptionBinding binding = ActivityInscriptionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        service = RetrofitCookie.get();

        setTitle("Inscription");

        username = binding.editTextUsernameInscription;
        password = binding.editTextMdpInscription;
        confPass = binding.editTextMdpConfirmInscription;

        binding.btnInscriptionInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s.username = username.getText().toString();
                s.password = password.getText().toString();
                if (s.password.equals(confPass.getText().toString())){
                    service.signUp(s);
                }

                Intent intent = new Intent(InscriptionActivity.this, AccueilActivity.class);
                startActivity(intent);
            }
        });
    }
}