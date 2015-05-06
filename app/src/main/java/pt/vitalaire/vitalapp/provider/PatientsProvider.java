package pt.vitalaire.vitalapp.provider;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;


/**
 * Created by Jorge.Feteira on 03/05/2015.
 */
public class PatientsProvider extends ContentProvider
{

    // provider identifier
    private static final String AUTHORITY = "android.pt.vitalaire.vitalapp.provider.patientsprovider";


    // The content: scheme identifies the URI as a content URI pointing to an Android content provider.
    public static final Uri CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY);

    // Matcher for see if the type is one element or all elements.
    private static UriMatcher URIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int PATIENTS_ID = 1;
    private static final int PATIENTS_ALL = 2;

    private static final String MIME_ALL = "vnd.android.cursor.dir/vnd.pt.vitalaire.vitalapp.provider." + PatientsContract.TABLE_PATIENTS;
    private static final String MIME_ONE = "vnd.android.cursor.item/vnd.pt.vitalaire.vitalapp.provider." + PatientsContract.TABLE_PATIENTS;

    // DB Helper instance for access to SQLite DB.
    private SQLiteOpenHelper helper;


    // dedinição da linha de criação da tabela Patient
    public static final String CREATE_TABLE_UTENTES = "CREATE TABLE IF NOT EXISTS " + PatientsContract.TABLE_PATIENTS + "(" +
            PatientsContract.ID_UITENTE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            PatientsContract.NUITENTE   + " TEXT," +
            PatientsContract.NOMEUTENTE + " TEXT," +
            PatientsContract.NRECFALTA  + " INTEGER" +  ")";

    public static final String CREATE_VIEW_UTENTES_NUMREC = "CREATE VIEW IF NOT EXISTS " + PatientsContract.VIEW_PATIENTS_MP +
            "SELECT A." + PatientsContract.ID_UITENTE + ", " +
                  " A." + PatientsContract.NUITENTE   + ", " +
                  " B.COUNT(*) " +
            "   FROM " + PatientsContract.TABLE_PATIENTS + " A, " +
                         PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING + " B" +
            "  WHERE A." + PatientsContract.ID_UITENTE + " = B."+PrescriptionsMissingContract.ID_UITENTE;

    static
    {
        URIMATCHER.addURI(AUTHORITY, PatientsContract.TABLE_PATIENTS+"/#", PATIENTS_ID);
        URIMATCHER.addURI(AUTHORITY, PatientsContract.TABLE_PATIENTS, PATIENTS_ALL);
    }

    @Override
    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri)
    {
        return URIMATCHER.match(uri) == PATIENTS_ALL ? MIME_ALL : MIME_ONE;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {

        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            long row = db.insert(PatientsContract.TABLE_PATIENTS, null, values);
           // return (row != -1) ? null : ContentUris.withAppendedId(uri, row);
            if (row == -1){
                return null;
            }
            else
            {
                return ContentUris.withAppendedId(uri, row);
            }
        }
        finally
        {
            db.close();
        }




    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db = helper.getReadableDatabase();
        return db.query(PatientsContract.TABLE_PATIENTS, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            return db.update(PatientsContract.TABLE_PATIENTS, values, selection, selectionArgs);
        }
        finally
        {
            db.close();
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            return db.delete(PatientsContract.TABLE_PATIENTS, selection, selectionArgs);
        }
        finally
        {
            db.close();
        }
    }

}


