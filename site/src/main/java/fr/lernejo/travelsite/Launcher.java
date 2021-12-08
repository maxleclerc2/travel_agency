package fr.lernejo.travelsite;

import fr.lernejo.travelsite.prediction.PredictionAPI;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

    @Bean
    PredictionAPI predictionClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cache(createCache())
            .addNetworkInterceptor(createInterceptor())
            .build();

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:7080/")
            .client(okHttpClient)
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

        return retrofit.create(PredictionAPI.class);
    }

    private Interceptor createInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder()
                    .header("Cache-Control", "max-age=120, only-if-cached, max-stale=0")
                    .build();
            }
        };
    }

    private Cache createCache() {
        Cache cache = null;
        cache = new Cache(getCacheDirectory(), 1024 * 1024 * 10);
        return cache;
    }

    private File getCacheDirectory() {
        return new File("./cache/predictionResponse");
    }
}
