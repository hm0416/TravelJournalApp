package com.example.traveljornal;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class LangChangeTest {

    @Rule
    public ActivityScenarioRule activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void changeLangOnButtonPressLoginScreen() throws Exception {
        onView(withId(R.id.langBtn)).perform(click()); //clicks the change lang button on main activity screen
        onView(withId(R.id.welcomeTxt)).check(matches(withText("Bienvenido!"))); //checks to see if username text in the username field box changed to spanish
    }

    @Test
    public void changeLangOnButtonPressRegisterScreen() throws Exception {
        onView(withId(R.id.registerButton)).perform(click());
        onView(withId(R.id.langBtnReg)).perform(click()); //clicks the change lang button on reg activity screen
        onView(withId(R.id.regTextView)).check(matches(withText("¡Hola nuevo \nusuario!")));
    }

    @Test
    public void changeLangOnButtonPressUpdateScreen() throws Exception {
        onView(withId(R.id.updateButton)).perform(click());
        onView(withId(R.id.langBtnUpdate)).perform(click()); //clicks the change lang button on update activity screen
        onView(withId(R.id.updateTxtView)).check(matches(withText("Actualiza contraseña")));
    }

    @Test
    public void changeLangOnButtonPressDashboardScreen() throws Exception {
        onView(withId(R.id.usernameTextField)).perform(replaceText("Admin"));
        onView(withId(R.id.passTextField)).perform(replaceText("12345"));
        onView(withId(R.id.loginButton)).perform(click());
        onView(withId(R.id.langBtnDash)).perform(click()); //clicks the change lang button on dashboard activity screen
        onView(withId(R.id.instructionsTxtView)).check(matches(withText("Instrucciones de vista de mapa: \n- Haga clic en el botón 'Vista de mapa' para ver el mapa mundial \n- ¡Haga clic en cualquier país que haya visitado y se colocará un marcador! \n")));
    }

}
