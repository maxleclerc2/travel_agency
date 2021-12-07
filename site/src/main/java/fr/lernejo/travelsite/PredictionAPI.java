package fr.lernejo.travelsite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PredictionAPI {

    @GET("/api/temperature?country={country}")
    Call<Object> getPrediction(@Path("country") String country);
}
