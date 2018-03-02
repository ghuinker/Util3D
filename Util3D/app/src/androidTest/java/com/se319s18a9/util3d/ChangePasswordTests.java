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
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChangePasswordTests {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void changePasswordTests() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.fragment_login_editText_username),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("alex1@iastate.edi"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.fragment_login_editText_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("alexrichardson"), closeSoftKeyboard());

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

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.fragment_login_editText_username), withText("alex1@iastate.edi"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("alex1@iastate.edu"));

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.fragment_login_editText_username), withText("alex1@iastate.edu"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.design.widget.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(closeSoftKeyboard());

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

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.fragment_dashboard_button_settings), withText("Settings"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_dashboard_linearLayout_root),
                                        1),
                                2),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.fragment_settings_button_changePassword), withText("Change Password"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_main_frameLayout_root),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.dialog_changePassword_editText_currentPassword),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("alex"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_newPassword),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("alexrich"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_repeatNewPassword),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("alexrich"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(android.R.id.button1), withText("Change Password"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_currentPassword), withText("alex"),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("alexrichardson"));

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_currentPassword), withText("alexrichardson"),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_newPassword), withText("alexrich"),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("alexric"));

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_newPassword), withText("alexric"),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText7.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Change Password"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_newPassword), withText("alexric"),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("alexrich"));

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_newPassword), withText("alexrich"),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText9.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_repeatNewPassword), withText("alexrich"),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText10.perform(longClick());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_repeatNewPassword), withText("alexrich"),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText(""));

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_repeatNewPassword),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText12.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(android.R.id.button1), withText("Change Password"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_newPassword), withText("alexrich"),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText(""));

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_newPassword),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText14.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(android.R.id.button1), withText("Change Password"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_newPassword),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatEditText15.perform(replaceText("alexrich"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.dialog_changePassword_editText_repeatNewPassword),
                        childAtPosition(
                                allOf(withId(R.id.dialog_changePassword_linearLayout_main),
                                        childAtPosition(
                                                withId(R.id.custom),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText16.perform(replaceText("alexrich"), closeSoftKeyboard());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(android.R.id.button1), withText("Change Password"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.buttonPanel),
                                        0),
                                3)));
        appCompatButton9.perform(scrollTo(), click());

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
