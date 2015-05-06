package pt.vitalaire.vitalapp.model;

/**
 * Created by Jorge.Feteira on 28/04/2015.
 */
public class Credentials {

    private String _username;
    private int _password;

    public Credentials(String username, int password) {
        _username = username;
        _password = password;
    }

    public String getUsername() {
        return _username;
    }

    public int getPassword() {
        return _password;
    }

    public void setUsername(String username) {
        _username = username;
    }

    public void setPassword(int password) {
        _password = password;
    }
}
