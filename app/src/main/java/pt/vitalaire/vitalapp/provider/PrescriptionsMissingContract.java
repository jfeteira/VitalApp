package pt.vitalaire.vitalapp.provider;

import android.net.Uri;

/**
 * Created by Jorge.Feteira on 03/05/2015.
 */
public class PrescriptionsMissingContract {

    // table name
    public static final String TABLE_PRESCRIPTIONSMISSING = "prescriptionsmissing";

    // columns names
    public static final String ID_UITENTE = "_id";
    public static final String ID_KEY     = "id_key";
    public static final String TERAPIA    = "terapia";
    public static final String DT_INI     = "dt_ini";
    public static final String DT_FIM     = "dt_fim";
    public static final String NDIAS      = "ndias";
    public static final String RECOK      = "recok";


    public static Uri CONTENT_PROVIDER = Uri.withAppendedPath(PatientsProvider.CONTENT_URI, TABLE_PRESCRIPTIONSMISSING);

}
