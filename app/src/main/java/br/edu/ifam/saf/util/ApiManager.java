package br.edu.ifam.saf.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import br.edu.ifam.saf.SAFService;
import br.edu.ifam.saf.api.data.MensagemErroResponse;
import br.edu.ifam.saf.data.LocalRepository;
import br.edu.ifam.saf.data.LocalRepositoryImpl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiManager {


    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    private static SAFService safService;

    private ApiManager() {

    }

    private static void init() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);


        final LocalRepository repository = LocalRepositoryImpl.getInstance();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        if (repository.getUserInfo() != null) {
                            Request request = chain.request().newBuilder().header("Authorization", "Bearer " + repository.getUserInfo().getToken()).build();
                            return chain.proceed(request);
                        }
                        return chain.proceed(null);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()

                .client(client)
                .baseUrl(repository.getApiHost())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GSON))
                .build();
        safService = retrofit.create(SAFService.class);

    }

    public static SAFService getService() {
        if (safService == null) {
            init();
        }
        return safService;
    }

    public static MensagemErroResponse parseErro(Response<?> response) {
        TypeAdapter<MensagemErroResponse> adapter = GSON.getAdapter(MensagemErroResponse.class);
        try {
            return adapter.fromJson(response.errorBody().charStream());
        } catch (IOException e) {
            return new MensagemErroResponse("Erro ao recuperar o erro");
        }
    }

    public static Gson getGson() {
        return GSON;
    }

}
