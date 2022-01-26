package com.example.kickmyb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.kickmyb.databinding.ActivityMainBinding;

import java.util.Date;
import java.util.GregorianCalendar;

public class AccueilActivity extends AppCompatActivity {
    ListeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setTitle("Accueil");

        initRecycler();
        item200();
    }

    private void item200(){
        Tache tache;
        Date date = new Date(125, 0,26);
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