package com.android.hackernewsreaderapp.data;

import com.android.hackernewsreaderapp.model.PostModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zaidbintariq on 15/06/2017.
 */

public class MockDataGenrator {

    public static List<Long> getStoryIds() {
        List<Long> ids = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            ids.add((long) i);
        }
        return ids;
    }

    public static PostModel getPostModel() {
        PostModel postModel = new PostModel();
        postModel.setId(1L);
        postModel.setBy("Zaid");
        postModel.setTitle("Testing post");
        postModel.setText("Testing post");
        postModel.setTime(1497671284L);
        postModel.setUrl("http://www.google.com");
        long[] kids = {2L,3L,4L,5L,6L};
        postModel.setKids(kids);
        postModel.postType = PostModel.PostType.STORY;

        return postModel;
    }
}
