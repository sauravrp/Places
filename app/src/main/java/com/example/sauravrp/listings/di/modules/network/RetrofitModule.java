package com.example.sauravrp.listings.di.modules.network;

import com.example.sauravrp.listings.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule {

    private static String BASE_URL = "https://query.yahooapis.com/v1/public/";

    private Retrofit retrofit;

    @Provides Retrofit providesRetrofit() {

        if(retrofit == null) {

            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                okHttpBuilder.addNetworkInterceptor(new StethoInterceptor());
            }

            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                     .client(okHttpBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
