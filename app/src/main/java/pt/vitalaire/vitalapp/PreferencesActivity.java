package pt.vitalaire.vitalapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import pt.vitalaire.vitalapp.model.Credentials;

/**
 * Created by Jorge.Feteira on 28/04/2015.
 */

public class PreferencesActivity extends PreferenceActivity
{
    public static final String USERNAME_KEY = "USERNAME_KEY";
    public static final String PASSWORD_KEY = "PASSWORD_KEY";
    public static final String PREFSYNCFREQUENCY_KEY = "PREFSYNCFREQUENCY_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }

    @Override
    protected void onPause()
    {
/*
        VitalApp app = (VitalApp)getApplication();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String username = prefs.getString(USERNAME_KEY, null);
        int password    = Integer.parseInt(prefs.getString(PASSWORD_KEY, null));

        app.setCredentials(new Credentials(username, password));
        */
        super.onPause();

    }
}
