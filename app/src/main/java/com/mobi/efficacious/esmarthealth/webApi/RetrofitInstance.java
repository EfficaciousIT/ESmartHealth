package com.mobi.efficacious.esmarthealth.webApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://e-smarthealth.co.in/e-SmartHealthApi/api/";
    public static final String Image_URL = "http://e-smarthealth.co.in/e-SmartHealthApi/image/";
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}