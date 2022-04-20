package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kickmyb.databinding.ActivityConnexionBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnexionActivity extends AppCompatActivity {
    SigninRequest s = new SigninRequest();
    ActivityConnexionBinding binding;
    ServiceCookie service;
    EditText userName;
    EditText passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConnexionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        service = RetrofitCookie.get();

        setTitle("Connexion");

        userName = binding.editTextUsernameConnexion;
        passWord = binding.editTextMdpConnexion;

        //region bindingBtn
        binding.btnConnexionConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userName.getText().toString().isEmpty() || !passWord.getText().toString().isEmpty()){
                    s.password = passWord.getText().toString();
                    s.username = userName.getText().toString();

                    Call<SigninResponse> c = service.singIn(s);
                    c.enqueue(new Callback<SigninResponse>() {
                        @Override
                        public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                            if (response.isSuccessful()){
                                Intent intent = new Intent(ConnexionActivity.this, AccueilActivity.class);
                                Singleton.getInstance(response.body().username);
                                startActivity(intent);
                            }
                            else{
                                try {
                                    String temp = response.errorBody().string();
                                    Log.e("error", "onResponse: " + temp);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<SigninResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        binding.btnInscriptionConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConnexionActivity.this, InscriptionActivity.class);
                startActivity(intent);
            }
        });
        //endregion
    }

    private boolean validatonInfo(String USNA, String MDPD, String MDPC){
        if(USNA.isEmpty() || MDPD.isEmpty() || MDPC.isEmpty()){
            if (USNA.isEmpty()){//Si le username est Null
                binding.textInputLayoutInscriptionUsername.setError(getString(R.string.UsernameNull));
            }else { binding.textInputLayoutInscriptionUsername.setError(null);}
            if (MDPD.isEmpty()){//Si le mot de passe est Null
                binding.textInputLayoutInscriptionPassword.setError(getString(R.string.PasswordNull));
            } else { binding.textInputLayoutInscriptionPassword.setError(null);}
            if (MDPC.isEmpty()){//Si la confirmation de mot de passe est Null
                binding.textInputLayoutInscriptionPasswordConfirm.setError(getString(R.string.PasswordConfNull));
            } else { binding.textInputLayoutInscriptionPasswordConfirm.setError(null);}
            return false;
        }
        binding.textInputLayoutInscriptionUsername.setError(null);
        binding.textInputLayoutInscriptionPassword.setError(null);
        binding.textInputLayoutInscriptionPasswordConfirm.setError(null);
        if (MDPD.equals(MDPC)){ //Confirme que les 2 mots de passe sont pareil
            return true;
        } else {
            binding.textInputLayoutInscriptionPasswordConfirm.setError(getString(R.string.PasswordDifferent));
            return false;
        }
    }
}