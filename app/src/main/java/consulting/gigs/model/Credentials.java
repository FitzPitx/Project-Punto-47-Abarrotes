package consulting.gigs.model;

import java.io.Serializable;

public class Credentials implements Serializable {
    String usu_identifier;
    String usu_key;
    String usu_id;

    public String getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(String usu_id) {
        this.usu_id = usu_id;
    }

    public String getUsu_identifier() {
        return usu_identifier;
    }

    public void setUsu_identifier(String usu_identifier) {
        this.usu_identifier = usu_identifier;
    }

    public String getUsu_key() {
        return usu_key;
    }

    public void setUsu_key(String usu_key) {
        this.usu_key = usu_key;
    }
}
