package fr.joffre.tpe.joffrecourse;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.String.*;

public class Options extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    private Button b = null;
    private EditText editTextAge = null;
    private EditText editTextPoids = null;
    private EditText editTextTaille = null;
    public static final String AGE = "AGE";
    public static final String POIDS = "POIDS";
    public static final String TAILLE = "TAILLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextPoids = (EditText) findViewById(R.id.editTextPoids);
        editTextTaille = (EditText) findViewById(R.id.editTextTaille);
        b = (Button) findViewById(R.id.buttonOptionValider);
        b.setOnClickListener(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Log.i("Preférences", String.valueOf(this));
        Log.i("préférences", String.valueOf(preferences.getAll()));
        try{
            editTextAge.setText(String.valueOf(preferences.getInt(AGE, 0)));
            editTextPoids.setText(String.valueOf(preferences.getInt(POIDS, 0)));
            editTextTaille.setText(String.valueOf(preferences.getInt(TAILLE, 0)));
            Log.i("Preferences", "Préférences chargées");
        }
        catch(Resources.NotFoundException e){
            Log.i("Preferences", "Les préférences sont introuvables");
        }
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

    @Override
    public void onClick(View v){
        try{
            if(Integer.parseInt(valueOf(editTextAge.getText())) >= 150){ Toast.makeText(this, R.string.Options_erreurAgePlus, Toast.LENGTH_LONG).show();}
            else if(Integer.parseInt(valueOf(editTextAge.getText())) <= 5){Toast.makeText(this, R.string.Options_erreurAgeMoins, Toast.LENGTH_LONG).show();}
            else if(Integer.parseInt(valueOf(editTextPoids.getText())) <= 20){ Toast.makeText(this, R.string.Options_erreurPoids, Toast.LENGTH_LONG).show();}
            else if(Integer.parseInt(valueOf(editTextTaille.getText())) <= 20){ Toast.makeText(this, R.string.Options_erreurTailleMoins, Toast.LENGTH_LONG).show();}
            else if(Integer.parseInt(valueOf(editTextTaille.getText())) >= 400){ Toast.makeText(this, R.string.Options_erreurTaillePlus, Toast.LENGTH_LONG).show();}
             else {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(AGE, Integer.parseInt(valueOf(editTextAge.getText())));
                editor.commit();
                editor.putInt(POIDS, Integer.parseInt(valueOf(editTextPoids.getText())));
                editor.commit();
                editor.putInt(TAILLE, Integer.parseInt(valueOf(editTextTaille.getText())));
                editor.commit();
                Toast.makeText(this, R.string.Options_enregistre, Toast.LENGTH_LONG).show();
                Log.i("préférences", String.valueOf(preferences.getAll()));
            }

        }
        catch (NumberFormatException e){
            Toast.makeText(this, R.string.Options_erreur, Toast.LENGTH_LONG).show();
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

    }
}


