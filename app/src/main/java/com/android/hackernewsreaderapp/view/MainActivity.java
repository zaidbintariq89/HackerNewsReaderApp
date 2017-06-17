package com.android.hackernewsreaderapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.android.hackernewsreaderapp.R;
import com.android.hackernewsreaderapp.adapter.PostAdapter;
import com.android.hackernewsreaderapp.databinding.ActivityMainBinding;
import com.android.hackernewsreaderapp.viewmodel.MainViewModel;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private static final String TAG = MainActivity.class.getName();

    private ActivityMainBinding binding;
    private MainViewModel mainViewModel;
    private PostAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        binding.setMainViewModel(mainViewModel);

        adapter = new PostAdapter(MainActivity.this);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        setupObserver(mainViewModel);

        mainViewModel.fetchTopStories();
    }

    @Override
    protected void onDestroy() {
        mainViewModel.reset();
        super.onDestroy();
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MainViewModel) {
            PostAdapter postAdapter = (PostAdapter) binding.recyclerView.getAdapter();
            MainViewModel peopleViewModel = (MainViewModel) observable;
            postAdapter.setItems(peopleViewModel.getStoriesIds());
        }
    }
}
