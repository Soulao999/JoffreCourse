package fr.joffre.tpe.joffrecourse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class ActivityHistoriqueBDD {
    private static final int VERSION_BDD = 1;
    public static final String ACTIVITY_KEY = "id";
    public static final String ACTIVITY_DATE = "Date";
    public static final String ACTIVITY_TEMPS = "Temps";
    public static final String ACTIVITY_DISTANCE = "Distance";
    public static final String ACTIVITY_VITESSE = "VitesseMoyenne";
    public static final String ACTIVITY_VITESSE_MAX = "VitesseMax";
    public static final String ACTIVITY_VITESSE_MIN = "VitesseMin";
    public static final String ACTIVITY_ALTITUDE_MAX = "AltitudeMax";
    public static final String ACTIVITY_ALTITUDE_MIN = "AltitudeMin";
    public static final String ACTIVITY_DENIVELE_MAX = "DéniveléMax";
    public static final String ACTIVITY_CALORIES = "Calories";

    public static final String TABLE_NAME = "Activity";
    private SQLiteDatabase bdd;
    private static int key = 0;

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
        bdd.delete(TABLE_NAME, ACTIVITY_KEY + " = ?", new String[]{String.valueOf(id)});
    }
    public void open(){
        //on ouvre la BDD en écriture
        bdd = Database.getWritableDatabase();
        String request = "SELECT * FROM " + TABLE_NAME;
        Cursor c = bdd.rawQuery(request, null);
        String str = String.valueOf(c.getCount());
        Log.i("BDDNombre d'éléments", str);
        key = c.getCount();

    }
    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }
    public ActivityHistorique getActivity(long id){
        String request = "SELECT * FROM " + TABLE_NAME +" WHERE "+ACTIVITY_KEY +"=?";
        Cursor c = bdd.rawQuery(request, new String[]{String.valueOf(id)});
        if(c.getCount() == 0) return null;
        c.moveToFirst();
        ActivityHistorique ah = new ActivityHistorique();
        ah.setId(c.getInt(0));
        ah.setDate(c.getString(1));
        ah.setTemps(c.getFloat(2));
        ah.setDistance(c.getFloat(3));
        ah.setVitesse(c.getFloat(4));
        ah.setVitesseMax(c.getFloat(5));
        ah.setVitesseMin(c.getFloat(6));
        ah.setAltitudeMax(c.getFloat(7));
        ah.setAltitudeMin(c.getFloat(8));
        ah.setDeniveleMax(c.getFloat(9));
        ah.setCalories(c.getInt(10));
        Log.i("BDD", c.getString(1));
        c.close();
        return ah;
    }
    public ActivityHistoriqueBDD(Context context) {
        //On créer la BDD et sa table
        Database = new DatabaseHandler(context, TABLE_NAME, null, VERSION_BDD);
        //Si bug
        //context.deleteDatabase(TABLE_NAME);
    }

}
