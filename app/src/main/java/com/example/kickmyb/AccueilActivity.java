package com.example.kickmyb;

import androidx.annotation.MainThread;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kickmyb.databinding.ActivityMainBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.GregorianCalendar;

public class AccueilActivity extends AppCompatActivity {
    ListeAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("Accueil");

        initRecycler();
        item200();

        binding.btnAjoutTacheAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, CreationActivity.class);
                startActivity(intent);
            }
        });

        NavigationView navigationView = findViewById(R.id.drawer_layout);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void item200(){
        Tache tache;
        LocalDateTime date = LocalDateTime.of(2023, 12, 25, 14,00);
        for (int i = 0; i < 200; i++){
            tache = new Tache("Item" + i, date);
            adapter.list.add(tache);
        }
        adapter.notifyDataSetChanged();
    }

    private void initRecycler(){
        RecyclerView recyclerView = findViewById(R.id.recyclerview_lst);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        adapter = new ListeAdapter();
        recyclerView.setAdapter(adapter);
    }
}