package com.example.kickmyb;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kickmyb.databinding.ActivityBaseBinding;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity {
    ActivityBaseBinding binding;
    String pseudo;
    String currentActivity; // Évite la double ouverture d'une activité

    @SuppressLint("RestrictedApi")
    @Override
    public void setContentView(View view){
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        binding.frameLayout.addView(view);
        super.setContentView(binding.drawerLayout);

        pseudo = "ByMkciK";
        setPseudo(pseudo);

        binding.navigationBar.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case (R.id.menu_acceuil):
                        if (!currentActivity.equals("Acceuil")){
                            intent = new Intent(BaseActivity.this, AccueilActivity.class);
                            startActivity(intent);
                        }
                        break;

                    case (R.id.menu_tache):
                        if (!currentActivity.equals("Tache")){
                            intent = new Intent(BaseActivity.this, CreationActivity.class);
                            startActivity(intent);
                        }
                        break;

                    case (R.id.menu_deconnexion):
                        intent = new Intent(BaseActivity.this, ConnexionActivity.class);
                        startActivity(intent);
                    break;
                }
                return false;
            }
        });
    }

    public void setPseudo(String n){
        NavigationView navigationView = findViewById(R.id.navigationBar);
        View hv = navigationView.getHeaderView(0);
        TextView t = (TextView) hv.findViewById(R.id.nav_utilisateur);
        t.setText(n);
    }

    public void setActionBar(){
        NavigationView navigationView = findViewById(R.id.na)
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }
}