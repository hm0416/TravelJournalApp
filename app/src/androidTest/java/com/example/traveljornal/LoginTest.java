package com.example.traveljornal;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;

import android.content.ComponentName;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityScenarioRule activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    //tests that after login button is clicked, user will be taken to the dashboard if credentials were correct
    @Test
    public void takeToDashboardAfterLogin() throws Exception {
        onView(withId(R.id.usernameTextField)).perform(replaceText("Admin"));
        onView(withId(R.id.passTextField)).perform(replaceText("12345"));
        onView(withId(R.id.loginButton)).perform(click());
        //checks to see if map view button is visible - if yes, then successfully went to dashboard screen
        onView(withId(R.id.mapButton)).check(matches(isDisplayed()));

    }

    //user enters wrong credentials so won't be taken to dashboard
    @Test
    public void failedLogin() throws Exception {
        onView(withId(R.id.usernameTextField)).perform(replaceText("NotAUser"));
        onView(withId(R.id.passTextField)).perform(replaceText("12345"));
        onView(withId(R.id.loginButton)).perform(click());
        //checks to see if map view button is visible - if not, then user entered wrong credentials and is not taken to the dashboard
        onView(withId(R.id.mapButton)).check(doesNotExist());
    }


}
