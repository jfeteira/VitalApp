package pt.vitalaire.vitalapp;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetPrescriptionsMissing extends Service {

    private static final String SERVICE_LOG = "SERVICE_LOG";

    public GetPrescriptionsMissing() {
        super.onCreate();
        Log.i(SERVICE_LOG, "onCreate");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {


                    VitalApp app = (VitalApp)getApplication();
                    URL url = new URL("http://jfeteira.com/getMissing.aspx?strKeyDoctor=" + app.getUsername());

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    String res = "";
                    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = "";
                    while ((line = rd.readLine()) != null)
                        res += line;

                    JSONObject response = new JSONObject(res);
                    double temp = response.getJSONObject("main").getDouble("temp");

                    Log.i(SERVICE_LOG, temp + " ºC");

                    stopSelf();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Log.i(SERVICE_LOG, "Cannot get temperature.");
                }
            }
        }).start();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.i(SERVICE_LOG, "onDestroy");
    }
}
