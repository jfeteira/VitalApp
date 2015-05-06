package pt.vitalaire.vitalapp.model;

import java.util.ArrayList;

/**
 * Created by Jorge.Feteira on 27/04/2015.
 *
 * Descrição do Paciente
 *
 *     _numUtente = Numero do Utente no Sistema Nacilnal de Saúde
 *     _numUtente = Nome do Paciente
 *
 *    _prescriptionsMissing = Numero de receitas em falta do Paciente
 *
 *
 */
public class Patient
{

    private int _idUtente;
    private String _numUtente;
    private String _nomeUtente;
    private int    _nrecfalta;

    public Patient(int idUtente, String numUtente, String nomeUtente, int nrecfalta)
    {
        _idUtente = idUtente;
        _numUtente = numUtente;
        _nomeUtente = nomeUtente;
        _nrecfalta = nrecfalta;
    }

    public Patient(String numUtente, String nomeUtente)
    {
        _numUtente = numUtente;
        _nomeUtente = nomeUtente;
    }

    public Patient()
    {
    }
    public void setIdUtente(int xIdUtente)
    {
        _idUtente = xIdUtente;
    }

    public void setNomeUtente(String xNomeUtente)
    {
        _nomeUtente = xNomeUtente;
    }

    public void setNumUtente(String xNumUtente)
    {
        _numUtente = xNumUtente;
    }

    public int getIdUtente()
    {
        return _idUtente;
    }

    public String getNumUtente()
    {
        return _numUtente;
    }

    public String getNomeUtente()
    {
        return _nomeUtente;
    }

    public void setNrecfalta(int nrecfalta) {
        _nrecfalta = nrecfalta;
    }

    public int getNrecfalta() {
        return _nrecfalta;
    }
}

