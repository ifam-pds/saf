package br.edu.ifam.saf.util;

import br.edu.ifam.saf.SAFService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiManager {


    private static final String HOST = "192.168.0.11";
    private static final Integer PORT = 8080;
    private static final String BASE_URL = String.format("http://%s:%d/api/", HOST, PORT);

    private static SAFService safService;

    private ApiManager() {

    }

    private static void init() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        safService = retrofit.create(SAFService.class);

    }

    public static SAFService getService() {
        if (safService == null) {
            init();
        }
        return safService;
    }
}
