package com.android.hackernewsreaderapp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.android.hackernewsreaderapp.R;

/**
 * Created by zaidbintariq on 16/06/2017.
 */

public class CommentsViewActivity extends MainActivity {

    private static final String EXTRA_COMMENTS = "EXTRA_COMMENTS";
    private static final String TAG_COMMENT_FRAGMENT = "COMMENT_FRAGMENT";

    public static Intent getCommentsActivityIntent(Context context, long[] kids) {
        Intent intent = new Intent(context, CommentsViewActivity.class);
        intent.putExtra(EXTRA_COMMENTS, kids);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        displayHomeAsUpEnabled();
    }

    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void initFragment() {
        // find the retained fragment on activity restarts
        FragmentManager fm = getSupportFragmentManager();
        CommentsFragment commentsFragment = (CommentsFragment) fm.findFragmentByTag(TAG_COMMENT_FRAGMENT);

        // create the fragment and data the first time
        if (commentsFragment == null) {
            // add the fragment
            commentsFragment = new CommentsFragment();
            commentsFragment.setComments(getExtrasFromIntent());

            fm.beginTransaction().replace(R.id.container, commentsFragment, TAG_COMMENT_FRAGMENT).commit();
        }
    }

    private long[] getExtrasFromIntent() {
        long[] comentIds = null;
        if (getIntent() != null && getIntent().hasExtra(EXTRA_COMMENTS)) {
            comentIds = getIntent().getLongArrayExtra(EXTRA_COMMENTS);
        }
        return comentIds;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
