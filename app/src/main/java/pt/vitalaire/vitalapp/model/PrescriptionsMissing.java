package pt.vitalaire.vitalapp.model;

import android.renderscript.Element;

/**
 * Created by Jorge.Feteira on 27/04/2015.
 *
 * Descrição das receitas em Falta
 *
 *     terapia     = terapia em Falta
 *     ini         = data inicial do período em falta
 *     end         = data final do período em falta
 *     missingDays = Numero de dias em falta
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class PrescriptionsMissing {

    private long _idUtente;
    private int _idKey;
    private String _terapia;
    private String _ini;
    private String _end;
    private int _missingDays;
    private int _recok;


    public PrescriptionsMissing(int idUtente, int idKey, String terapia, String ini, String end, int missingDays)
    {
        _idUtente = idUtente;
        _idKey = idKey;
        _terapia = terapia;
        _ini = ini;
        _end = end;
        _missingDays = missingDays;
    }

    public PrescriptionsMissing(int idKey, String terapia, String ini, String end, int missingDays)
    {
        _idKey = idKey;
        _terapia = terapia;
        _ini = ini;
        _end = end;
        _missingDays = missingDays;
    }

    public PrescriptionsMissing()
    {
    }

    public void setIdUtente(long idUtente) {
        _idUtente = idUtente;
    }

    public void setIdKey(int idKey) {
        _idKey = idKey;
    }

    public void setTerapia(String terapia) {
        _terapia = terapia;
    }

    public void setIni(String ini) {
        _ini = ini;
    }

    public void setEnd(String end) {
        _end = end;
    }

    public void setMissingDays(int missingDays) {
        _missingDays = missingDays;
    }

    public String getTerapia() {
        return _terapia;
    }

    public String getIni() {
        return _ini;
    }

    public String getEnd() {
        return _end;
    }

    public int getMissingDays() {
        return _missingDays;
    }

    public int getIdKey() {
        return _idKey;
    }

    public long getIdUtente() {
        return _idUtente;
    }

    public void setRecok(int recok) {
        _recok = recok;
    }

    public int getRecok() {
        return _recok;
    }
}
