// WeatherResponse.java
package com.example.floodshield.net;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {
    @SerializedName("rain")
    public Rain rain;

    public static class Rain {
        @SerializedName("1h")  // may be null
        public Double oneHour;
    }
}
