package consulting.gigs.model;

import java.io.Serializable;

public class Loger implements Serializable {

    String usu_contra;
    String usu_mail;
    public String getUsu_contra() {
        return usu_contra;
    }

    public void setUsu_contra(String usu_contra) {
        this.usu_contra = usu_contra;
    }

    public String getUsu_mail() {
        return usu_mail;
    }

    public void setUsu_mail(String usu_mail) {
        this.usu_mail = usu_mail;
    }


}
