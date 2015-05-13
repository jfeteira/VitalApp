package pt.vitalaire.vitalapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
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

import pt.vitalaire.vitalapp.provider.PatientsContract;
import pt.vitalaire.vitalapp.provider.PatientsProvider;

/**
 * Created by Jorge.Feteira on 08/05/2015.
 */
public class ListaPacientesFaltaActivity extends Activity {


    private CursorAdapter _adapter;
    private static final String LOG_TAG = "LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listapacientesfalta_layout);



    }

    @Override
    protected void onResume() {
        super.onResume();

        new getDataListAsyncTask().execute();
    }


    /*==============================================================================================
     * AsyncTask para ir buscar os dados as tabelas da Base de dados
     *============================================================================================== */
    private class getDataListAsyncTask extends AsyncTask<Void, Void, Cursor>
    {
        @Override
        protected Cursor doInBackground(Void... params)
        {
            String xSelect = PatientsContract.NRECFALTA + " > 0";
            Cursor cursor = getContentResolver().query(PatientsProvider.CONTENT_URI, null, xSelect, null, null);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                startManagingCursor(cursor);
            }
            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            if(_adapter == null)
            {
                _adapter = new PatientsAdapter(cursor);

                final ListView listViewPacientes = (ListView)findViewById(R.id.listViewPacientes);

                listViewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                        Intent intentToOpenDadosRecitasFaltasActivity = new Intent(getApplicationContext(), DadosRecitasFaltasActivity.class);

                        intentToOpenDadosRecitasFaltasActivity.putExtra(DadosRecitasFaltasActivity.PATIENT_ID, ((TextView)v.findViewById(R.id.lbIdPaciente)).getText());
                        intentToOpenDadosRecitasFaltasActivity.putExtra(DadosRecitasFaltasActivity.PATIENT_NAME, ((TextView)v.findViewById(R.id.lbNomeUtente)).getText());
                        intentToOpenDadosRecitasFaltasActivity.putExtra(DadosRecitasFaltasActivity.PATIENT_NUTENTE, ((TextView)v.findViewById(R.id.lbNumUtente)).getText());
                        intentToOpenDadosRecitasFaltasActivity.putExtra(DadosRecitasFaltasActivity.PATIENT_RECFALTA, ((TextView)v.findViewById(R.id.lbNumReceitasFalta)).getText());


                        startActivity(intentToOpenDadosRecitasFaltasActivity);

                    }
                });
                listViewPacientes.setAdapter(_adapter);
            }
            else
            {
                // Stop using the old version of the temperatures in the cursor.
                stopManagingCursor(_adapter.getCursor());
                _adapter.changeCursor(cursor);

            }
        }
    }


    private class PatientsAdapter extends CursorAdapter
    {
        /**
         *
         * @pt Construtor de Adapter que recebe o cursor com a informação a colocar na listagem de receitas em falta
         */
        public PatientsAdapter(Cursor cursor)
        {
            super(ListaPacientesFaltaActivity.this, cursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
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
                    .setText(getResources().getString(R.string.utentenumero)+": " + cursor.getString(cursor.getColumnIndex(PatientsContract.NUITENTE)));

            ((TextView) v.findViewById(R.id.lbNumReceitasFalta))
                    .setText(getResources().getString(R.string.utenterecfalta) +": " +cursor.getString(cursor.getColumnIndex(PatientsContract.NRECFALTA)));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View xView = super.getView(position, convertView,  parent);

                        //return super.getView(position, convertView, parent);
            return xView;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.rowpacientefalta, parent, false);
            bindView(view, context, cursor);

            return view;
        }


    }


}
