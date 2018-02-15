package com.se319s18a9.util3d;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment implements View.OnClickListener {

    OnSuccessfulLoginListener mCallback;

    private EditText usernameEditText;
    private EditText passwordEditText;

    Button loginButton;
    Button forgotPasswordButton;
    Button createAccountButton;

    public interface OnSuccessfulLoginListener {
        void onSuccessfulLogin(String username, String password);
    }

    public LoginFragment() {
        // Empty constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnSuccessfulLoginListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSuccessfulLoginListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize EditTexts and Buttons

        usernameEditText = v.findViewById(R.id.fragment_login_editText_username);
        passwordEditText = v.findViewById(R.id.fragment_login_editText_password);

        loginButton = v.findViewById(R.id.fragment_login_button_login);
        loginButton.setOnClickListener(this);

        forgotPasswordButton = v.findViewById(R.id.fragment_login_button_forgotPassword);
        forgotPasswordButton.setOnClickListener(this);

        createAccountButton = v.findViewById(R.id.fragment_login_button_createAccount);
        createAccountButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fragment_login_button_login:
                // TODO
//                Toast.makeText(this.getContext(), R.string.s_fragment_login_debug_login, Toast.LENGTH_SHORT).show(); // DEBUG
                String username = this.getEditTextValue(usernameEditText);
                String password = this.getEditTextValue(passwordEditText);
                if(validateUserCredentials(username, password)) {
                    mCallback.onSuccessfulLogin(username, password);
                } else {
                    Toast.makeText(this.getContext(), R.string.s_fragment_login_errorMessage_invalidCredentials, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragment_login_button_forgotPassword:
                // TODO
//                Toast.makeText(this.getContext(), R.string.s_fragment_login_debug_forgotPassword, Toast.LENGTH_SHORT).show(); // DEBUG

                LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
                View dialogView = layoutInflater.inflate(R.layout.dialog_forgotpassword, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
                alertDialogBuilder.setTitle(getString(R.string.s_dialog_forgotPassword_title));
                alertDialogBuilder.setView(dialogView);
                final EditText userInput = dialogView.findViewById(R.id.dialog_forgotPassword_editText_securityAnswer);
                alertDialogBuilder
                        .setPositiveButton("Verify",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // hello
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
            case R.id.fragment_login_button_createAccount:
                // TODO
                //Toast.makeText(this.getContext(), R.string.s_fragment_login_debug_createAccount, Toast.LENGTH_SHORT).show(); // DEBUG
                Fragment createAccountFragment = new CreateAccountFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_login_frameLayout_main, createAccountFragment);
                fragmentTransaction.addToBackStack(null).commit();
        }
    }

    private String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }

    private boolean validateUserCredentials(String username, String password) {
        // TODO: Validate from local storage or Firebase

        // TODO: TEST (REMOVE) =====

        if((username.equals("")) || (password.equals(""))) {
            return false;
        }

        // TODO: TEST (REMOVE) =====

        return true;
    }
}
