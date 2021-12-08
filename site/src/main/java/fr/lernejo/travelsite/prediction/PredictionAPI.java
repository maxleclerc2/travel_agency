package fr.lernejo.travelsite.prediction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PredictionAPI {

    @GET("/api/temperature")
    Call<Prediction> getPrediction(@Query("country") String country);
}
