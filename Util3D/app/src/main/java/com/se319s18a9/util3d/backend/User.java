package com.se319s18a9.util3d.backend;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private static final User instance = new User();
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    public User() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
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

    /**
     *
     * @return An array of length 1 containing the exception thrown by the database query.
     * Note that the only reason I am returning an array is so that I can pass a reference to an
     * exception that does not yet and may not ever actually exist
     */
    public Exception[] getMyPersonalProjects(final ArrayList<Project> projects, final Runnable callback){
        final DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference("/users/"+mAuth.getUid()+"/files");
        Exception[] exception = {null};
        tempRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot temp:dataSnapshot.getChildren()) {
                    //TODO: store filenames as values, not keys, check for null values
                    String filename = temp.getKey();
                    if(filename!=null&&!filename.equals("")) {
                        Task<StorageMetadata> storageMetadataTask = FirebaseStorage.getInstance().getReference("/users/"+mAuth.getUid()+"/files/"+filename).getMetadata();
                        //It's ok to wait here. This happens in background thread.
                        while(!storageMetadataTask.isComplete()){
                            try {
                                Thread.sleep(10);
                            }catch(InterruptedException e){

                            }
                        }
                        if(storageMetadataTask.isSuccessful()) {
                            StorageMetadata storageMetadata = storageMetadataTask.getResult();
                            Date created = new Date(storageMetadata.getCreationTimeMillis());
                            Date updated = new Date(storageMetadata.getUpdatedTimeMillis());
                            //TODO: store utility type in database, retrieve here
                            projects.add(new Project(filename, created, updated, null));
                        }
                        else{
                            exception[0] = new Exception("A file in database file list does not exist in storage");
                        }

                    }
                    else
                    {
                        exception[0] = new Exception("Blank or Null Filename Error");
                    }
                }
                callback.run();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                exception[0] = databaseError.toException();
                callback.run();
            }
        });
        return exception;
    }

    public CustomAsyncTask readMapFromFirebaseStorage(String givenPath, Map givenMap, final Runnable givenCallback, Activity givenActivity){
        //TODO: Allow upload operation to be cancelled. If operation stalls (for example, due to no internet), application will hang until firebase calls succeed
        class MapFromJSONTask extends CustomAsyncTask{
            boolean complete = false;
            boolean successful = false;
            Exception exception = null;
            String path;
            Map map;
            Runnable callback;
            Activity activity;

            public MapFromJSONTask(String givenPath, Map givenMap, final Runnable givenCallback, Activity givenActivity){
                path = givenPath;
                map = givenMap;
                callback = givenCallback;
                activity = givenActivity;
            }

            public boolean isComplete(){
                return complete;
            }

            public boolean isSuccessful(){
                return successful;
            }

            public Exception getException(){
                return exception;
            }

            public void run() {
                byte[] json;
                try{
                    final Task<byte[]> downloadTask = FirebaseStorage.getInstance().getReference("/users/"+mAuth.getUid()+"/files/"+path).getBytes(10000000);
                    while(!downloadTask.isComplete()){
                        Thread.sleep(100);
                    }
                    exception = downloadTask.getException();
                    if(exception!=null) {
                        throw exception;
                    }
                    map.readFromJSON(new String(downloadTask.getResult()));
                    successful = true;
                }catch(Exception e){
                    exception = e;
                    successful = false;
                }
                complete = true;
                activity.runOnUiThread(callback);
            }
        }

        MapFromJSONTask mapFromJSONTask = new MapFromJSONTask(givenPath, givenMap, givenCallback, givenActivity);
        mapFromJSONTask.start();
        return mapFromJSONTask;
    }

    public CustomAsyncTask writeMapToFirebaseStorage(String givenPath, Map givenMap, final Runnable givenCallback, Activity givenActivity){
        //TODO: Allow upload operation to be cancelled. If operation stalls (for example, due to no internet), application will hang until firebase calls succeed
        class MapToJSONTask extends CustomAsyncTask{
            boolean complete = false;
            boolean successful = false;
            Exception exception = null;
            String path;
            Map map;
            Runnable callback;
            Activity activity;

            public MapToJSONTask(String givenPath, Map givenMap, final Runnable givenCallback, Activity givenActivity){
                path = givenPath;
                map = givenMap;
                callback = givenCallback;
                activity = givenActivity;
            }

            public boolean isComplete(){
                return complete;
            }

            public boolean isSuccessful(){
                return successful;
            }

            public Exception getException(){
                return exception;
            }

            public void run() {
                byte[] json;
                try{
                    json = map.writeToJSON().getBytes();
                    DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference("/users/"+mAuth.getUid()+"/files/"+path);
                    final boolean[] databaseWriteCompleted = {false};
                    DatabaseReference.CompletionListener completionListener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError!=null) {
                                exception = databaseError.toException();
                            }
                            databaseWriteCompleted[0] = true;
                        }
                    };
                    tempRef.setValue(true, completionListener);
                    int debug = 0;
                    while(!databaseWriteCompleted[0]){
                        Thread.sleep(100);
                    }
                    if(exception!=null)
                    {
                        throw exception;
                    }
                    Task upload = FirebaseStorage.getInstance().getReference("/users/"+mAuth.getUid()+"/files/"+path).putBytes(json);
                    while(!upload.isComplete()){
                        Thread.sleep(100);
                    }
                    exception = upload.getException();
                    if(exception!=null) {
                        throw exception;
                    }
                    successful = true;
                }catch(Exception e){
                    exception = e;
                    successful = false;
                }
                complete = true;
                activity.runOnUiThread(callback);
            }
        }

        MapToJSONTask mapToJSONTask = new MapToJSONTask(givenPath, givenMap, givenCallback, givenActivity);
        mapToJSONTask.start();
        return mapToJSONTask;
    }
}