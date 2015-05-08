package pt.vitalaire.vitalapp;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.net.URL;

/**

 */
public class GetPrescritionsMissingIntentService extends IntentService {


    private static final String INTENT_SERVICE_LOG = "INTENT_SERVICE_LOG";
    AlarmManager alarmManager ;

    public GetPrescritionsMissingIntentService()
    {
        super("GetPrescritionsMissingIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            ConnectivityManager connMgr = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);


            /*... metodo que vai buscar os dados a net*/
            if (Utils.hasConnectivityInternet(connMgr))
            {
                Utils.carregaDados(preferences.getString(PreferencesActivity.USERNAME_KEY, null), getApplicationContext());
                /* dar a notidicar que os dados foram actualizados */
                setNotification(getResources().getString(R.string.carregaDadosTitle), getResources().getString(R.string.carregaDadosMessage));
                startService(new Intent(getApplicationContext(),SetAlarmIntentService.class));

            }
            else
            {
                // dar erro que não há net
            }


        }
        catch(Exception e)
        {
                Log.i(INTENT_SERVICE_LOG, "Error get webserver data");
        }
    }

    /**
     * Helper method para afectar notificações.
     * @param title
     * @param text
     */
    private void setNotification(String title, String text)
    {
        Notification notification = new NotificationCompat.Builder(getApplicationContext()).setSmallIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha)
                .setContentTitle(title)
                .setContentText(text)
                .build();

        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1,notification);
    }
}
