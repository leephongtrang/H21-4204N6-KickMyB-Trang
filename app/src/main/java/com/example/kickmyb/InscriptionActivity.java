package com.example.kickmyb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kickmyb.databinding.ActivityInscriptionBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;

import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscriptionActivity extends AppCompatActivity {
    SignupRequest s = new SignupRequest();
    EditText username;
    EditText password;
    EditText confPass;
    TextView error;
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
        error = binding.textViewErreur;

        binding.btnInscriptionInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatonInfo(username.getText().toString(), password.getText().toString(), confPass.getText().toString())){
                    s.username = username.getText().toString();
                    s.password = password.getText().toString();

                    Log.e("Tincri", s.username + " et " + s.password);

                    Call<SigninResponse> c = service.signUp(s);
                    c.enqueue(new Callback<SigninResponse>() {
                        @Override
                        public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                            String zou = response.body().username;
                            Log.i("livraison", zou);

                            Singleton.getInstance(response.body().username);

                            Intent intent = new Intent(InscriptionActivity.this, AccueilActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onFailure(Call<SigninResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }

    private boolean validatonInfo(String USNA, String MDPD, String MDPC){
        if (USNA.isEmpty()) { //Si le username est Null
            error.setText("Le nom d'utilisateur est vide.");
            return false;
        }
        if (MDPD.isEmpty()) { error.setText("Veuillez entrer un mot de passe."); return false; } //Si le mot de passe est Null
        if (MDPC.isEmpty()) { error.setText("Veuillez confirmer votre mot de passe."); return false; } //Si la confirmation de mot de passe est Null
        if (MDPD.equals(MDPC)){ //Confirme que les 2 mots de passe sont pareil
            return true;
        } else {
            error.setText("La confirmation du mot de passe à échouer.");
            return false;
        }
    }
}