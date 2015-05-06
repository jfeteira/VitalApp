package pt.vitalaire.vitalapp.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import pt.vitalaire.vitalapp.model.Patient;

/**
 * Created by Jorge.Feteira on 03/05/2015.
 */
public class PatientsManager {

    private Context _context;

    public PatientsManager(Context context)
    {
        _context = context;
    }

    /* Metodo para Gravar dados na Tabela de Pacientes     */
    public static long save(Patient patient, Context _context)
    {
        ContentValues values = new ContentValues();

        values.put(PatientsContract.NUITENTE, patient.getNumUtente());
        values.put(PatientsContract.NOMEUTENTE, patient.getNomeUtente());
        values.put(PatientsContract.NRECFALTA, patient.getNrecfalta());

        Uri xIdTmp =  _context.getContentResolver().insert(PatientsProvider.CONTENT_URI, values);
        return ContentUris.parseId(xIdTmp);
    }

    public static void limpaPacientesTodas(Context _context){
        _context.getContentResolver().delete(PatientsProvider.CONTENT_URI,null, null);
    }

    public static void dadosPacientesLista(Context _context){

    }

}
