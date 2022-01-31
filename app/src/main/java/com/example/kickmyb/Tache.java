package com.example.kickmyb;

import android.annotation.SuppressLint;

import java.time.LocalDateTime;
import java.util.Date;

public class Tache {
    public String sQueTuDoisFaire;
    public int progres;
    public LocalDateTime dateDebut;
    public LocalDateTime dateFinal;

    @SuppressLint("NewApi")
    public Tache(String nom, LocalDateTime dateF){
        sQueTuDoisFaire = nom;
        progres = 0;
        dateDebut = LocalDateTime.now();
        dateFinal = dateF;
    }
}
