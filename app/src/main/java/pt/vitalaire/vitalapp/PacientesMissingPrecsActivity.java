package pt.vitalaire.vitalapp;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import pt.vitalaire.vitalapp.provider.PatientsContract;
import pt.vitalaire.vitalapp.provider.PatientsProvider;

/**
 * Created by Jorge.Feteira on 04/05/2015.
 */
public class PacientesMissingPrecsActivity extends ListActivity {

    private CursorAdapter _adapter;
    private static final String LOG_TAG = "LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new getDataListAsyncTask().execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intentToOpenDadosRecitasFaltasActivity = new Intent(getApplicationContext(), DadosRecitasFaltasActivity.class);

        intentToOpenDadosRecitasFaltasActivity.putExtra(DadosRecitasFaltasActivity.PATIENT_ID, ((TextView)v.findViewById(R.id.lbIdPaciente)).getText());
        intentToOpenDadosRecitasFaltasActivity.putExtra(DadosRecitasFaltasActivity.PATIENT_NAME, ((TextView)v.findViewById(R.id.lbNomeUtente)).getText());
        intentToOpenDadosRecitasFaltasActivity.putExtra(DadosRecitasFaltasActivity.PATIENT_NUTENTE, ((TextView)v.findViewById(R.id.lbNumUtente)).getText());

        startActivity(intentToOpenDadosRecitasFaltasActivity);

    }


    /*==============================================================================================
    * AsyncTask para ir buscar os dados as tabelas da Base de dados
    ================================================================================================ */
    private class getDataListAsyncTask extends AsyncTask<Void, Void, Cursor>
    {
        @Override
        protected Cursor doInBackground(Void... params)
        {

            Cursor cursor = getContentResolver().query(PatientsProvider.CONTENT_URI, null, null, null, null);
            startManagingCursor(cursor);
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            /**
             * @pt Se foi a primeira chamada, criar o adapter com os dados do cursor.
             */


            _adapter = new PatientsAdapter(cursor);
            PacientesMissingPrecsActivity.this.setListAdapter(_adapter);


            /*
            if(_adapter == null)
            {
                _adapter = new PatientsAdapter(cursor);
                // Set adapter in the list.
                PacientesMissingPrecsActivity.this.setListAdapter(_adapter);
            }

            else
            {
                // Stop using the old version of the temperatures in the cursor.
                stopManagingCursor(_adapter.getCursor());
                _adapter.changeCursor(cursor);
            }
            */
        }
    }

    private class PatientsAdapter extends CursorAdapter
    {
        /**
         * Constructor to build Adapter with given cursor of temperatures.
         * @param cursor
         *
         * @pt Construtor de Adapter que recebe o cursor com a informação a colocar na listagem de temperaturas.
         */
        public PatientsAdapter(Cursor cursor)
        {
            super(PacientesMissingPrecsActivity.this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
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
            ((TextView) v.findViewById(R.id.lbIdPaciente))
                    .setText(cursor.getString(cursor.getColumnIndex(PatientsContract.ID_UITENTE)));

            ((TextView) v.findViewById(R.id.lbNomeUtente))
                    .setText(cursor.getString(cursor.getColumnIndex(PatientsContract.NOMEUTENTE)));

            ((TextView) v.findViewById(R.id.lbNumUtente))
                    .setText(cursor.getString(cursor.getColumnIndex(PatientsContract.NUITENTE)));

            ((TextView) v.findViewById(R.id.lbNumReceitasFalta))
                    .setText(cursor.getString(cursor.getColumnIndex(PatientsContract.NRECFALTA)));


        }



        @Override
        public View newView(Context ctx, Cursor cursor, ViewGroup vg)
        {


            return getLayoutInflater().inflate(R.layout.list_utentes_prescriptionsmissing, null);
        }
    }

}
