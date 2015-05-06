package pt.vitalaire.vitalapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by JFA on 04-05-2015.
 *
 *    Para conter todos os metodos que correm mais que uma vez
 *
 */
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import pt.vitalaire.vitalapp.model.Patient;
import pt.vitalaire.vitalapp.model.PrescriptionsMissing;
import pt.vitalaire.vitalapp.provider.PatientsManager;
import pt.vitalaire.vitalapp.provider.PrescriptionsMissingManager;
import pt.vitalaire.vitalapp.provider.PrescriptionsMissingProvider;

public class Utils {

    private static final String LOG_TAG = "LOG_TAG";

    public static void carregaDados(String xMedico, Context xContext)
    {
        try
        {

            //*... vou buscar o cód de médico que está nas preferences
            URL url = new URL("http://www.jfeteira.com/getMissing.aspx?strKeyDoctor=" + xMedico);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            String res = "";
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = rd.readLine()) != null)
                res += line;

            JSONObject response = new JSONObject(res);
            JSONArray xDados = response.getJSONArray("MissingPrescriptions");

            //*... agora vou limpar os dados das tabelas da base de dados
            PatientsManager.limpaPacientesTodas(xContext);
            PrescriptionsMissingManager.limpaRecitasTodas(xContext);

            //*... caregar os dados que vem do servidor WEB
            for(int i=0; i < xDados.length(); i++)
            {
                Patient xPaciente = new Patient();
                JSONObject temp =(JSONObject) xDados.get(i);

                //*... dados do pacientes
                xPaciente.setNumUtente(temp.getString("NUTENTE"));
                xPaciente.setNomeUtente(temp.getString("NOMEUTENTE"));
                xPaciente.setNrecfalta(temp.getInt("NRECFALTA"));

                long xIdPaciente = PatientsManager.save(xPaciente, xContext);

                Log.i(LOG_TAG, "........" + xPaciente.getNumUtente() + " --- " + xPaciente.getNomeUtente() + " ID...>" + xIdPaciente );

                PrescriptionsMissing xPrescriptionsMissing = new PrescriptionsMissing();

                xPrescriptionsMissing.setIdUtente(xIdPaciente);

                //*... receitas em atraso
                JSONArray xReceitas = temp.getJSONArray("missing");
                for(int k=0; k < xReceitas.length(); k++)
                {
                    JSONObject xRecTemp =(JSONObject) xReceitas.get(k);
                    xPrescriptionsMissing.setIdKey(xRecTemp.getInt("ID_KEY"));
                    xPrescriptionsMissing.setTerapia(xRecTemp.getString("TERAPIA"));
                    xPrescriptionsMissing.setIni(xRecTemp.getString("DTINI"));
                    xPrescriptionsMissing.setEnd(xRecTemp.getString("DTFIM"));
                    xPrescriptionsMissing.setMissingDays(xRecTemp.getInt("NDIAS"));
                    xPrescriptionsMissing.setRecok(0);

                    PrescriptionsMissingManager.save(xPrescriptionsMissing, xContext);

                    Log.i(LOG_TAG, "\n............" + xRecTemp.getString("ID_KEY") );

                }




            }



        }
        catch(Exception e)
        {
            Log.i(LOG_TAG, "Já fostes ........\n" + e.getMessage());
            e.printStackTrace();
        }
    }


}
