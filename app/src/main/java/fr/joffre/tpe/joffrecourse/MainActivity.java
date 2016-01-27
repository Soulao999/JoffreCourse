package fr.joffre.tpe.joffrecourse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;




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
    private ImageView iv;
    private static TextView tv;
    private double latitude;
    private double longitude;
    private double altitude;
    private float accuracy;
    private float vitesse;
    private ArrayList alVitesse = new ArrayList();
    private float vitesseMax;
    private float vitesseMin;
    private int Time;
    private int TimeTampon;
    private String date;
    private double altitudeMax;
    private double altitudeMin;
    public static float distance = 0;
    private boolean GpsEnabled = true;
    static LocationManager lm;
    private boolean running = false;
    private boolean isFirst = true;
    private Location loc;
    public static int Calories = 0;
    final Object synchLock = new Object();
    private SharedPreferences preferences;



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
        b.setBackgroundResource(R.drawable.animbouton);
        iv = (ImageView) findViewById(R.id.imageStart);
        tv = (TextView) findViewById(R.id.Acceuil_textView);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
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
        critere.setSpeedRequired(true);
        try {
            Log.i("Localisation", lm.getBestProvider(critere, true));
        } catch (NullPointerException e) {
            Log.i("Localistion", "Gps not enabled");
        }
        String best = lm.getBestProvider(critere, false);
        lm.requestLocationUpdates(best, 10000, 1, this);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, this);
        //lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 1, this);
        Log.i("Localisation GPS", "Start localisation " + String.valueOf(lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)));
        Log.i("Localisation NETWORK", "Start localisation " + String.valueOf(lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)));
        Log.i("Localisation BEST", "Start localisation " + String.valueOf(lm.getLastKnownLocation(best)));
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
                Fonctionne f = new Fonctionne("test");
                t.start();
                f.start();
                iv.setBackgroundResource(R.drawable.messagestop);
                Calendar calendrier = Calendar.getInstance();
                int Mois = calendrier.get(Calendar.MONTH)+1;
                date = String.valueOf(calendrier.get(Calendar.DAY_OF_MONTH) + "/" + String.valueOf(Mois) + "/" + String.valueOf(calendrier.get(Calendar.YEAR)));
                b.setBackgroundResource(R.drawable.bouton);
            }
            else if(running){
                t.arret();
                running = false;
                iv.setBackgroundResource(R.drawable.messagestart);
                Time = Timer.seconde;
                //km
                distance = distance/1000;
                //km/h
                vitesseMax = (float) (vitesseMax*(3.6));
                vitesseMin = (float) (vitesseMin*(3.6));
                int i =0;
                while(i<alVitesse.size()){
                    vitesse = vitesse + (float)alVitesse.get(i);
                    i++;
                }
                vitesse = vitesse/i;
                vitesse = (float) (vitesse*(3.6));
                Calories = (int) (distance*preferences.getInt(Options.POIDS,0));
                ActivityHistoriqueBDD Bdd= new ActivityHistoriqueBDD(this);
                Bdd.open();
                ActivityHistorique ah = new ActivityHistorique(date, Time, distance, vitesse, vitesseMax, vitesseMin, (float)altitudeMax, (float)altitudeMin, Calories);
                Bdd.ajouter(ah);
                Bdd.close();
                Timer.seconde = 0;
                isFirst = true;
                b.setBackgroundResource(R.drawable.bouton);
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
            b.setBackgroundResource(R.drawable.bouton);
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        if(running){
            if(!isFirst){
                //vitesse m/s
                //altitude m
                //distance m
                     if(altitudeMax<location.getAltitude()){altitudeMax = location.getAltitude();}
                     if(altitudeMin>location.getAltitude()){altitudeMin = location.getAltitude();}
                     distance = distance + location.distanceTo(loc);
                     alVitesse.add(location.getSpeed());
                     if(vitesseMax<location.getSpeed()){vitesseMax = location.getSpeed();}
                     if(vitesseMin>location.getSpeed()){vitesseMin = location.getSpeed();}
                TimeTampon = Timer.seconde;
                loc = location;

            }
            else if(isFirst){
                    vitesseMax = location.getSpeed();
                    vitesseMin = location.getSpeed();
                    altitudeMax = location.getAltitude();
                    altitudeMin = location.getAltitude();
                loc = location;
                isFirst = false;
            }
            altitude = location.getAltitude();
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            accuracy = location.getAccuracy();
            Log.i("Localisation", "Lat: " + latitude + " Lon: " + longitude + " Alt: " + altitude + " acc: " + accuracy);

        }
        loc = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("Localisation"," Statut changed");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("Localisation", "Enabled");
        GpsEnabled = true;
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.i("Localisation", "Disabled");
        GpsEnabled = false;
    }
    public double LatLonDistance(double lat1,double lng1, double lat2, double lng2){
        double earthRadius = 6371.0; // km (or 3958.75 miles)
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }


    public class Fonctionne extends Thread{
        public Fonctionne(String name){
            super(name);
        }
        public void run(){
            while(running){
                synchronized (synchLock){try {
                    synchLock.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
                Timer.convert(Timer.seconde);
                Calories = (int) ((distance/1000)*preferences.getInt(Options.POIDS,0));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(Timer.tim+"          Distance: "+distance/1000 + "(km)           Calories: "+Calories+"(kcal)");

                    }
                });}

            }
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


