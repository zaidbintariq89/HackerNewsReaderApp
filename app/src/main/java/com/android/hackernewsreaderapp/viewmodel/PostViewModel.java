package com.android.hackernewsreaderapp.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableInt;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.android.hackernewsreaderapp.data.network.ServerCallback;
import com.android.hackernewsreaderapp.data.network.ServerError;
import com.android.hackernewsreaderapp.data.network.ServerTask;
import com.android.hackernewsreaderapp.data.utility.CommonUtility;
import com.android.hackernewsreaderapp.model.PostModel;
import com.android.hackernewsreaderapp.view.CommentsViewActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

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
        if (post != null && !TextUtils.isEmpty(post.title)) {
            commentProgress.set(View.GONE);
            return post.title;
        } else if (post != null && !TextUtils.isEmpty(post.text)) {
            commentProgress.set(View.GONE);
            return post.text;
        } else
            return "";
    }

    public String getUpdatedAt() {
        return post != null ? CommonUtility.getTimeEllapseDifference(post.time): "";
    }

    public int getCommentsVisibility() {
        return post != null && post.postType == PostModel.PostType.COMMENT ? View.GONE : View.VISIBLE;
    }

    public int getReplyVisibility() {
        return replyModel != null && replyModel.postType == PostModel.PostType.COMMENT ? View.VISIBLE : View.GONE;
    }

    public String getReplyText() {
        if (replyModel != null && !TextUtils.isEmpty(replyModel.title)) {
            replyView.set(View.VISIBLE);
            return replyModel.title;
        } else if (replyModel != null && !TextUtils.isEmpty(replyModel.text)) {
            replyView.set(View.VISIBLE);
            return replyModel.text;
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

    public View.OnClickListener onClickComments() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCommentsActivity();
            }
        };
    }

    private void launchStoryActivity() {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(post.url)));
    }

    private void launchCommentsActivity() {
        context.startActivity(CommentsViewActivity.getCommentsActivityIntent(context, post.kids));
    }

    private void addItem(long id, PostModel post) {
        if (!mPosts.containsKey(id)) {
            mPosts.put(id, post);
        }
        this.post = post;

        if (post != null && post.postType == PostModel.PostType.COMMENT &&
                post.kids != null && post.kids.length > 0) {
            long replyId = post.kids[0];
            fetchReply(replyId);
        } else {
            notifyChange();
        }
    }

    private void fetchReply(final long id) {
        Call<PostModel> call = ServerTask.getInstance().getServices().getStoryDetail(id);
        call.enqueue(new ServerCallback<PostModel>() {
            @Override
            public void onFailure(ServerError restError) {
                Toast.makeText(context, restError.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Response<PostModel> response) {

                replyModel = response.body();
                notifyChange();
            }
        });
    }

    private void fetchStory(final long id) {
        Call<PostModel> call = ServerTask.getInstance().getServices().getStoryDetail(id);
        call.enqueue(new ServerCallback<PostModel>() {
            @Override
            public void onFailure(ServerError restError) {
                Toast.makeText(context, restError.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Response<PostModel> response) {
                addItem(id, response.body());
            }
        });
    }
}
