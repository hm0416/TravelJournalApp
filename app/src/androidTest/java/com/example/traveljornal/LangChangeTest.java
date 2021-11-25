package com.example.traveljornal;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

//@RunWith(AndroidJUnit4.class)
public class LangChangeTest {

    @Rule
    public ActivityScenarioRule activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void changeLangOnButtonPressLoginScreen() throws Exception {
        onView(withId(R.id.langBtn)).perform(click()); //clicks the change lang button on main activity screen
        onView(withId(R.id.welcomeTxt)).check(matches(withText("Bienvenido!"))); //checks to see if username text in the username field box changed to spanish
    }


}
