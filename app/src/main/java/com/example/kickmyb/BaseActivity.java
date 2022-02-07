package com.example.kickmyb;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kickmyb.databinding.ActivityBaseBinding;
import com.google.android.material.navigation.NavigationView;

public class BaseActivity extends AppCompatActivity {
    ActivityBaseBinding binding;
    String currentActivity; // Évite la double ouverture d'une activité

    @Override
    public void setContentView(View view){
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        binding.frameLayout.addView(view);
        super.setContentView(binding.drawerLayout);
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
                }
                return false;
            }
        });
    }
}