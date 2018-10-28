package com.zeffah.usermanagementportal.rest;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://reqres.in";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK);
            request = builder.build();
            return chain.proceed(request);
        }
    };

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();
}
