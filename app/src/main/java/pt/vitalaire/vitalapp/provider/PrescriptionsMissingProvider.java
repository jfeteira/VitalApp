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
public class PrescriptionsMissingProvider extends ContentProvider
{
    // provider identifier
    private static final String AUTHORITY = "android.pt.vitalaire.vitalapp.provider.prescriptionsmissingprovider";


    // The content: scheme identifies the URI as a content URI pointing to an Android content provider.
    public static final Uri CONTENT_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTHORITY);

    // Matcher for see if the type is one element or all elements.
    private static UriMatcher URIMATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int PRESCRIPTIONSMISSING_ID  = 1;
    private static final int PRESCRIPTIONSMISSING_ALL = 2;

    private static final String MIME_ALL = "vnd.android.cursor.dir/vnd.pt.vitalaire.vitalapp.provider." + PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING;
    private static final String MIME_ONE = "vnd.android.cursor.item/vnd.pt.vitalaire.vitalapp.provider." + PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING;

    // DB Helper instance for access to SQLite DB.
    private SQLiteOpenHelper helper;

    // dedinição da linha de criação da tabela Receitas em Falta
    public static final String CREATE_TABLE_RECEITAS = "CREATE TABLE IF NOT EXISTS " + PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING + "(" +
            PrescriptionsMissingContract.ID_UITENTE + " INTEGER," +
            PrescriptionsMissingContract.ID_KEY     + " TEXT," +
            PrescriptionsMissingContract.TERAPIA    + " TEXT," +
            PrescriptionsMissingContract.DT_INI     + " TEXT," +
            PrescriptionsMissingContract.DT_FIM     + " TEXT," +
            PrescriptionsMissingContract.NDIAS      + " INTEGER," +
            PrescriptionsMissingContract.RECOK      + " INTEGER DEFAULT 0" +  ")";

    static
    {
        URIMATCHER.addURI(AUTHORITY, PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING+"/#", PRESCRIPTIONSMISSING_ID);
        URIMATCHER.addURI(AUTHORITY, PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING, PRESCRIPTIONSMISSING_ALL);
    }

    public boolean onCreate() {
        helper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri)
    {
        return URIMATCHER.match(uri) == PRESCRIPTIONSMISSING_ALL ? MIME_ALL : MIME_ONE;
    }



    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            long row = db.insert(PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING , null, values);
            //return (row != -1) ? null : ContentUris.withAppendedId(uri, row);
            return (row == -1) ? null : ContentUris.withAppendedId(uri, row);
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
        return db.query(PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING , projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs)
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        try
        {
            return db.update(PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING , values, selection, selectionArgs);
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
            return db.delete(PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING , selection, selectionArgs);
        }
        finally
        {
            db.close();
        }
    }






}
