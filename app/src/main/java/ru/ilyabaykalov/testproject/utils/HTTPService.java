package ru.ilyabaykalov.testproject.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.ilyabaykalov.testproject.SkyLoveAPI.API;

public class HTTPService {
    private static final String BASE_URL = "https://api.skylove.su/";
    private static HTTPService instance;
    Retrofit retrofit;

    private HTTPService() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
    }

    public static HTTPService getInstance() {
        if (instance == null) {
            instance = new HTTPService();
        }
        return instance;
    }

    private OkHttpClient getClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();

        client.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("DeviceID", "test1234")
                    .addHeader("X-sklapp-version", "Android/test")
                    .addHeader("X-sklapp-lang", "ru")
                    .build();
            return chain.proceed(request);
        });

        return client.build();
    }

    public API getApi() {
        return retrofit.create(API.class);
    }
}
