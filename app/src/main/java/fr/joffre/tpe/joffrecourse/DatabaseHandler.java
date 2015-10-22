package fr.joffre.tpe.joffrecourse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
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
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ACTIVITY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ACTIVITY_DATE + " TEXT, " +
                    ACTIVITY_TEMPS + " REAL" +
                    ACTIVITY_DISTANCE +"REAL"+
                    ACTIVITY_VITESSE +"REAL"+
                    ACTIVITY_VITESSE_MAX +"REAL"+
                    ACTIVITY_VITESSE_MIN +"REAL"+
                    ACTIVITY_ALTITUDE_MAX +"REAL"+
                    ACTIVITY_ALTITUDE_MIN +"REAL"+
                    ACTIVITY_DENIVELE_MAX +"REAL"+
                    ACTIVITY_CALORIES + "INTEGER );";
    public static final String METIER_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(METIER_TABLE_DROP);
        onCreate(db);
    }
}
