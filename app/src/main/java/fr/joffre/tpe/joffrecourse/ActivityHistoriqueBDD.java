package fr.joffre.tpe.joffrecourse;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ActivityHistoriqueBDD {
    private static final int VERSION_BDD = 1;
    public static final String ACTIVITY_KEY = "id";
    public static final String ACTIVITY_DATE = "Date";
    public static final String ACTIVITY_TEMPS = "Temps(s)";
    public static final String ACTIVITY_DISTANCE = "Distance(km)";
    public static final String ACTIVITY_VITESSE = "Vitesse moyenne(km/h)";
    public static final String ACTIVITY_VITESSE_MAX = "Vitesse max(km/h)";
    public static final String ACTIVITY_VITESSE_MIN = "Vitesse min(km/h)";
    public static final String ACTIVITY_ALTITUDE_MAX = "Altitude max(m)";
    public static final String ACTIVITY_ALTITUDE_MIN = "Altitude min(m)";
    public static final String ACTIVITY_DENIVELE_MAX = "Dénivelé max(m)";
    public static final String ACTIVITY_CALORIES = "Calories (kcal)";

    public static final String TABLE_NAME = "Activity";
    private SQLiteDatabase bdd;

    private DatabaseHandler Database;

    public void ajouter(ActivityHistorique ah) {
        //Création d'un ContentValues
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(ACTIVITY_DATE, ah.getDate());
        values.put(ACTIVITY_TEMPS, ah.getTemps());
        values.put(ACTIVITY_DISTANCE, ah.getDistance());
        values.put(ACTIVITY_VITESSE, ah.getVitesse());
        values.put(ACTIVITY_VITESSE_MAX, ah.getVitesseMax());
        values.put(ACTIVITY_VITESSE_MIN, ah.getVitesseMin());
        values.put(ACTIVITY_ALTITUDE_MAX, ah.getTemps());
        values.put(ACTIVITY_ALTITUDE_MIN, ah.getAltitudeMin());
        values.put(ACTIVITY_DENIVELE_MAX, ah.getDeniveleMax());
        values.put(ACTIVITY_CALORIES, ah.getCalories());
        //on insère l'objet dans la BDD via le ContentValues
        bdd.insert(TABLE_NAME, null, values);
    }
    public void supprimer(long id) {
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = Database.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public ActivityHistoriqueBDD(Context context) {
        //On créer la BDD et sa table
        Database = new DatabaseHandler(context, TABLE_NAME, null, VERSION_BDD);
    }

}
