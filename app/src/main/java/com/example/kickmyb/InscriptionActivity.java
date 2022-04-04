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
    ActivityInscriptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInscriptionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        service = RetrofitCookie.get();

        setTitle("Inscription");

        username = binding.editTextUsernameInscription;
        password = binding.editTextMdpInscription;
        confPass = binding.editTextMdpConfirmInscription;
        //error = binding.textViewErreur;

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
        if(USNA.isEmpty() || MDPD.isEmpty() || MDPC.isEmpty()){
            if (USNA.isEmpty()){//Si le username est Null
                binding.textInputLayoutInscriptionUsername.setError("Le nom d'utilisateur est vide.");
            }else { binding.textInputLayoutInscriptionUsername.setError(null);}
            if (MDPD.isEmpty()){//Si le mot de passe est Null
                binding.textInputLayoutInscriptionPassword.setError("Veuillez entrer un mot de passe.");
            } else { binding.textInputLayoutInscriptionPassword.setError(null);}
            if (MDPC.isEmpty()){//Si la confirmation de mot de passe est Null
                binding.textInputLayoutInscriptionPasswordConfirm.setError("Veuillez confirmer votre mot de passe.");
            } else { binding.textInputLayoutInscriptionPasswordConfirm.setError(null);}
            return false;
        }
        binding.textInputLayoutInscriptionUsername.setError(null);
        binding.textInputLayoutInscriptionPassword.setError(null);
        binding.textInputLayoutInscriptionPasswordConfirm.setError(null);
        if (MDPD.equals(MDPC)){ //Confirme que les 2 mots de passe sont pareil
            return true;
        } else {
            binding.textInputLayoutInscriptionPasswordConfirm.setError("Les mots de passe sont différents.");
            return false;
        }
    }
}