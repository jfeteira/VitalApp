package pt.vitalaire.vitalapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import pt.vitalaire.vitalapp.provider.PrescriptionsMissingContract;
import pt.vitalaire.vitalapp.provider.PrescriptionsMissingProvider;

/**
 * Created by Jorge.Feteira on 06/05/2015.
 */
public class DadosRecitasFaltasActivity extends Activity {

    private Context _context;

    public static final String PATIENT_ID      = "PATIENT_ID";
    public static final String PATIENT_NAME    = "PATIENT_NAME";
    public static final String PATIENT_NUTENTE = "PATIENT_NUTENTE";

    private int ID_PATIENT = 0;

    private CursorAdapter _adapter;
    private static final String LOG_TAG = "LOG_TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dadosrecitasfaltas_layout);

        ID_PATIENT = Integer.parseInt(getIntent().getStringExtra(PATIENT_ID));

        ((TextView)findViewById(R.id.lbNumUtente)).setText(getIntent().getStringExtra(PATIENT_NUTENTE));
        ((TextView)findViewById(R.id.lbNomeUtente)).setText(getIntent().getStringExtra(PATIENT_NAME));

        //*... AsyncTask para ir buscar os dados
        new getDataListAsyncTask().execute();

    }

    /*==============================================================================================
* AsyncTask para ir buscar os dados as tabelas da Base de dados
================================================================================================ */
    private class getDataListAsyncTask extends AsyncTask<Void, Void, Cursor>
    {
        @Override
        protected Cursor doInBackground(Void... params)
        {

            String xSelect =  PrescriptionsMissingContract.ID_UITENTE + " = "+ ID_PATIENT;
            Cursor cursor = getContentResolver().query(PrescriptionsMissingProvider.CONTENT_URI, null, xSelect, null, null);
            startManagingCursor(cursor);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
              if(_adapter == null)
            {
                _adapter = new MissingPrescriptionsAdapter(cursor);
                final ListView lsListaRecfalta = (ListView)findViewById(R.id.lstViewrecFalta);
                lsListaRecfalta.setAdapter(_adapter);
            }
            else
            {
                // Stop using the old version of the temperatures in the cursor.
                stopManagingCursor(_adapter.getCursor());
                _adapter.changeCursor(cursor);

            }
        }
    }

    private class MissingPrescriptionsAdapter extends CursorAdapter
    {
        /**
         *
         * @pt Construtor de Adapter que recebe o cursor com a informação a colocar na listagem de receitas em falta
         */
        public MissingPrescriptionsAdapter(Cursor cursor)
        {
            super(DadosRecitasFaltasActivity.this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        @Override
        public boolean isEnabled(int position)
        {
            return true;
        }

        @Override
        public void bindView(View v, final Context ctx, final Cursor cursor)
        {


            ((TextView) v.findViewById(R.id.lbIdRec))
                    .setText(cursor.getString(cursor.getColumnIndex(PrescriptionsMissingContract.ID_KEY)));

            ((TextView) v.findViewById(R.id.lbTerapia))
                    .setText(cursor.getString(cursor.getColumnIndex(PrescriptionsMissingContract.TERAPIA)));

            ((TextView) v.findViewById(R.id.lbDt_INI))
                    .setText(cursor.getString(cursor.getColumnIndex(PrescriptionsMissingContract.DT_INI)));

            ((TextView) v.findViewById(R.id.lbDt_FIM))
                    .setText(cursor.getString(cursor.getColumnIndex(PrescriptionsMissingContract.DT_FIM)));

            ((TextView) v.findViewById(R.id.lbMissingDay))
                    .setText(cursor.getString(cursor.getColumnIndex(PrescriptionsMissingContract.NDIAS)));
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recfaltadetail, parent, false);
            bindView(view, context, cursor);

            return view;


        }


    }

}