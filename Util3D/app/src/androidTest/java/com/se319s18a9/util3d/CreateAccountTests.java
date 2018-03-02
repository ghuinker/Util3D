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
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CreateAccountTests {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void createAccountTests() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.fragment_login_button_createAccount), withText("Create Account"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.fragment_createAccount_button_create), withText("Create"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.fragment_createAccount_editText_username),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("alex1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_username), withText("alex1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("alexrichardson"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.fragment_createAccount_button_create), withText("Create"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_username), withText("alex1"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("alex1@iastate.edu"));

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_username), withText("alex1@iastate.edu"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withText("Clear"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password), withText("alexrichardson"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("alexrichardso"));

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password), withText("alexrichardso"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText8.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password), withText("alexrichardso"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText9.perform(click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password), withText("alexrichardso"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText(""));

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText11.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.fragment_createAccount_button_create), withText("Create"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText12.perform(click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText13.perform(click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText14.perform(click());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText15.perform(click());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText16.perform(replaceText("alex"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.fragment_createAccount_button_create), withText("Create"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password), withText("alex"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText17.perform(click());

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password), withText("alex"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText18.perform(replaceText("alexrichardson"));

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password), withText("alexrichardson"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText19.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.fragment_createAccount_button_create), withText("Create"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton7.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.fragment_dashboard_button_logout), withText("Logout"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_dashboard_linearLayout_root),
                                        1),
                                3),
                        isDisplayed()));
        appCompatButton8.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.fragment_login_button_createAccount), withText("Create Account"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login_linearLayout_root),
                                        childAtPosition(
                                                withId(R.id.activity_login_frameLayout_root),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_username),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatEditText20.perform(replaceText("alex1@iastate.edu"), closeSoftKeyboard());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.fragment_createAccount_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatEditText21.perform(replaceText("alexrich"), closeSoftKeyboard());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.fragment_createAccount_button_create), withText("Create"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                0),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.fragment_createAccount_button_cancel), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                1),
                        isDisplayed()));
        appCompatButton11.perform(click());

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
