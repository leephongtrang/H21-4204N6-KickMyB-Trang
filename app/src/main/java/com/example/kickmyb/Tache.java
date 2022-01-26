package com.example.kickmyb;

import java.util.Date;

public class Tache {
    public String sQueTuDoisFaire;
    public int progres;
    public Date dateDebut;
    public Date dateFinal;

    public Tache(String nom, Date dateD, Date dateF){
        sQueTuDoisFaire = nom;
        progres = 0;
        dateDebut = dateD;
        dateFinal = dateF;
    }
}
