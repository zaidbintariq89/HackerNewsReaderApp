package com.android.hackernewsreaderapp.view;

import com.android.hackernewsreaderapp.adapter.PostAdapter;
import com.android.hackernewsreaderapp.viewmodel.MainViewModel;

import java.util.Observable;

/**
 * Created by zaidbintariq on 17/06/2017.
 */

public class CommentsFragment extends PostFragment {

    private long[] comments;

    public long[] getComments() {
        return comments;
    }

    public void setComments(long[] comments) {
        this.comments = comments;
    }

    @Override
    protected void fetchData() {
        mainViewModel.setComentIds(getComments());
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MainViewModel) {
            PostAdapter postAdapter = (PostAdapter) binding.recyclerView.getAdapter();
            MainViewModel viewModel = (MainViewModel) observable;
            postAdapter.setItems(viewModel.getComentIds()); // load comments
        }
    }
}
