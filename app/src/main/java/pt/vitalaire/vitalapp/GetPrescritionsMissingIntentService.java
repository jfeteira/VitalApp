package pt.vitalaire.vitalapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.net.URL;

/**

 */
public class GetPrescritionsMissingIntentService extends IntentService {


    private static final String INTENT_SERVICE_LOG = "INTENT_SERVICE_LOG";


    public GetPrescritionsMissingIntentService()
    {
        super("GetPrescritionsMissingIntentService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            VitalApp app = (VitalApp)getApplication();
            URL url = new URL("http://www.jfeteira.com/getmissing.aspx?strKeyDoctor=" + app.getUsername());


        }
        catch(Exception e)
        {
                Log.i(INTENT_SERVICE_LOG, "Error get webserver data");
        }
    }




}
