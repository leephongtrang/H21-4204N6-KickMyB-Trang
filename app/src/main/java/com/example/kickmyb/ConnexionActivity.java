package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kickmyb.databinding.ActivityConnexionBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;

import org.kickmyb.transfer.SigninRequest;
import org.kickmyb.transfer.SigninResponse;
import org.kickmyb.transfer.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnexionActivity extends AppCompatActivity {
    SigninRequest s = new SigninRequest();
    ServiceCookie service;
    EditText userName;
    EditText passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityConnexionBinding binding = ActivityConnexionBinding.inflate(getLayoutInflater());
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
                            Intent intent = new Intent(ConnexionActivity.this, AccueilActivity.class);
                            Singleton.getInstance(response.body().username);
                            startActivity(intent);
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
}