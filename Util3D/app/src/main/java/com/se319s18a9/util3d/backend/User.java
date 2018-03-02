package com.se319s18a9.util3d.backend;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.UserProfileChangeRequest;

public class User {
    private static final User instance = new User();
    private FirebaseAuth mAuth;

    private User() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static User getInstance(){
        return instance;
    }

    /**
     * Check if a user is logged in
     *
     * @return True if a user is already logged in, false if no user is logged in
     */
    public boolean isAlreadyLoggedIn(){
        return mAuth.getCurrentUser() != null;
    }

    /**
     * Login a user
     *
     * @param email
     * @param password
     * @return True if login succeeds, false if login fails
     */
    public boolean validateAndLogin(String email, String password) {
        //If user is already logged in, show that login has succeeded.
        //TODO: It may be better to log user out first if user is attempting to switch between accounts
        if(isAlreadyLoggedIn())
        {
            return true;
        }
        //FireBase sign in method cannot handle null or empty strings.
        else if((email!=null)&&(password!=null)&&(!email.isEmpty())&&(!password.isEmpty())){
            Task<AuthResult> loginTask = mAuth.signInWithEmailAndPassword(email, password);
            //Wait for login to complete before checking if it was successful.
            //This may cause app to hang if connection to Firebase is slow.
            while (!loginTask.isComplete());
            return loginTask.isSuccessful();
        }
        else
        {
            return false;
        }
    }

    /**
     * Sends an email to the user to allow them to reset their password.
     *
     * @param email
     */
    public void sendPasswordResetEmail(String email){
        if((email!=null)&&(!email.isEmpty())) {
            mAuth.sendPasswordResetEmail(email);
        }
    }

    /**
     * Creates a new account
     *
     * @param email
     * @param password
     * @throws Exception Contains message detailing cause of error
     */
    public void createAccount(String email, String password) throws Exception {
        if((email!=null)&&(password!=null)&&(!email.isEmpty())&&(!password.isEmpty())) {
            Task<AuthResult> createTask = mAuth.createUserWithEmailAndPassword(email, password);
            while(!createTask.isComplete());
            if(!createTask.isSuccessful())
            {
                if(createTask.getException() instanceof FirebaseAuthWeakPasswordException) {
                    throw new Exception("Weak password");
                }
                else if(createTask.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                    throw new Exception("Invalid email");
                }
                else if(createTask.getException() instanceof FirebaseAuthUserCollisionException) {
                    throw new Exception("Account already exists");
                }
                else {
                    throw new Exception("Unknown error");
                }
            }
        }
        else {
            throw new Exception("Email or password is blank");
        }
    }

    /**
     * Change account email address
     * @param email
     * @throws Exception Contains message detailing cause of error
     */
    public void changeEmail(String email) throws Exception {
        if (isAlreadyLoggedIn())
        {
            if(email!=null&&!email.isEmpty()) {
                Task changeTask = mAuth.getCurrentUser().updateEmail(email);
                while (!changeTask.isComplete()) ;
                if (!changeTask.isSuccessful()) {
                    if (changeTask.getException() instanceof FirebaseAuthInvalidUserException) {
                        throw new Exception("Credentials invalid");
                    } else if (changeTask.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        throw new Exception("Invalid email");
                    } else if (changeTask.getException() instanceof FirebaseAuthUserCollisionException) {
                        throw new Exception("Account with that email already exists");
                    } else if (changeTask.getException() instanceof FirebaseAuthRecentLoginRequiredException) {
                        throw new Exception("Re-authenticate. Session is too old.");
                    } else {
                        throw new Exception("Unknown error");
                    }
                }
            }
            else
            {
                throw new Exception("Email can't be blank");
            }
        }
        else {
            throw new Exception("No user logged in");
        }
    }

