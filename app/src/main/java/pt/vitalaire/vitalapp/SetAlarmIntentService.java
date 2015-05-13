package pt.vitalaire.vitalapp;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SetAlarmIntentService extends IntentService {

    AlarmManager alarmManager ;
    private static final String LOG_TAG = "LOG_TAG";


    public SetAlarmIntentService() {
        super("SetAlarmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        try {
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            Log.i(LOG_TAG, preferences.getString(PreferencesActivity.PREFSYNCFREQUENCY_KEY, "86400000"));

            Intent i = new Intent(getApplicationContext(), GetPrescritionsMissingIntentService.class);
            PendingIntent pi = PendingIntent.getService(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            Long xTempoMilis = System.currentTimeMillis() + Long.parseLong(preferences.getString(PreferencesActivity.PREFSYNCFREQUENCY_KEY, "86400000"));

            System.out.println("tempo em milis: " + xTempoMilis);

            /*=======================================================================================
            * para fazer o set do Alarm independente da API que estamos a usar
            =========================================================================================*/
            setAlarmBasedOnAPILevel(AlarmManager.RTC_WAKEUP, xTempoMilis, pi, alarmManager);
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }

    }

    private void setAlarmBasedOnAPILevel(int rtcWakeup, long timeToNotify, PendingIntent contentIntent,AlarmManager am){
        int version = android.os.Build.VERSION.SDK_INT;
        if(version>=19)
            am.setExact(rtcWakeup, timeToNotify, contentIntent);
        else
            am.set(rtcWakeup, timeToNotify, contentIntent);
    }


}
