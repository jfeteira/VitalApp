package pt.vitalaire.vitalapp.provider;

import android.content.ContentValues;
import android.content.Context;
import pt.vitalaire.vitalapp.model.PrescriptionsMissing;

/**
 * Created by Jorge.Feteira on 03/05/2015.
 */
public class PrescriptionsMissingManager {
    private Context _context;

    public PrescriptionsMissingManager(Context context)
    {
        _context = context;
    }

    /* Metodo para Gravar dados na Tabela de Pacientes     */
    public static void save(PrescriptionsMissing prescriptionsmissing, Context _context)
    {
        ContentValues values = new ContentValues();

        values.put(PrescriptionsMissingContract.ID_UITENTE, prescriptionsmissing.getIdUtente());
        values.put(PrescriptionsMissingContract.ID_KEY    , prescriptionsmissing.getIdKey());
        values.put(PrescriptionsMissingContract.TERAPIA   , prescriptionsmissing.getTerapia());
        values.put(PrescriptionsMissingContract.DT_INI    , prescriptionsmissing.getIni());
        values.put(PrescriptionsMissingContract.DT_FIM    , prescriptionsmissing.getEnd());
        values.put(PrescriptionsMissingContract.NDIAS     , prescriptionsmissing.getMissingDays());
        values.put(PrescriptionsMissingContract.RECOK     , prescriptionsmissing.getRecok());
        _context.getContentResolver().insert(PrescriptionsMissingProvider.CONTENT_URI, values);
    }


    public static void limpaRecitasTodas(Context _context){
        _context.getContentResolver().delete(PrescriptionsMissingProvider.CONTENT_URI, null, null);
    }

}
