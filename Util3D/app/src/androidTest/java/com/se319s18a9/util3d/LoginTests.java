package com.se319s18a9.util3d;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTests {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginTests() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.fragment_login_editText_username),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("alex1"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.fragment_login_editText_username), withText("alex1"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("alex1@iastate.com"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.fragment_login_editText_username), withText("alex1@iastate.com"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.fragment_login_editText_password),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.fragment_login_editText_password),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("wrong"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.fragment_login_button_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.fragment_login_editText_password), withText("wrong"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("alexrichardson"));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.fragment_login_editText_password), withText("alexrichardson"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText7.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.fragment_login_button_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.fragment_login_editText_password), withText("alexrichardson"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText8.perform(click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.fragment_login_editText_password), withText("alexrichardson"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("wrong"));

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.fragment_login_editText_password), withText("wrong"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText10.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.fragment_login_editText_username), withText("alex1@iastate.com"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("alex1@iastate.edu"));

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.fragment_login_editText_username), withText("alex1@iastate.edu"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText12.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.fragment_login_button_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.fragment_login_editText_password), withText("wrong"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("alexrichardson"));

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.fragment_login_editText_password), withText("alexrichardson"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText14.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.fragment_login_button_login), withText("Login"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatButton4.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.fragment_dashboard_button_logout), withText("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_dashboard_linearLayout_root),
                                        1),
                                3),
                        isDisplayed()));
        appCompatButton5.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
