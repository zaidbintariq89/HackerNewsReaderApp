package com.android.hackernewsreaderapp;

import android.app.Application;
import android.content.Context;

import com.android.hackernewsreaderapp.data.network.ServerApi;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zaidbintariq on 14/06/2017.
 */

public class MainApplication extends Application {

    private ServerApi serverApi;
    private Scheduler scheduler;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    private static MainApplication get(Context context) {
        return (MainApplication) context.getApplicationContext();
    }

    public static MainApplication create(Context context) {
        return MainApplication.get(context);
    }

    public Scheduler subscribeScheduler() {
        if (scheduler == null) {
            scheduler = Schedulers.io();
        }

        return scheduler;
    }

    public void setServerApi(ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
