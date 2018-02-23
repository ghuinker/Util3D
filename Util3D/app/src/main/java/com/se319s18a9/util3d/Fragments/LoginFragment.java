package com.se319s18a9.util3d.Fragments;

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

import com.se319s18a9.util3d.R;
import com.se319s18a9.util3d.backend.User;

public class LoginFragment extends Fragment implements View.OnClickListener {

    OnSuccessfulLoginListener mCallback;

    private EditText usernameEditText;

    private EditText passwordEditText;
    Button loginButton;
    Button forgotPasswordButton;

    Button createAccountButton;

    public interface OnSuccessfulLoginListener {
        void onSuccessfulLogin();
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
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize EditTexts, and Buttons

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
    public void onResume(){
        super.onResume();
        if(User.getInstance().isAlreadyLoggedIn())
        {
            mCallback.onSuccessfulLogin();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_login_button_login:
                String username = this.getEditTextValue(usernameEditText);
                String password = this.getEditTextValue(passwordEditText);

                if (User.getInstance().validateAndLogin(username, password)) {
                    mCallback.onSuccessfulLogin();
                } else {
                    Toast.makeText(this.getContext(), R.string.s_fragment_login_errorMessage_invalidCredentials, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragment_login_button_forgotPassword:
                // TODO

                LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
                final View dialogView = layoutInflater.inflate(R.layout.dialog_forgotpassword, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this.getContext());
                alertDialogBuilder.setTitle(getString(R.string.s_dialog_forgotPassword_title));
                alertDialogBuilder.setView(dialogView);
                alertDialogBuilder
                        .setPositiveButton(R.string.s_dialog_forgotPassword_button_sendEmail,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {}})
                        .setNegativeButton(R.string.s_dialog_forgotPassword_button_cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                alertDialog.getButton(alertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        try {
                            User.getInstance().sendPasswordResetEmail(((EditText) dialogView.findViewById(R.id.dialog_forgotPassword_editText_email)).getText().toString());
                            Toast.makeText(v.getContext(), R.string.s_dialog_forgotPassword_successMessage, Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.fragment_login_button_createAccount:
                Fragment createAccountFragment = new CreateAccountFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_login_frameLayout_root, createAccountFragment);
                fragmentTransaction.addToBackStack(null).commit();
        }
    }

    private String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }
}
