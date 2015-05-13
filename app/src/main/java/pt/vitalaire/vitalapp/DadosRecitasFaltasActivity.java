package pt.vitalaire.vitalapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import pt.vitalaire.vitalapp.provider.PatientsContract;
import pt.vitalaire.vitalapp.provider.PatientsProvider;
import pt.vitalaire.vitalapp.provider.PrescriptionsMissingContract;
import pt.vitalaire.vitalapp.provider.PrescriptionsMissingProvider;

/**
 * Created by Jorge.Feteira on 06/05/2015.
 */
public class DadosRecitasFaltasActivity extends Activity {

    private Context _context;

    public static final String PATIENT_ID       = "PATIENT_ID";
    public static final String PATIENT_NAME     = "PATIENT_NAME";
    public static final String PATIENT_NUTENTE  = "PATIENT_NUTENTE";
    public static final String PATIENT_RECFALTA = "PATIENT_RECFALTA";

    private int ID_PATIENT = 0;
    private int XRECFALTA = 0;

    private CursorAdapter _adapterDadosRecitasFaltas;
    private static final String LOG_TAG = "LOG_TAG";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dadosrecitasfaltas_layout);

        ID_PATIENT = Integer.parseInt(getIntent().getStringExtra(PATIENT_ID));
        XRECFALTA =  Integer.parseInt(getIntent().getStringExtra(PATIENT_RECFALTA).replace( getResources().getString(R.string.utenterecfalta) + ": ",""));

        ((TextView)findViewById(R.id.lbNumUtente)).setText(getIntent().getStringExtra(PATIENT_NUTENTE));
        ((TextView)findViewById(R.id.lbNomeUtente)).setText(getIntent().getStringExtra(PATIENT_NAME));

        //*... AsyncTask para ir buscar os dados
        new getDataListAsyncTask().execute();




    }

    public void FinishAfterAsyncTask()
    {
        this.finish();
    }
    /*==============================================================================================
     * AsyncTask para ir buscar os dados as tabelas da Base de dados
     *============================================================================================== */
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
              if(_adapterDadosRecitasFaltas == null)
            {
                _adapterDadosRecitasFaltas = new MissingPrescriptionsAdapter(cursor);
                final ListView lsListaRecfalta = (ListView)findViewById(R.id.lstViewrecFalta);
                lsListaRecfalta.setAdapter(_adapterDadosRecitasFaltas);
                /*
                lsListaRecfalta.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i(LOG_TAG, "Carreiguei no Bt >>>"  + ((TextView)view.findViewById(R.id.lbIdRec)).getText());

                    }
                });
                */
            }
            else
            {
                // Stop using the old version of the temperatures in the cursor.
                stopManagingCursor(_adapterDadosRecitasFaltas.getCursor());
                _adapterDadosRecitasFaltas.changeCursor(cursor);

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
        public View getView(final int position, View convertView, ViewGroup parent) {

            View xView = super.getView(position, convertView,  parent);


            ImageButton xBtDelete = (ImageButton)xView.findViewById(R.id.btDelete);
            final  TextView  xId = ((TextView)xView.findViewById(R.id.lbIdRec));
            xBtDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*... deve ser executado o delete numa AsyTasck */
                    String xWhere = PrescriptionsMissingContract.ID_KEY + " = " + xId.getText().toString();
                    new deleteDataAsyncTask().execute(xWhere);

                }
            });


            return xView;
        }


        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recfaltadetail, parent, false);
            bindView(view, context, cursor);

            return view;
        }


    }


    /*==============================================================================================
* AsyncTask para ir buscar os dados as tabelas da Base de dados
*============================================================================================== */
    private class deleteDataAsyncTask extends AsyncTask<String, Void, Integer>
    {
        @Override
        protected Integer doInBackground(String... xWhere)
        {

            int xReturn = getContentResolver().delete(PrescriptionsMissingProvider.CONTENT_URI, xWhere[0], null);

            /*---------------------------------------------------------------------------------------------------------------
                 serve para eu saber quando não tenho mais receitas em falta para um paciente, e no caso de já não haver
                 receitas então vou terminar a activity pois não faz sentido ela continuar a existir, porque não há mais
                 receitas

                 entretanto tambem vou actualizar a tabela de pacientes o campo NRECFALTA para que depois de ser Zero o
                 registo do paciente não apareça mais
             ---------------------------------------------------------------------------------------------------------------*/
            if (xReturn > 0)
            {
                XRECFALTA--;
                String xSelect       = PatientsContract.ID_UITENTE + " = "+ ID_PATIENT;
                ContentValues xValues = new ContentValues();
                xValues.put(PatientsContract.NRECFALTA, XRECFALTA);
                getContentResolver().update(PatientsProvider.CONTENT_URI, xValues, xSelect, null);


            }



            return xReturn;


        }

        @Override
        protected void onPostExecute(Integer xResult) {
            super.onPostExecute(null);

            if (xResult > 0) {
                Toast.makeText(getApplicationContext(), R.string.recfaltadeleteok, Toast.LENGTH_SHORT).show();

                if(XRECFALTA == 0) {
                    FinishAfterAsyncTask();
                }else {
                /*... para fazer o refresh a ListView */
                    String xSelect = PrescriptionsMissingContract.ID_UITENTE + " = " + ID_PATIENT;
                    Cursor newCursor = getContentResolver().query(PrescriptionsMissingProvider.CONTENT_URI, null, xSelect, null, null);
                    _adapterDadosRecitasFaltas.swapCursor(newCursor);
                }
            }
            else
            {
                Toast.makeText(getApplicationContext(),  R.string.recfaltadeleteko, Toast.LENGTH_SHORT).show();
            }

        }




    }
}