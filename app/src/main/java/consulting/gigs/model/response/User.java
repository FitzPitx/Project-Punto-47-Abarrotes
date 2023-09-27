package consulting.gigs.model.response;

public class User{
    int user_id;
    String user_nombre, user_apellido, user_mail, user_usuario;

    public User(int user_id,String user_nombre, String user_apellido, String user_mail, String user_usuario){
        this.user_id = user_id;
        this.user_nombre = user_nombre;
        this.user_apellido = user_apellido;
        this.user_mail = user_mail;
        this.user_usuario = user_usuario;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_nombre(){
        return user_nombre;
    }

    public void setUser_nombre(String user_nombre){
        this.user_nombre = user_nombre;
    }

    public String getUser_apellido(){
        return user_apellido;
    }

    public void setUser_apellido(String user_apellido){
        this.user_apellido = user_apellido;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_usuario() {
        return user_usuario;
    }

    public void setUser_usuario(String user_usuario) {
        this.user_usuario = user_usuario;
    }
}
