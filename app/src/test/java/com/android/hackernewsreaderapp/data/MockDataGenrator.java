package com.android.hackernewsreaderapp.data;

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
}
