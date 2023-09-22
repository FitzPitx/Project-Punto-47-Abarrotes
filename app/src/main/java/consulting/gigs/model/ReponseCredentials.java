package consulting.gigs.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ReponseCredentials implements Serializable {


    private ArrayList<Credentials> credentials;
    private String mensaje;


    public ReponseCredentials() {}

    public ArrayList<Credentials> getCredentials() {
        return credentials;
    }

    public void setCredentials(ArrayList<Credentials> credentials) {
        this.credentials = credentials;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
