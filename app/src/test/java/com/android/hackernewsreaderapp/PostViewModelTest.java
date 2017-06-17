package com.android.hackernewsreaderapp;

import android.content.Context;
import android.content.Intent;
import android.databinding.Observable;
import android.view.View;

import com.android.hackernewsreaderapp.data.MockDataGenrator;
import com.android.hackernewsreaderapp.data.network.ServerApi;
import com.android.hackernewsreaderapp.data.utility.CommonUtility;
import com.android.hackernewsreaderapp.model.PostModel;
import com.android.hackernewsreaderapp.viewmodel.PostViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created by zaidbintariq on 17/06/2017.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = Config.NONE)
public class PostViewModelTest {

    @Mock
    private ServerApi serverApi;

    private PostViewModel postViewModel;
    private MainApplication mainApplication;

    @Before
    public void setUpMainViewModelTest() {
        // inject the mocks
        MockitoAnnotations.initMocks(this);

        mainApplication = (MainApplication) RuntimeEnvironment.application;
        mainApplication.setScheduler(Schedulers.trampoline());
        mainApplication.setServerApi(serverApi);

        postViewModel = new PostViewModel(mainApplication,MockDataGenrator.getPostModel());
    }

    @Test
    public void shouldGetPostTitle() throws Exception {
        PostModel postModel = MockDataGenrator.getPostModel();

        assertEquals(postModel.getTitle(), postViewModel.getPostTitle());
    }

    @Test
    public void shouldGetUpdateAt() throws Exception {
        PostModel postModel = MockDataGenrator.getPostModel();

        assertEquals(CommonUtility.getTimeEllapseDifference(postModel.getTime()), postViewModel.getUpdatedAt());
    }

    @Test public void shouldStartCommentsActivityOnItemClick() throws Exception {
        Context mockContext = mock(Context.class);
        PostViewModel postViewModel = new PostViewModel(mockContext,MockDataGenrator.getPostModel());
        postViewModel.onItemClick(new View(mainApplication));
        verify(mockContext).startActivity(any(Intent.class));
    }

    @Test public void shouldNotifyPostChangeWhenSetComment() throws Exception {

        PostViewModel postViewModel = new PostViewModel(mainApplication,MockDataGenrator.getPostModel());
        Observable.OnPropertyChangedCallback mockCallback =
                mock(Observable.OnPropertyChangedCallback.class);
        postViewModel.addOnPropertyChangedCallback(mockCallback);
        postViewModel.setPost(MockDataGenrator.getPostModel());
        verify(mockCallback).onPropertyChanged(any(Observable.class), anyInt());
    }
}
