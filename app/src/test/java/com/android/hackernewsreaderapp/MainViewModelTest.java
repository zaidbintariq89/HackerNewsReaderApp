package com.android.hackernewsreaderapp;

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

    @Before
    public void setUpMainViewModelTest() {
        // inject the mocks
        MockitoAnnotations.initMocks(this);

        MainApplication mainApplication = (MainApplication) RuntimeEnvironment.application;
        mainApplication.setScheduler(Schedulers.trampoline());
        mainApplication.setServerApi(serverApi);

        mainViewModel = new MainViewModel(mainApplication);
    }

    @Test
    public void testFetchTopStories() throws Exception {
        List<Long> ids = MockDataGenrator.getStoryIds();
        doReturn(Observable.just(ids)).when(serverApi).getTopStories();
    }

    @Test
    public void testIsStoriesIdExists() {
        assert mainViewModel.getStoriesIds() != null;
    }

    @Test
    public void testBindStoriesData() throws Exception {
        PostModel postModel = MockDataGenrator.getPostModel();
        doReturn(Observable.just(postModel)).when(serverApi).getStoryDetailRX(1L);
    }
}
