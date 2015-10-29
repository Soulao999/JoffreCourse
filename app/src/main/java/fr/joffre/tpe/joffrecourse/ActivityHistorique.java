package fr.joffre.tpe.joffrecourse;

import android.content.Context;

import java.util.ArrayList;

public class ActivityHistorique {
    private long id;
    private String date;
    private float temps;
    private float distance;
    private float vitesse;
    private float vitesseMax;
    private float vitesseMin;
    private float altitudeMax;
    private float altitudeMin;
    private float deniveleMax;
    private int calories;

    public ActivityHistorique(String date, float temps, float distance, float vitesse, float vitesseMax, float vitesseMin, float altitudeMax, float altitudeMin, float deniveleMax, int calories) {
        this.date = date;
        this.temps = temps;
        this.distance =distance;
        this.vitesse = vitesse;
        this.vitesseMax = vitesseMax;
        this.vitesseMin = vitesseMin;
        this.altitudeMax = altitudeMax;
        this.altitudeMin = altitudeMin;
        this.deniveleMax = deniveleMax;
        this.calories = calories;

    }
    public ActivityHistorique(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public float getTemps() {
        return temps;
    }

    public void setTemps(float temps) {
        this.temps = temps;
    }
    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }
    public float getVitesseMax() {
        return vitesseMax;
    }

    public void setVitesseMax(float vitesseMax) {
        this.vitesseMax = vitesseMax;
    }
    public float getVitesseMin() {
        return vitesseMin;
    }

    public void setVitesseMin(float vitesseMin) {
        this.vitesseMin = vitesseMin;
    }
    public float getAltitudeMax() {
        return altitudeMax;
    }

    public void setAltitudeMax(float altitudeMax) {
        this.altitudeMax = altitudeMax;
    }
    public float getAltitudeMin() {
        return altitudeMin;
    }

    public void setAltitudeMin(float altitudeMin) {
        this.altitudeMin = altitudeMin;
    }
    public float getDeniveleMax() {
        return deniveleMax;
    }

    public void setDeniveleMax(float deniveleMax) {
        this.deniveleMax = deniveleMax;
    }
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String toString(){
        return "ID: "+ id +" Date: "+ date + " Temps: " + temps + " Distance: "+distance+" Vitesse: "+ vitesse +
                " VitesseMax: "+vitesseMax+" VitesseMin: "+vitesseMin+" AltitudeMax: "+altitudeMax +" AltitudeMin: "+ altitudeMin+
                " DéniveléMax: "+deniveleMax+" Calories: "+calories;
    }
    public static ArrayList<ActivityHistorique> getList(Context context) {
        ArrayList<ActivityHistorique> listPers = new ArrayList<ActivityHistorique>();

        ActivityHistoriqueBDD Bdd = new ActivityHistoriqueBDD(context);
        Bdd.open();
        for(int i = 0;i<Bdd.getLenght();i++){
            listPers.add(Bdd.getActivity(i+1));
        }
        Bdd.close();
        return listPers;
    }
}
