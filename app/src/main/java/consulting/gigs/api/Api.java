package consulting.gigs.api;

import consulting.gigs.model.response.LoginResponse;
import consulting.gigs.model.response.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("registerUser.php")
    Call<RegisterResponse> register (
            @Field("user_nombre") String user_nombre,
            @Field("user_apellido") String user_apellido,
            @Field("user_usuario") String user_usuario,
            @Field("user_contra") String user_contra,
            @Field("user_mail") String user_mail,
            @Field("user_status") int user_status
    );

    @FormUrlEncoded
    @POST("loginUser.php")
    Call<LoginResponse> login (
            @Field("user_mail") String user_mail,
            @Field("user_contra") String user_contra
    );
}
