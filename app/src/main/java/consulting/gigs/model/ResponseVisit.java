package consulting.gigs.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseVisit implements Serializable {
    private ArrayList<Visit> visita;
    private String mensaje;

    public ArrayList<Visit> getVisita() {
        return visita;
    }

    public void setVisita(ArrayList<Visit> visita) {
        this.visita = visita;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
