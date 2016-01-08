package fr.joffre.tpe.joffrecourse;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Historique extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, ActiviteAdapter.ActiviteAdapterListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private TextView Temps;
    private TextView Distance;
    private TextView Vitesse;
    private TextView VitesseMax;
    private TextView VitesseMin;
    private TextView AltitudeMax;
    private TextView AltitudeMin;
    private TextView Denivele;
    private TextView Calories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        //Récupération de la liste des personnes
        ArrayList<ActivityHistorique> listP = ActivityHistorique.getList(this);

        //Création et initialisation de l'Adapter pour les personnes
        ActiviteAdapter adapter = new ActiviteAdapter(this, listP);

        //Ecoute des évènements sur votre liste
        adapter.addListener(this);

        //Récupération du composant ListView
        ListView list = (ListView)findViewById(R.id.ListView);

        //Initialisation de la liste avec les données
        list.setAdapter(adapter);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
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
            ActivityHistoriqueBDD Bdd= new ActivityHistoriqueBDD(this);
            Bdd.open();
            for(int i =0;i<Bdd.getLenght();i++){
            Bdd.supprimer(i);
            }
            Bdd.close();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickActivite(ActivityHistorique item, int position) {
        Log.i("info", String.valueOf(item.getTemps()));
        Dialog box = new Dialog(this);
        box.setContentView(R.layout.histobox);
        Temps = (TextView) box.findViewById(R.id.histoboxTemps);
        Distance = (TextView) box.findViewById(R.id.histoboxDistance);
        Vitesse = (TextView) box.findViewById(R.id.vitesse);
        VitesseMax = (TextView) box.findViewById(R.id.vitesseMax);
        VitesseMin = (TextView) box.findViewById(R.id.vitesseMin);
        AltitudeMax = (TextView) box.findViewById(R.id.altitudeMax);
        AltitudeMin = (TextView) box.findViewById(R.id.altitudeMin);
        Denivele = (TextView) box.findViewById(R.id.Denivele);
        Calories = (TextView) box.findViewById(R.id.Calories);
        int i[] = Timer.convert((int)item.getTemps());
        Temps.setText(i[0]+"h "+i[1]+"min "+i[2]+"sec");
        Distance.setText(String.valueOf(item.getDistance()));
        Vitesse.setText(String.valueOf(item.getVitesse()));
        VitesseMax.setText(String.valueOf(item.getVitesseMax()));
        VitesseMin.setText(String.valueOf(item.getVitesseMin()));
        AltitudeMax.setText(String.valueOf(item.getAltitudeMax()));
        AltitudeMin.setText(String.valueOf(item.getAltitudeMin()));
        Denivele.setText(String.valueOf(item.getDeniveleMax()));
        Calories.setText(String.valueOf(item.getCalories()));
        box.setTitle(item.getDate());
        box.show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
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

    }

}

