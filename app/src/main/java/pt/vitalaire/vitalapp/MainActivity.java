package pt.vitalaire.vitalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageButton;

import java.net.URL;

public class MainActivity extends ActionBarActivity {

    private Context _context;

    private static final String LOG_TAG = "LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




     //*... Botão para ver as Receitas em Atraso
        ImageButton btnPrecrMissing = (ImageButton) findViewById(R.id.btPrescriptionMissing);
        btnPrecrMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getDataServer().execute();
            }
        });


        //*... Botão para ver as Receitas em Atraso
        ImageButton btCallPrescriptionMissing = (ImageButton) findViewById(R.id.btCallPrescriptionMissing);
        btCallPrescriptionMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PacientesMissingPrecsActivity.class));
            }
        });


    }


    private class getDataServer extends AsyncTask<String, Integer, String> {

        protected String doInBackground(String... xMedico) {
            try{
                final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                Utils.carregaDados(preferences.getString(PreferencesActivity.USERNAME_KEY, null), getApplicationContext());
            }
            catch (Exception e) {
                Log.d(LOG_TAG, e.toString());
                return null;
            }
            return null;
        }

        protected void onPostExecute() {
            //super.onPostExecute(String xReturn);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.action_prefs)
        {
            startActivity(new Intent(getApplicationContext(), PreferencesActivity.class));
            return true;
        }

        //return super.onOptionsItemSelected(item);
        return true ;
    }
}
