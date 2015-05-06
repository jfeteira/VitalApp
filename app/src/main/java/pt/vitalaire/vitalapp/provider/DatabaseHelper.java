package pt.vitalaire.vitalapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Formando FLAG on 04-05-2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context)
    {
        super(context, "vitaldb.sql", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(PatientsProvider.CREATE_TABLE_UTENTES );
        sqLiteDatabase.execSQL(PrescriptionsMissingProvider.CREATE_TABLE_RECEITAS );
       // sqLiteDatabase.execSQL(PatientsProvider.CREATE_VIEW_UTENTES_NUMREC );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PatientsContract.TABLE_PATIENTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PrescriptionsMissingContract.TABLE_PRESCRIPTIONSMISSING);
        //sqLiteDatabase.execSQL("DROP VIEW  IF EXISTS " + PatientsContract.VIEW_PATIENTS_MP);
        onCreate(sqLiteDatabase);
    }
}
