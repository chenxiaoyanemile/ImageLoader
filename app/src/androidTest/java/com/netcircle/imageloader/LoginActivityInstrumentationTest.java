package com.netcircle.imageloader;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * UI Test
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityInstrumentationTest {

    private static final String STRING_TO_BE_TYPED = "UITest";

    @Rule
    public ActivityTestRule<RegisteredActivity> mActivityRule = new ActivityTestRule<>(
            RegisteredActivity.class);

    @Test
    public void getData(){
        onView(withId(R.id.input_name)).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard()); //line 1

        onView(withText("android")).perform(click()); //line 2

        String expectedText = "Hello, " + STRING_TO_BE_TYPED + "!";
        onView(withId(R.id.btn_registered)).check(matches(withText(expectedText))); //line 3
    }


}