    /**
     * Re-authenticate user. Used before changing email or password to ensure new session.
     * @param password
     * @throws Exception Message details cause of error
     */
    public void reauthenticate(String password) throws Exception{
        if(isAlreadyLoggedIn()) {
            if(password!=null&&!password.isEmpty()) {
                Task authTask = mAuth.getCurrentUser().reauthenticate(EmailAuthProvider.getCredential(getEmail(), password));
                while (!authTask.isComplete()) ;
                if(!authTask.isSuccessful())
                {
                    throw new Exception("Authentication failed");
                }
            }
            else
            {
                throw new Exception("Current password cannot be empty");
            }
        }
        else {
            throw new Exception("No user logged in");
        }
    }

    public String getUserID(){
       return mAuth.getCurrentUser().getUid();
    }

    /**
     * Change username. Returns a boolean since the firebase method does not throw any exceptions.
     * @param displayName
     * @throws Exception Contains message detailing cause of error
     */
    public void changeDisplayName(String displayName) throws Exception{
        if (isAlreadyLoggedIn()) {
            //TODO: determine if blank display name crashes app
            if(displayName!=null&&!displayName.isEmpty()) {
                UserProfileChangeRequest.Builder change = new UserProfileChangeRequest.Builder();
                Task changeTask = mAuth.getCurrentUser().updateProfile(change.setDisplayName(displayName).build());
                while (!changeTask.isComplete());
                if(!changeTask.isSuccessful())
                {
                    throw new Exception("Error changing username");
                }
            }
            else
            {
                throw new Exception("Username can't be empty");
            }
        } else {
            throw new Exception("No user logged in");
        }
    }

    /**
     * Change the password of the currently logged in user.
     *
     * @param password
     * @throws Exception Contains message detailing cause of error
     */
    public void changePassword(String password) throws Exception{
        if (isAlreadyLoggedIn())
        {
            //TODO: Determine if blank password crashes app
            if(password!=null&&!password.isEmpty()) {
                Task changeTask = mAuth.getCurrentUser().updatePassword(password);
                while (!changeTask.isComplete()) ;
                if (!changeTask.isSuccessful()) {
                    if (changeTask.getException() instanceof FirebaseAuthInvalidUserException) {
                        throw new Exception("Credentials invalid");
                    } else if (changeTask.getException() instanceof FirebaseAuthWeakPasswordException) {
                        throw new Exception("Weak password");
                    } else if (changeTask.getException() instanceof FirebaseAuthRecentLoginRequiredException) {
                        throw new Exception("Re-authenticate. Session is too old.");
                    } else {
                        throw new Exception("Unknown error");
                    }
                }
            }
            else {
                throw new Exception("Password can't be empty");
            }
        }
        else {
            throw new Exception("No user logged in");
        }
    }

    /**
     * Logs out the current user
     */
    public void signOut() {
        mAuth.signOut();
    }

    /**
     * Get the username of the current user
     * @return username of the current user, Blank String if no user is logged in
     */
    public String getDisplayName(){
        return isAlreadyLoggedIn() ? mAuth.getCurrentUser().getDisplayName() : "";
    }

    /**
     * Get the email of the current user
     * @return email of the current user, Blank String if no user is logged in
     */
    public String getEmail(){
        return isAlreadyLoggedIn() ? mAuth.getCurrentUser().getEmail() : "";
    }

    /**
     * Completely deletes the user account from firebase.
     *
     * @throws Exception Message contains error information
     */
    public void deleteAccount() throws Exception{
        if (isAlreadyLoggedIn())
        {
            Task changeTask = mAuth.getCurrentUser().delete();
            while (!changeTask.isComplete());
            if (!changeTask.isSuccessful()) {
                if (changeTask.getException() instanceof FirebaseAuthInvalidUserException) {
                    throw new Exception("Credentials invalid");
                } else if (changeTask.getException() instanceof FirebaseAuthRecentLoginRequiredException) {
                    throw new Exception("Re-authenticate. Session is too old.");
                } else {
                    throw new Exception("Unknown error. Session Probably too old.");
                }
            }
        }
        else {
            throw new Exception("No user logged in");
        }
    }
}