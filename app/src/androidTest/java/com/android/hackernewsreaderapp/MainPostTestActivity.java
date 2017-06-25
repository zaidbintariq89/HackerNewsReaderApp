package com.android.hackernewsreaderapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.hackernewsreaderapp.view.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.anything;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by zaidbintariq on 17/06/2017.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class MainPostTestActivity {

    @Rule
    public ActivityTestRule<MainActivity> rule  = new  ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.android.hackernewsreaderapp", appContext.getPackageName());
    }

    @Test
    public void testAProgressBarShouldGone() {
        final MainActivity activity = rule.getActivity();

        final View progress = activity.findViewById(R.id.progress_post);
        final View viewById = activity.findViewById(R.id.recyclerView);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                progress.setVisibility(View.GONE);
                viewById.setVisibility(View.VISIBLE);

                assertThat(progress.getVisibility(),is(View.GONE));
            }
        });

    }

    @Test
    public void testBListViewIsPresent() throws Exception {
        final MainActivity activity = rule.getActivity();

        final View progress = activity.findViewById(R.id.progress_post);
        final View viewById = activity.findViewById(R.id.recyclerView);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                progress.setVisibility(View.GONE);
                viewById.setVisibility(View.VISIBLE);

                assertThat(viewById,notNullValue());
                assertThat(viewById, instanceOf(RecyclerView.class));
                RecyclerView listView = (RecyclerView) viewById;

                RecyclerView.Adapter adapter = listView.getAdapter();
                assertThat(adapter, instanceOf(RecyclerView.Adapter.class));

                assertThat(viewById.getVisibility(),is(View.VISIBLE));
            }
        });
    }

    @UiThreadTest
    public void testCListViewShouldBindData() {
        onData(anything())
                .inAdapterView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()));
    }

    @UiThreadTest
    public void testDCommentsViewShouldVisible() {
        onData(anything())
                .inAdapterView(withId(R.id.recyclerView))
                .atPosition(0)
                .onChildView(withText("Comments"))
                .check(matches(isDisplayed()));
    }

    @UiThreadTest
    public void testECommentsViewShouldBeClickable() {

        onData(anything())
                .inAdapterView(withId(R.id.recyclerView))
                .atPosition(0)
                .onChildView(withText("Comments"))
                .perform(click());
    }
}
