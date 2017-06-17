package com.android.hackernewsreaderapp.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.android.hackernewsreaderapp.MainApplication;
import com.android.hackernewsreaderapp.data.network.ServerTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by zaidbintariq on 15/06/2017.
 */

public class MainViewModel extends Observable {

    public ObservableInt postProgress;
    public ObservableInt postRecycler;

    private List<Long> storiesIds;
    private long[] comentIds;
    private Context context;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public MainViewModel(@NonNull Context con) {
        context = con;
        this.storiesIds = new ArrayList<>();
        postProgress = new ObservableInt(View.GONE);
        postRecycler = new ObservableInt(View.GONE);
    }

    public void initializeViews() {
        postRecycler.set(View.GONE);
        postProgress.set(View.VISIBLE);
    }

    public void hideProgressView() {
        postProgress.set(View.GONE);
        postRecycler.set(View.VISIBLE);
    }

    public List<Long> getComentIds() {
        List<Long> ids = new ArrayList<>();
        if (comentIds != null && comentIds.length >  0) {
            for (long id : comentIds) {
                ids.add(id);
            }
        }
        return ids;
    }

    public void setComentIds(long[] comentIds) {
        this.comentIds = comentIds;
        hideProgressView();
        setChanged();
        notifyObservers();
    }

    public void fetchTopStories() {
        if (context != null) {
            storiesIds.clear();
            initializeViews();

            MainApplication mainApplication = MainApplication.create(context);

            Disposable disposable = ServerTask.getInstance().getServices().getTopStories()
                    .subscribeOn(mainApplication.subscribeScheduler())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<List<Long>>() {
                        @Override
                        public void accept(List<Long> peopleResponse) throws Exception {

                            changeDataSet(peopleResponse);

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            postProgress.set(View.GONE);
                            postRecycler.set(View.GONE);
                            Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            compositeDisposable.add(disposable);
        }
    }

    public void changeDataSet(List<Long> ids) {
        hideProgressView();
        storiesIds.addAll(ids);
        setChanged();
        notifyObservers();
    }

    public List<Long> getStoriesIds() {
        return storiesIds;
    }

    private void unSubscribeFromObservable() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void reset() {
        unSubscribeFromObservable();
        compositeDisposable = null;
        context = null;
    }
}
