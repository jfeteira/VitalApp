package pt.vitalaire.vitalapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import pt.vitalaire.vitalapp.model.Credentials;

/**
 * Created by Jorge.Feteira on 28/04/2015.
 */
public class VitalApp extends Application
{

    private Credentials _credentials;

    @Override
    public void onCreate()
    {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString(PreferencesActivity.USERNAME_KEY, null);
        int password    = Integer.parseInt(prefs.getString(PreferencesActivity.PASSWORD_KEY, null));

        _credentials = new Credentials(username, password);

        super.onCreate();
    }

    public Credentials getCredentials() { return _credentials; }

    public void setCredentials(Credentials credentials)
{
    _credentials = credentials;
}

    public String getUsername(){
        return _credentials.getUsername();

    }
}
