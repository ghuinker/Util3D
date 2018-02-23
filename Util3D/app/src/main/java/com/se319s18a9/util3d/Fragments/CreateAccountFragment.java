package com.se319s18a9.util3d.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.se319s18a9.util3d.R;
import com.se319s18a9.util3d.backend.User;

public class CreateAccountFragment extends Fragment implements View.OnClickListener {

    OnAccountCreatedListener mCallback;

    private EditText usernameEditText;
    private EditText passwordEditText;

    Button createButton;
    Button cancelButton;

    public interface OnAccountCreatedListener {
        void onAccountCreated(String username, String password);
    }

    public CreateAccountFragment() {
        // Empty constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnAccountCreatedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnAccountCreatedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_createaccount, container, false);

        // Initialize EditTexts and Buttons

        usernameEditText = v.findViewById(R.id.fragment_createAccount_editText_username);
        passwordEditText = v.findViewById(R.id.fragment_createAccount_editText_password);

        createButton = v.findViewById(R.id.fragment_createAccount_button_create);
        createButton.setOnClickListener(this);

        cancelButton = v.findViewById(R.id.fragment_createAccount_button_cancel);
        cancelButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_createAccount_button_create:
                //Toast.makeText(this.getContext(), R.string.s_fragment_createAccount_debug_create, Toast.LENGTH_SHORT).show(); // DEBUG

                String username = this.getEditTextValue(usernameEditText);
                String password = this.getEditTextValue(passwordEditText);

                try{
                    User.getInstance().createAccount(username, password);
                    //TODO: verify this is correct way to do this
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }catch(Exception e){
                    Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.fragment_createAccount_button_cancel:
                // TODO: Discard credentials and return to LoginFragment
                //Toast.makeText(this.getContext(), R.string.s_fragment_createAccount_debug_cancel, Toast.LENGTH_SHORT).show(); // DEBUG
                getActivity().onBackPressed();
                break;
        }
    }

    private String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }
}