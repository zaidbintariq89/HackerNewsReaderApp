package com.android.hackernewsreaderapp;

import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.hackernewsreaderapp.view.CommentsViewActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertThat;

/**
 * Created by zaidbintariq on 25/06/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class CommentsTestActivity {

    @Rule
    public ActivityTestRule<CommentsViewActivity> rule  = new  ActivityTestRule<>(
            CommentsViewActivity.class);


    @Test
    public void activityShouldBeLaunch() {
        onView(withText("Comments"))
                .check(matches(isDisplayed()));
    }

    @Test
    public void ensureListViewIsPresent() throws Exception {
        CommentsViewActivity activity = rule.getActivity();
        View viewById = activity.findViewById(R.id.recyclerView);

        assertThat(viewById,notNullValue());
        assertThat(viewById, instanceOf(RecyclerView.class));
        RecyclerView listView = (RecyclerView) viewById;

        RecyclerView.Adapter adapter = listView.getAdapter();
        assertThat(adapter, instanceOf(RecyclerView.Adapter.class));

        assertThat(viewById.getVisibility(),is(View.VISIBLE));
    }

    @Test
    public void listViewShouldVisible() {
        CommentsViewActivity activity = rule.getActivity();

        View viewById = activity.findViewById(R.id.recyclerView);

        assertThat(viewById.getVisibility(),is(View.VISIBLE));
    }

    @UiThreadTest
    public void checkDataIsBindInListView() {

        onData(anything())
                .inAdapterView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()));
    }

    @UiThreadTest
    public void replyViewShouldVisible() {

        onData(anything())
                .inAdapterView(withId(R.id.recyclerView))
                .atPosition(0)
                .onChildView(withText("Reply"))
                .check(matches(isDisplayed()));
    }
}
