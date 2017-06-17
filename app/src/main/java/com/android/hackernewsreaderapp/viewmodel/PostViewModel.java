package com.android.hackernewsreaderapp.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.android.hackernewsreaderapp.data.network.ServerCallback;
import com.android.hackernewsreaderapp.data.network.ServerError;
import com.android.hackernewsreaderapp.data.network.ServerTask;
import com.android.hackernewsreaderapp.model.PostModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by zaidbintariq on 14/06/2017.
 */

public class PostViewModel extends BaseObservable {

    private Context context;
    private PostModel post;
    private HashMap<Long,PostModel> mPosts;

    public PostViewModel(Context context,long id,HashMap<Long,PostModel> posts) {
        this.context = context;
        this.mPosts = posts;

        if (!mPosts.containsKey(id)) {
            fetchStory(id);
        } else {
            addItem(id,mPosts.get(id));
        }
    }

    public String getPostTitle() {
        return post == null ? "" : post.title;
    }

    public int getCommentsVisibility() {
        return post != null && post.postType == PostModel.PostType.STORY && post.kids == null ? View.GONE : View.VISIBLE;
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
//        context.startActivity(CommentsActivity.getStartIntent(context, post));
    }

    private void addItem(long id , PostModel post) {
        if (!mPosts.containsKey(id)) {
            mPosts.put(id,post);
        }
        this.post = post;
        notifyChange();
    }

    private void fetchStory(final long id) {
        Call<PostModel> call = ServerTask.getInstance().getServices().getStoryDetail(id);
        call.enqueue(new ServerCallback<PostModel>() {
            @Override
            public void onFailure(ServerError restError) {
                Log.e("Adapter",restError.getMessage());
            }

            @Override
            public void onSuccess(Response<PostModel> response) {
                addItem(id,response.body());
            }
        });
    }
}
