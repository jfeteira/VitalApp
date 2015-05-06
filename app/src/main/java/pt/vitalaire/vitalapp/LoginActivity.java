package pt.vitalaire.vitalapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TableRow;
import pt.vitalaire.vitalapp.model.Credentials;

import static android.app.PendingIntent.getActivity;
import static java.lang.Integer.parseInt;

/**
 * Created by Jorge.Feteira on 30/04/2015.
 */
public class LoginActivity extends Activity {


    final Context context = this;
    private final static String LOG_TAG = "LOG_TAG";
    private Boolean  xNewUser  = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);
        final EditText  etUserName = (EditText)this.findViewById(R.id.etUserName);
        final EditText  etPin      = (EditText)this.findViewById(R.id.etPin);
        final EditText  etPinConf  = (EditText)this.findViewById(R.id.etPinConf);

        /*
         * ... vou verificar se é a primeira vez que o utlizador vai entar,
         *          se for caso disso vou pedir afixar algumas MSG para informação
         */
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        etUserName.setText(preferences.getString(PreferencesActivity.USERNAME_KEY, null));

        if(etUserName.getText().length() > 0)
        {
            TableRow mainLayout=(TableRow)this.findViewById(R.id.tabRowConf);
            mainLayout.setVisibility(TableRow.INVISIBLE );
            etPin.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        else
        {
            xNewUser = Boolean.TRUE;
        }


        findViewById(R.id.btOK).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /* em caso de novo user vou gravar as credenciais
                * caso contrario vou apenas verificar o PIN
                */

                try {

                    if (xNewUser) {
                        if(etPin.getText().toString().equals(etPinConf.getText().toString()))
                        {

                            //*... actualizar as Shared preferences da Aplicação
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(PreferencesActivity.USERNAME_KEY, etUserName.getText().toString());
                            editor.putString(PreferencesActivity.PASSWORD_KEY, etPin.getText().toString());
                            editor.commit();

                            //*... actualizar as variaveis globais da Aplicação
                            VitalApp app = (VitalApp) getApplication();
                            app.setCredentials(new Credentials(etUserName.getText().toString(), parseInt(etPin.getText().toString())));
                        }
                        else
                        {
                            showMessage( getResources().getString(R.string.warningMsg), getResources().getString(R.string.pinNotEqual),"");
                        }
                    } else {
                        if(!etPin.getText().toString().equals(preferences.getString(PreferencesActivity.PASSWORD_KEY, "")))
                        {
                            showMessage( getResources().getString(R.string.warningMsg), getResources().getString(R.string.pinIncorrecto),"");
                            return;
                        }

                        //*... proxima activiade
                        Intent intentToOpenMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentToOpenMainActivity);
                        finish();
                    }

                }
                catch (Exception e)
                {
                    Log.d(LOG_TAG,  e.getMessage());
                }

            }
        });
    }


    private void showMessage(String xTitle, String xMessage, String xBts)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle(xTitle);

        // set dialog message
        alertDialogBuilder
                .setMessage(xMessage)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        //*... serve para ir buscar o Icon de ALERT
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.alertDialogIcon, typedValue, true);


        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setIcon(typedValue.resourceId);
        alertDialog.show();
    }
}
