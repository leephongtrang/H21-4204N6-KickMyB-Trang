package com.example.kickmyb;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kickmyb.databinding.ActivityMainBinding;

import java.time.LocalDateTime;

public class AccueilActivity extends BaseActivity {
    ListeAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("Accueil");
        currentActivity = "Acceuil";

        initRecycler();
        item200();

        //Toast.makeText(this,Singleton.getInstance("dfsd").username ,Toast.LENGTH_LONG).show();

        binding.btnAjoutTacheAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, CreationActivity.class);
                startActivity(intent);
            }
        });
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