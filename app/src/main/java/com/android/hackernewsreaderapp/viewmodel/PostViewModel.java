package com.android.hackernewsreaderapp.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.hackernewsreaderapp.MainApplication;
import com.android.hackernewsreaderapp.data.network.ServerTask;
import com.android.hackernewsreaderapp.data.utility.CommonUtility;
import com.android.hackernewsreaderapp.model.PostModel;
import com.android.hackernewsreaderapp.view.CommentsViewActivity;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * Created by zaidbintariq on 14/06/2017.
 */

public class PostViewModel extends BaseObservable {

    public ObservableInt commentProgress;
    public ObservableInt replyView;

    private Context context;
    private PostModel post;
    private PostModel replyModel;
    private HashMap<Long, PostModel> mPosts;

    // this is for unit test
    public PostViewModel(Context context,PostModel model) {
        this.context = context;
        post = model;

        commentProgress = new ObservableInt(View.VISIBLE);
        replyView = new ObservableInt(View.GONE);
    }

    public PostViewModel(Context context, long id, HashMap<Long, PostModel> posts) {
        this.context = context;
        this.mPosts = posts;

        commentProgress = new ObservableInt(View.VISIBLE);
        replyView = new ObservableInt(View.GONE);

        if (!mPosts.containsKey(id)) {
            fetchStory(id);
        } else {
            addItem(id, mPosts.get(id));
        }
    }

    public String getPostTitle() {
        if (post != null && !TextUtils.isEmpty(post.getTitle())) {
            commentProgress.set(View.GONE);
            return post.getTitle();
        } else if (post != null && !TextUtils.isEmpty(post.getText())) {
            commentProgress.set(View.GONE);
            return post.getText();
        } else
            return "";
    }

    public String getUpdatedAt() {
        return post != null ? CommonUtility.getTimeEllapseDifference(post.getTime()) : "";
    }

    public int getCommentsVisibility() {
        return post != null && post.postType == PostModel.PostType.COMMENT ? View.GONE : View.VISIBLE;
    }

    public int getReplyVisibility() {
        return replyModel != null && replyModel.postType == PostModel.PostType.COMMENT ? View.VISIBLE : View.GONE;
    }

    public long[] getKids() {
        return post != null && post.getKids() != null ? post.getKids() : null;
    }

    public String getReplyText() {
        if (replyModel != null && !TextUtils.isEmpty(replyModel.getTitle())) {
            replyView.set(View.VISIBLE);
            return replyModel.getTitle();
        } else if (replyModel != null && !TextUtils.isEmpty(replyModel.getText())) {
            replyView.set(View.VISIBLE);
            return replyModel.getText();
        } else
            return "";
    }

    public View.OnClickListener onClickPost() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostModel.PostType postType = post.postType;
                if (postType == PostModel.PostType.JOB || postType == PostModel.PostType.STORY) {
                    launchStoryActivity();
                } else if (postType == PostModel.PostType.ASK) {
                    launchCommentsActivity();
                }
            }
        };
    }

    // for unit test
    public void onItemClick(View view) {
        PostModel.PostType postType = post.postType;
        if (postType == PostModel.PostType.JOB || postType == PostModel.PostType.STORY) {
            launchStoryActivity();
        } else if (postType == PostModel.PostType.ASK || postType == PostModel.PostType.COMMENT) {
            launchCommentsActivity();
        }
    }

    public void setPost(PostModel post) {
        this.post = post;
        notifyChange();
    }

    public View.OnClickListener onClickComments() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCommentsActivity();
            }
        };
    }

    private void launchStoryActivity() {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(post.getUrl())));
    }

    private void launchCommentsActivity() {
        context.startActivity(CommentsViewActivity.getCommentsActivityIntent(context, post.getKids()));
    }

    private void addItem(long id, PostModel post) {
        if (!mPosts.containsKey(id)) {
            mPosts.put(id, post);
        }
        this.post = post;

        if (post != null && post.postType == PostModel.PostType.COMMENT &&
                post.getKids() != null && post.getKids().length > 0) {
            long replyId = post.getKids()[0];
            fetchReply(replyId);
        } else {
            notifyChange();
        }
    }

    private void fetchReply(final long id) {
        MainApplication mainApplication = MainApplication.create(context);
        ServerTask.getInstance().getServices().getStoryDetailRX(id)
                .subscribeOn(mainApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PostModel>() {
                    @Override
                    public void accept(PostModel response) throws Exception {

                        replyModel = response;
                        notifyChange();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchStory(final long id) {

        MainApplication mainApplication = MainApplication.create(context);
        ServerTask.getInstance().getServices().getStoryDetailRX(id)
                .subscribeOn(mainApplication.subscribeScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PostModel>() {
                    @Override
                    public void accept(PostModel response) throws Exception {

                        addItem(id, response);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
