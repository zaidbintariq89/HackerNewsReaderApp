package com.android.hackernewsreaderapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.hackernewsreaderapp.R;
import com.android.hackernewsreaderapp.databinding.PostRowItemBinding;
import com.android.hackernewsreaderapp.model.PostModel;
import com.android.hackernewsreaderapp.viewmodel.PostViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zaidbintariq on 14/06/2017.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.BindingHolder> {
    private HashMap<Long,PostModel> mPosts;
    private Context mContext;
    private List<Long> storiesIds;

    public PostAdapter(Context context) {
        mContext = context;
        mPosts = new HashMap<>();
        storiesIds = new ArrayList<>();
    }

    public void setItems(List<Long> ids) {
        storiesIds.addAll(ids);
        notifyItemRangeChanged(storiesIds.size(),ids.size()-1);
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PostRowItemBinding postBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.post_row_item,
                parent,
                false);
        return new BindingHolder(postBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        long id = storiesIds.get(position);
        holder.binding.setViewModel(new PostViewModel(mContext,id,mPosts));
    }

    @Override
    public int getItemCount() {
        return storiesIds.size();
    }


    public static class BindingHolder extends RecyclerView.ViewHolder {
        private PostRowItemBinding binding;

        public BindingHolder(PostRowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
