package by.vanhooijdonk.destroyerdroid.rest;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import by.vanhooijdonk.destroyerdroid.helper.PrefHelper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yahor_Fralou on 3/22/2017 12:08 PM.
 */

public class ClientProvider {
    private static Retrofit retrofitClient = null;
    private static RobotApiClient robotApiClient = null;

    public static RobotApiClient getApiClient(Context ctx) {
        if (robotApiClient == null) {
            robotApiClient = getRetrofit(ctx).create(RobotApiClient.class);
        }

        return robotApiClient;
    }

    public static void dropClientsSettings() {
        robotApiClient = null;
        retrofitClient = null;
    }

    private static Retrofit getRetrofit(Context ctx) {
        if (retrofitClient == null) {
            retrofitClient = new Retrofit.Builder()
                    .baseUrl("http://" + PrefHelper.getBaseUrl(ctx))
                    .client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }

        return retrofitClient;
    }

    private static OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();

    }
}
