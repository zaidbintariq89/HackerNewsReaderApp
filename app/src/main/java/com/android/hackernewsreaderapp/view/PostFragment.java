package com.android.hackernewsreaderapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.hackernewsreaderapp.adapter.PostAdapter;
import com.android.hackernewsreaderapp.databinding.FragmentPostBinding;
import com.android.hackernewsreaderapp.viewmodel.MainViewModel;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by zaidbintariq on 17/06/2017.
 */

public class PostFragment extends Fragment implements Observer {

    protected FragmentPostBinding binding;
    protected MainViewModel mainViewModel;
    protected PostAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // retain this fragment
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = FragmentPostBinding.inflate(inflater, container, false);
            mainViewModel = new MainViewModel(getActivity());
            binding.setMainViewModel(mainViewModel);

            bindData();
            fetchData();
        }

        return binding.getRoot();
    }

    private void bindData() {
        setupListView(binding.recyclerView);
        setupObserver(mainViewModel);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.resetList();
                fetchData();
                if (binding.swipeRefreshLayout.isRefreshing()) {
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    protected void fetchData() {
        mainViewModel.fetchTopStories();
    }

    private void setupListView(RecyclerView listView) {
        adapter = new PostAdapter(getActivity());
        listView.setAdapter(adapter);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainViewModel.reset();
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MainViewModel) {
            PostAdapter postAdapter = (PostAdapter) binding.recyclerView.getAdapter();
            MainViewModel viewModel = (MainViewModel) observable;
            postAdapter.setItems(viewModel.getStoriesIds());
        }
    }
}
