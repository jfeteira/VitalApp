package pt.vitalaire.vitalapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Jorge.Feteira on 06/05/2015.
 */
public class DadosRecitasFaltasActivity extends Activity {

    private Context _context;

    public static final String PATIENT_ID      = "PATIENT_ID";
    public static final String PATIENT_NAME    = "PATIENT_NAME";
    public static final String PATIENT_NUTENTE = "PATIENT_NUTENTE";

    private int ID_PATIENT = 0;

    private static final String LOG_TAG = "LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dadosrecitasfaltas_layout);

        ID_PATIENT = Integer.parseInt(getIntent().getStringExtra(PATIENT_ID));

        ((TextView)findViewById(R.id.lbNumUtente)).setText(getIntent().getStringExtra(PATIENT_NUTENTE));
        ((TextView)findViewById(R.id.lbNomeUtente)).setText(getIntent().getStringExtra(PATIENT_NAME));
    }
}