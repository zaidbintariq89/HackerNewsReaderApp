package com.android.hackernewsreaderapp;

import android.view.View;

import com.android.hackernewsreaderapp.data.MockDataGenrator;
import com.android.hackernewsreaderapp.data.network.ServerApi;
import com.android.hackernewsreaderapp.model.PostModel;
import com.android.hackernewsreaderapp.viewmodel.MainViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;
import static org.junit.Assert.assertEquals;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.doReturn;

/**
 * Created by zaidbintariq on 15/06/2017.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21, manifest = Config.NONE)
public class MainViewModelTest {

    @Mock private ServerApi serverApi;

    private MainViewModel mainViewModel;
    private MainApplication mainApplication;

    @Before
    public void setUpMainViewModelTest() {
        // inject the mocks
        MockitoAnnotations.initMocks(this);

        mainApplication = (MainApplication) RuntimeEnvironment.application;
        mainApplication.setScheduler(Schedulers.trampoline());
        mainApplication.setServerApi(serverApi);

        mainViewModel = new MainViewModel(mainApplication);
    }

    @Test public void ensureTheViewsAreInitializedCorrectly() throws Exception {
        mainViewModel.initializeViews();
        assertEquals(View.GONE, mainViewModel.postRecycler.get());
        assertEquals(View.VISIBLE, mainViewModel.postProgress.get());
    }

    @Test
    public void testFetchTopStories() throws Exception {
        List<Long> ids = MockDataGenrator.getStoryIds();
        doReturn(Observable.just(ids)).when(serverApi).getTopStories();
    }

    @Test
    public void testStoriesIdsExist() {
        List<Long> ids = MockDataGenrator.getStoryIds();
        mainViewModel.setStoriesIds(ids);

        assertEquals(ids,mainViewModel.getStoriesIds());
    }

    @Test
    public void testGetStoriesDetails() throws Exception {
        PostModel postModel = MockDataGenrator.getPostModel();
        doReturn(Observable.just(postModel)).when(serverApi).getStoryDetailRX(1L);
    }
}
