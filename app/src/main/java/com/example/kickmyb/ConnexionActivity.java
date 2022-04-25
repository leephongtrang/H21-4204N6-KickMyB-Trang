package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
    ProgressDialog progressDialog;

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
                progressDialog = ProgressDialog.show(ConnexionActivity.this, "", getString(R.string.Connecting));
                binding.textInputLayoutInscriptionPassword.setError(null);
                binding.textInputLayoutInscriptionUsername.setError(null);
                if (!userName.getText().toString().isEmpty() || !passWord.getText().toString().isEmpty()){
                    s.password = passWord.getText().toString();
                    s.username = userName.getText().toString();

                    Call<SigninResponse> c = service.singIn(s);
                    c.enqueue(new Callback<SigninResponse>() {
                        @Override
                        public void onResponse(Call<SigninResponse> call, Response<SigninResponse> response) {
                            if (response.isSuccessful()){
                                Intent intent = new Intent(ConnexionActivity.this, AccueilActivity.class);
                                Singleton.getInstance("").username = response.body().username;
                                startActivity(intent);
                                progressDialog.cancel();
                            }
                            else{
                                try {
                                    String temp = response.errorBody().string();
                                    Log.e("error", "onResponse: " + temp);
                                    if (temp.equals("\"InternalAuthenticationServiceException\"")){
                                        binding.textInputLayoutInscriptionPassword.setError(getString(R.string.SomethingWrong));
                                        binding.textInputLayoutInscriptionUsername.setError(getString(R.string.SomethingWrong));
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                progressDialog.cancel();
                            }
                        }
                        @Override
                        public void onFailure(Call<SigninResponse> call, Throwable t) {
                            progressDialog.cancel();
                        }
                    });
                }
                else{
                    if (userName.getText().toString().isEmpty()){
                        binding.textInputLayoutInscriptionUsername.setError(getString(R.string.UsernameNull));
                    } else { binding.textInputLayoutInscriptionUsername.setError(null);}
                    if (passWord.getText().toString().isEmpty()){
                        binding.textInputLayoutInscriptionPassword.setError(getString(R.string.PasswordNull));
                    } else { binding.textInputLayoutInscriptionPassword.setError(null);}
                    progressDialog.cancel();
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
}