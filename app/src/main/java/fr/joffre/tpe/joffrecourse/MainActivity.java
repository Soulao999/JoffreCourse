package fr.joffre.tpe.joffrecourse;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener, android.location.LocationListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Button b;
    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;
    private float vitesse;
    private float vitesseMax;
    private float vitesseMin;
    private int Time;
    private String date;
    private double altitudeMax;
    private double altitudeMin;
    private double deniveleMax;
    private float distance;
    private boolean GpsEnabled = true;
    static LocationManager lm;
    private boolean running = false;
    private boolean isFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        b = (Button) findViewById(R.id.startbutton);
        b.setOnClickListener(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria critere = new Criteria();
        critere.setAccuracy(Criteria.ACCURACY_FINE);
        critere.setAltitudeRequired(true);
        critere.setBearingRequired(true);
        critere.setCostAllowed(false);
        critere.setPowerRequirement(Criteria.POWER_HIGH);
        critere.setSpeedRequired(true);
        try {
            Log.i("Localisation", lm.getBestProvider(critere, true));
        } catch (NullPointerException e) {
            Log.i("Localistion", "Gps not enabled");
        }
        String best = lm.getBestProvider(critere, false);
        lm.requestLocationUpdates(best, 10000, 1, this);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, this);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1, this);
        Log.i("Localisation GPS", "Start localisation " + String.valueOf(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)));
        Log.i("Localisation NETWORK", "Start localisation " + String.valueOf(lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)));
        Log.i("Localisation BEST", "Start localisation " + String.valueOf(lm.getLastKnownLocation(best)));
        if(running){
            b.setText(R.string.Accueil_bouton_clicked);
        }
    }

    @Override
    public void onDestroy(){
       super.onDestroy();
       lm.removeUpdates(this);
    }
    @Override
    public void onClick(View v) {
        Timer t = new Timer("time");
        if(GpsEnabled == true){
            if(!running) {
                running = true;
                b.setText("GO !");
                t.start();
                Calendar calendrier = Calendar.getInstance();
                int Mois = calendrier.get(Calendar.MONTH)+1;
                date = String.valueOf(calendrier.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Mois) + "/" + String.valueOf(calendrier.get(Calendar.YEAR)));
            }
            else if(running){
                t.arret();
                Time = Timer.seconde;
                //km
                distance = distance/1000;
                //km/h
                vitesseMax = (float) (vitesseMax*(3.6));
                vitesseMin = (float) (vitesseMin*(3.6));
                vitesse = (float) (vitesse*(3.6));
                ActivityHistoriqueBDD Bdd= new ActivityHistoriqueBDD(this);
                Bdd.open();
                ActivityHistorique ah = new ActivityHistorique(date, Time, distance, vitesse, vitesseMax, vitesseMin, (float)altitudeMax, (float)altitudeMin, (float)deniveleMax, 80);
                Bdd.ajouter(ah);
                b.setText(R.string.Accueil_bouton);
                Bdd.close();
                Timer.seconde = 0;
                running = false;
            }
        }
        else{
            new AlertDialog.Builder(this)
            .setCancelable(false)
            .setIcon(R.drawable.error_image)
            .setMessage(R.string.Accueil_erreurLocalisation)
            .setTitle("Erreur de localisation")
            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .show();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        if(running){
            if(!isFirst){
                //vitesse m/s
                //altitude m
                //distance m
                if(vitesseMax<location.getSpeed()){vitesseMax = location.getSpeed();}
                if(vitesseMin>location.getSpeed()){vitesseMin = location.getSpeed();}
                if(altitudeMax<location.getAltitude()){altitudeMax = location.getAltitude();}
                if(altitudeMin>location.getAltitude()){altitudeMin = location.getAltitude();}
                if (altitude < location.getAltitude() & deniveleMax < location.getAltitude() - altitude) {deniveleMax = location.getAltitude() - altitude;}
                if (altitude > location.getAltitude()& deniveleMax < altitude - location.getAltitude()) {deniveleMax = altitude - location.getAltitude();}
                distance = distance + location.getSpeed()*Timer.seconde;
            }
            else if(isFirst){
                vitesseMax = location.getSpeed();
                vitesseMin = location.getSpeed();
                altitudeMax = location.getAltitude();
                altitudeMin = location.getAltitude();
                deniveleMax = 0;
                isFirst = false;
                b.setText(R.string.Accueil_bouton_clicked);
            }
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            altitude = location.getAltitude();
            accuracy = location.getAccuracy();
            vitesse = location.getSpeed();
            Log.i("Localisation", "Lat: " + latitude + " Lon: " + longitude + " Alt: " + altitude + " acc: " + accuracy);

        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("Localisation"," Statut changed");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("Localisation","Enabled");
        GpsEnabled = true;
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("Localisation","Disabled");
        GpsEnabled = false;
    }
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }




}
