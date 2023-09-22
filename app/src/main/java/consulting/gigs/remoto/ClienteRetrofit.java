package consulting.gigs.remoto;

import static android.util.Base64.NO_WRAP;



import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteRetrofit {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl,String token) {
        //private static final String AUTH = "Basic "+ Base64.encodeToString(token);
        if (retrofit==null) {
           /* OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(
                            new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request original = chain.request();
                                    Request.Builder requestBuilder = original.newBuilder()
                                            .addHeader("Accept", "application/json")
                                            .addHeader("Authorization", token)
                                            .method(original.method(), original.body());
                                    Request request = requestBuilder.build();
                                    return chain.proceed(request);
                                }
                            }
                    ).build();*/

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                   // .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
