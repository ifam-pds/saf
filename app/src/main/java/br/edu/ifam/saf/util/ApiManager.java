package br.edu.ifam.saf.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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


    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Date.class, new DateTypeDeserializer())
            .create();

    private static SAFService safService;

    private static SimpleDateFormat formatter;

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
                        if (repository.getInfoUsuario() != null) {
                            Request request = chain.request().newBuilder().header("Authorization", "Bearer " + repository.getInfoUsuario().getToken()).build();
                            return chain.proceed(request);
                        }
                        return chain.proceed(chain.request());
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

    public static SimpleDateFormat getFormatter() {

        if (formatter == null) {
            formatter = new SimpleDateFormat("dd/MM HH:mm");
        }

        return formatter;
    }

    public static SAFService getService() {
        if (safService == null) {
            init();
        }
        return safService;
    }

    public static MensagemErroResponse parseErro(Response response) {
        TypeAdapter<MensagemErroResponse> adapter = GSON.getAdapter(MensagemErroResponse.class);
        try {
            return adapter.fromJson(response.errorBody().charStream());
        } catch (Exception e) {
            e.printStackTrace();
            return new MensagemErroResponse("Erro ao recuperar o erro");
        }
    }

    public static Gson getGson() {
        return GSON;
    }

    static class DateTypeDeserializer implements JsonDeserializer<Date> {
        private static final String[] DATE_FORMATS = new String[]{
                "yyyy-MM-dd'T'HH:mm:ssZ",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd",
                "EEE MMM dd HH:mm:ss z yyyy",
                "HH:mm:ss",
                "MM/dd/yyyy HH:mm:ss aaa",
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS",
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'",
                "MMM d',' yyyy H:mm:ss a",
                "MMM d',' yyyy H:mm:ss"
        };

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws JsonParseException {
            for (String format : DATE_FORMATS) {
                try {
                    return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
                } catch (ParseException e) {
                }
            }
            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString() + '"');
        }

    }

}
