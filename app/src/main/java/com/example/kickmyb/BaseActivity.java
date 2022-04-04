package com.example.kickmyb;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.kickmyb.databinding.ActivityBaseBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {
    ActivityBaseBinding binding;
    String currentActivity; // Évite la double ouverture d'une activité
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    ServiceCookie service = RetrofitCookie.get();

    @SuppressLint("RestrictedApi")
    @Override
    public void setContentView(View view){
        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        binding.frameLayout.addView(view);
        super.setContentView(binding.drawerLayout);

        //region setupToolBar
        //https://github.com/codepath/android_guides/wiki/Fragment-Navigation-Drawer
        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        drawerLayout.addDrawerListener(drawerToggle);
        //endregion

        setPseudo(Singleton.getInstance("username").username);

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
                        Call<String> c = service.signout();
                        c.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Intent intent = new Intent(BaseActivity.this, ConnexionActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
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

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

}