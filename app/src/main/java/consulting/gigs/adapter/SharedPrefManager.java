package consulting.gigs.adapter;

import android.content.Context;
import android.content.SharedPreferences;

import consulting.gigs.model.response.User;

public class SharedPrefManager {
    private static String SHARED_PREF_NAME = "Crosiux";
    private SharedPreferences sharedPreferences;
    Context context;
    private  SharedPreferences.Editor editor;

    public SharedPrefManager(Context context){
        this.context = context;
    }

    public void saveUser(User user){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("id", user.getUser_id());
        editor.putString("mail", user.getUser_mail());
        editor.putString("usuario", user.getUser_usuario());
        editor.putBoolean("logged", true);
        editor.apply();
    }

    public boolean isLoggedIn(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("logged", false);
    }

    public User getUser(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("user_id", -1),
                sharedPreferences.getString("user_mail", null),
                sharedPreferences.getString("user_usuario", null)
        );
    }

    public void logout(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
