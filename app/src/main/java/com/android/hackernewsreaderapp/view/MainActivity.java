package com.android.hackernewsreaderapp.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.android.hackernewsreaderapp.R;
import com.android.hackernewsreaderapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_POST_FRAGMENT = "POST_FRAGMENT";

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataBinding();
        setSupportActionBar(binding.toolbar);
        initFragment();
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    protected void initFragment() {
        // find the retained fragment on activity restarts
        FragmentManager fm = getSupportFragmentManager();
        PostFragment postFragment = (PostFragment) fm.findFragmentByTag(TAG_POST_FRAGMENT);

        // create the fragment and data the first time
        if (postFragment == null) {
            // add the fragment
            postFragment = new PostFragment();
            fm.beginTransaction().replace(R.id.container, postFragment, TAG_POST_FRAGMENT).commit();
        }
    }
}
