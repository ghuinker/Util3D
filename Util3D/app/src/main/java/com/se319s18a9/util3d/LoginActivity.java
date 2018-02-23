package com.se319s18a9.util3d;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.se319s18a9.util3d.Fragments.CreateAccountFragment;
import com.se319s18a9.util3d.Fragments.LoginFragment;
import com.se319s18a9.util3d.backend.User;

public class LoginActivity extends AppCompatActivity implements
        LoginFragment.OnSuccessfulLoginListener,
        CreateAccountFragment.OnAccountCreatedListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        // TODO: Set title programmatically based on active fragment

        selectStartingFragment();
    }

    public void selectStartingFragment() {
        Fragment startingFragment = new LoginFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_login_frameLayout_root, startingFragment);
        fragmentTransaction.commit();
    }

    public void onSuccessfulLogin() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    public void onAccountCreated(String name, String password) {
        onSuccessfulLogin();
        //Toast.makeText(this, name + " : " + password + " - created", Toast.LENGTH_SHORT).show();
    }
}