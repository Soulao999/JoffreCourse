package fr.joffre.tpe.joffrecourse;

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
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class Statistiques extends AppCompatActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private int TempsSemaine = 0;
    private int TempsMois = 0;
    private int TempsAnnee = 0;
    private int TempsAll = 0;
    private int DistanceSemaine = 0;
    private int DistanceMois = 0;
    private int DistanceAnnee = 0;
    private int DistanceAll = 0;
    private int CaloSemaine = 0;
    private int CaloMois = 0;
    private int CaloAnnee = 0;
    private int CaloAll = 0;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistiques);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        Calendar calendrier = Calendar.getInstance();
        int Mois = calendrier.get(Calendar.MONTH)+1;
        int Jour =calendrier.get(Calendar.DAY_OF_MONTH);
        int Annee = calendrier.get(Calendar.YEAR);
        ArrayList<ActivityHistorique> listP = ActivityHistorique.getList(this);
        listP.get(0);
        for(int i = 0;i<listP.size();i++){
            ActivityHistorique ah = listP.get(i);
            String[] s= ah.getDate().split("/");
            if(Annee == Integer.parseInt(s[2])
                    && Mois == Integer.parseInt(s[1])
                    && Jour-4 < Integer .parseInt(s[0])
                    && Jour+4 > Integer.parseInt(s[0])){

                TempsSemaine = TempsSemaine + (int) ah.getTemps();
                DistanceSemaine = DistanceSemaine + (int)ah.getDistance();
                CaloSemaine = CaloSemaine + ah.getCalories();
            }
            if(Annee == Integer.parseInt(s[2]) && Mois == Integer.parseInt(s[1])){
                TempsMois = TempsMois + (int) ah.getTemps();
                DistanceMois = DistanceMois + (int)ah.getDistance();
                CaloMois = CaloMois + ah.getCalories();
            }
            if(Annee == Integer.parseInt(s[2])){
                TempsAnnee = TempsAnnee + (int) ah.getTemps();
                DistanceAnnee = DistanceAnnee + (int)ah.getDistance();
                CaloAnnee = CaloAnnee + ah.getCalories();
            }
            TempsAll = TempsAll + (int) ah.getTemps();
            DistanceAll = DistanceAll + (int)ah.getDistance();
            CaloAll = CaloAll + ah.getCalories();
        }
        TextView b = (TextView) findViewById(R.id.CaloAll);
        b.setText(String.valueOf(CaloAll) + "(kcal)");
        TextView b1 = (TextView) findViewById(R.id.CaloAnnee);
        b1.setText(String.valueOf(CaloAnnee)+ "(kcal)");
        TextView b2 = (TextView) findViewById(R.id.CaloMois);
        b2.setText(String.valueOf(CaloMois)+ "(kcal)");
        TextView b3 = (TextView) findViewById(R.id.CaloSemaine);
        b3.setText(String.valueOf(CaloSemaine)+ "(kcal)");
        TextView b4 = (TextView) findViewById(R.id.TempsAll);
        b4.setText(String.valueOf(Timer.convert(TempsAll)[0]+"h"+Timer.convert(TempsAll)[1]+"min"));
        TextView b5 = (TextView) findViewById(R.id.TempsAnnee);
        b5.setText(String.valueOf(Timer.convert(TempsAnnee)[0]+"h"+Timer.convert(TempsAnnee)[1]+"min"));
        TextView b6 = (TextView) findViewById(R.id.TempsMois);
        b6.setText(String.valueOf(Timer.convert(TempsMois)[0]+"h"+Timer.convert(TempsMois)[1]+"min"));
        TextView b7 = (TextView) findViewById(R.id.TempsSemaine);
        b7.setText(String.valueOf(Timer.convert(TempsSemaine)[0]+"h"+Timer.convert(TempsSemaine)[1]+"min"));
        TextView b8 = (TextView) findViewById(R.id.DistanceAll);
        b8.setText(String.valueOf(DistanceAll)+ "(km)");
        TextView b9 = (TextView) findViewById(R.id.DistanceAnnee);
        b9.setText(String.valueOf(DistanceAnnee)+ "(km)");
        TextView b10 = (TextView) findViewById(R.id.DistanceMois);
        b10.setText(String.valueOf(DistanceMois)+ "(km)");
        TextView b11 = (TextView) findViewById(R.id.DistanceSemaine);
        b11.setText(String.valueOf(DistanceSemaine)+ "(km)");




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
            return true;
        }

        return super.onOptionsItemSelected(item);
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

