package com.se319s18a9.util3d;

import com.google.firebase.auth.FirebaseAuth;
import com.se319s18a9.util3d.backend.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserUnitTests {

    //Global Variables
    private FirebaseAuth auth;
    //Tests

    //Pre- and Post- Test
    @Before
    public void initialize() {
        auth = FirebaseAuth.getInstance();

        User.getInstance().validateAndLogin("njcool@iastate.edu", "nathancool");
    }

    @After
    public void deinitialize() {
        User.getInstance().signOut();
    }

    //----------Create Account Tests----------



    //----------Login Tests----------
    @Test
    public void correct_User_correct_Pass() {
        //Should return true and a logged in message?
        assertTrue(User.getInstance().validateAndLogin("alex1@iastate.edu", "alexrichardson"));
        User.getInstance().signOut();
    }

    @Test
    public void correct_User_incorrect_Pass() {
        //Should return false and a message?
        assertFalse(User.getInstance().validateAndLogin("alex1@iastate.edu", "notpassword"));
    }

    @Test
    public void incorrect_User_correct_Pass() {
        //Should return false and a message?
        assertFalse(User.getInstance().validateAndLogin("alex2@iastate.edu", "notpassword"));
    }

    @Test
    public void incorrect_User_incorrect_Pass() {
        //Should return false and a message?
        assertFalse(User.getInstance().validateAndLogin("alex2@iastate.edu", "notpassword"));
    }

    @Test
    public void same_user_double_login_correct_Pass() {
        //Should return true and keep the user logged into the same account
        User.getInstance().validateAndLogin("alex1@iastate.edu", "alexrichardson");
        assertTrue(User.getInstance().validateAndLogin("alex1@iastate.edu", "alexrichardson"));
        User.getInstance().signOut();
    }

    @Test
    public void logged_in_switching_accounts_Fail() {
        //Should return false and keep the user logged into the same account
        assertFalse(User.getInstance().validateAndLogin("alex1@iastate.edu", "notpassword"));
    }


    @Test
    public void logged_in_switching_accounts_Success(){
        assertTrue(User.getInstance().validateAndLogin("alex1@iastate.edu", "alexrichardson"));
    }

    //----------Null Tests----------

    //----------Signout Test----------
    @Test
    public void log_out(){
        User.getInstance().signOut();
        assertFalse(User.getInstance().isAlreadyLoggedIn());
    }


    //----------Re-authentication Tests----------



    //----------Change Display-Name Tests----------



}
