package com.android.hackernewsreaderapp.data.network;

import android.util.Log;

import com.android.hackernewsreaderapp.data.constants.UrlConstants;
import com.android.hackernewsreaderapp.data.utility.CommonUtility;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zaid on 03/03/2016.
 */
public class ServerTask {

    private static ServerTask serverTask = null;

    private ServerApi serverAPI;
    private Retrofit retrofit;
    private OkHttpClient.Builder httpClient;

    public static ServerTask getInstance() {
        if (serverTask == null) {
            serverTask = new ServerTask();
        }

        return serverTask;
    }

    private ServerTask() {

        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(loggingInterceptor);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .baseUrl(UrlConstants.SERVER_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

    }

    public <S> S createService(Class<S> serviceClass) {

        return retrofit.create(serviceClass);
    }

    public ServerApi getServices() {
        if (serverAPI == null) {
            serverAPI = createService(ServerApi.class);
        }

        return serverAPI;
    }

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!CommonUtility.isNetworkAvailable()) {
                throw new IOException("No internet connectivity");
            } else {

                String requestLog = String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers());

                if (request.method().compareToIgnoreCase("post") == 0) {
                    requestLog = "\n" + requestLog + "\n" + bodyToString(request);
                }

                if (request.method().compareToIgnoreCase("put") == 0) {
                    requestLog = "\n" + requestLog + "\n" + bodyToString(request);
                }

                Log.d("TAG", "request" + "\n" + requestLog);

                Response response = chain.proceed(request);

                String bodyString = response.body().string();

                Log.d("TAG", "\nResponse Body : " + bodyString);

                return response.newBuilder()
                        .body(ResponseBody.create(response.body().contentType(), bodyString))
                        .build();
            }
        }
    }

    private static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
