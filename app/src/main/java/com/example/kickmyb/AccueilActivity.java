package com.example.kickmyb;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kickmyb.databinding.ActivityMainBinding;
import com.example.kickmyb.http.RetrofitCookie;
import com.example.kickmyb.http.ServiceCookie;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.kickmyb.transfer.HomeItemResponse;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kotlin.text.UStringsKt;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccueilActivity extends BaseActivity {
    ListeAdapter adapter;
    ServiceCookie service;

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
        gettask();
        //item200();

        //Toast.makeText(this,Singleton.getInstance("dfsd").username ,Toast.LENGTH_LONG).show();



        binding.btnAjoutTacheAccueil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccueilActivity.this, CreationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void gettask(){
        service = RetrofitCookie.get();
        Call<ArrayList<HomeItemResponse>> call = service.home();
        call.enqueue(new Callback<ArrayList<HomeItemResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<HomeItemResponse>> call, Response<ArrayList<HomeItemResponse>> response) {
                Log.e("test" ,response.body().toString());
                ArrayList<HomeItemResponse> a = response.body();

                adapter.list.clear();
                /*for (int i = 0; i < a.size(); i++){
                    Tache tache = new Tache(a.get(i).name, a.get(i).percentageDone, a.get(i).percentageTimeSpent, a.get(i).deadline);
                    adapter.list.add(tache);
                }*/
                adapter.list.addAll(a);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<HomeItemResponse>> call, Throwable t) {

            }
        });
    }

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    private void item200(){
        Tache tache;
        LocalDateTime date = LocalDateTime.of(2023, 12, 25, 14,00);
        for (int i = 0; i < 200; i++){
            tache = new Tache("Item" + i, date);
            adapter.list.add(tache);
        }
        adapter.notifyDataSetChanged();
    }*/

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