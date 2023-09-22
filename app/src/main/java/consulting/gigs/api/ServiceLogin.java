package consulting.gigs.api;



import consulting.gigs.model.Loger;
import consulting.gigs.model.ReponseCredentials;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceLogin {
    @POST("login")
    public Call<ReponseCredentials> accessLogin(@Body Loger login);

}
