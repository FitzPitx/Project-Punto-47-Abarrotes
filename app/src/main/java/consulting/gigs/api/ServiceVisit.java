package consulting.gigs.api;
import consulting.gigs.model.ResponseVisit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ServiceVisit {
    @GET("visita/use/{id}")
    public Call<ResponseVisit> visitList(@Path("id") String id, @Header("Authorization") String token);
}
