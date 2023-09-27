package consulting.gigs.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static String BASE_URL = "http://192.168.1.5/userApiAbarrotes/";
    private static RetrofitClient retrofitClient;
    private static Retrofit retrofit;
    private HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    private OkHttpClient.Builder builder = new OkHttpClient.Builder();



    private RetrofitClient(){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();

    }

    public static synchronized RetrofitClient getInstance(){
        if (retrofitClient == null){
            retrofitClient = new RetrofitClient();

        }
        return retrofitClient;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
