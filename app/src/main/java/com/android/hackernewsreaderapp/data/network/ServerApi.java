package com.android.hackernewsreaderapp.data.network;


import com.android.hackernewsreaderapp.data.constants.UrlConstants;
import com.android.hackernewsreaderapp.model.PostModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zaid on 03/03/2016.
 */
public interface ServerApi {

    @GET(UrlConstants.GET_TOP_STORIES)
    Observable<List<Long>> getTopStories();

    @GET(UrlConstants.GET_DETAILS)
    Observable<PostModel> getStoryDetailRX(@Path("id") long id);
}


