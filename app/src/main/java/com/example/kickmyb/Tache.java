package com.example.kickmyb;

import android.annotation.SuppressLint;

import java.time.LocalDateTime;
import java.util.Date;

public class Tache {
    public String sQueTuDoisFaire;
    public int progres;
    public int tempsPerdu;
    public Date dateFinal;

    @SuppressLint("NewApi")
    public Tache(String name, int percentageDone, int percentageTimeSpent, Date deadline){
        sQueTuDoisFaire = name;
        progres = percentageDone;
        tempsPerdu = percentageTimeSpent;
        dateFinal = deadline;
    }
}
