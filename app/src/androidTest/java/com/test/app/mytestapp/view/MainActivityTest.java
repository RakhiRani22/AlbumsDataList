package com.test.app.mytestapp.view;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.test.app.mytestapp.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Before
    public void init(){
        activityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

        onView(withId(R.id.refresh_data));
        onView(withId(R.id.listViewAlbums));
    }

    @Test
    public void TestFab(){
        //onView(withId(R.id.listViewAlbums)).perform(click());
        /*onView(allOf(withId(android.support.design.R.id.snackbar_text), withText("This Test Works")))
                .check(matches(isDisplayed()));*/
    }

    @Before
    public void disableRecyclerViewAnimations() {
        // Disable RecyclerView animations
        EspressoTestUtil.disableAnimations(mActivityRule);
    }
}
