// FloodChecker.java
package com.example.floodshield;

import android.util.Log;

import com.example.floodshield.net.RetrofitClient;
import com.example.floodshield.net.WeatherApi;
import com.example.floodshield.net.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FloodChecker {

    public interface RiskCallback {
        void onResult(String risk, double rainfallMm);
    }

    private static final double HIGH_THRESHOLD = 50.0;   // mm in last 1 h
    private static final double MOD_THRESHOLD  = 20.0;   // mm

    public static void checkRisk(double lat, double lon, RiskCallback cb) {
        String API_KEY = "4a6f7c7bf2c9bed8361ddc1d815668d2";

        WeatherApi api = RetrofitClient.getInstance().create(WeatherApi.class);
        api.getCurrentWeather(lat, lon, API_KEY, "metric")
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> res) {
                        double rain = 0;
                        if (res.body() != null && res.body().rain != null && res.body().rain.oneHour != null) {
                            rain = res.body().rain.oneHour;
                        }

                        System.out.println("Rain: " + rain);
                        String risk = "High";
                        if (rain >= HIGH_THRESHOLD) risk = "High";
                        else if (rain >= MOD_THRESHOLD) risk = "Moderate";

                        cb.onResult(risk, rain);
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e("FloodChecker", "Weather fetch failed", t);
                        cb.onResult("Unknown", 0);
                    }
                });
    }
}
