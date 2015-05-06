package pt.vitalaire.vitalapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Jorge.Feteira on 03/05/2015.
 */
public class PatientsContract {


    // table name
    public static final String TABLE_PATIENTS = "patients";
    public static final String VIEW_PATIENTS_MP = "vpatients_mp_count" ;

    // columns names
    public static final String ID_UITENTE = "_id";
    public static final String NUITENTE   = "nutente";
    public static final String NOMEUTENTE = "nomeutente";
    public static final String NRECFALTA  = "nrecfalta";

    public static Uri CONTENT_PROVIDER = Uri.withAppendedPath(PatientsProvider.CONTENT_URI, TABLE_PATIENTS);
}
