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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.se319s18a9.util3d.R;
import com.se319s18a9.util3d.backend.User;
import com.google.firebase.database.FirebaseDatabase;
import com.se319s18a9.util3d.database.UserInfo;

public class CreateAccountFragment extends Fragment implements View.OnClickListener {

    OnAccountCreatedListener mCallback;

    Button createButton;
    Button cancelButton;

    private EditText fullName;
    private EditText companyName;
    private EditText occupation;
    private EditText phoneNumber;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public interface OnAccountCreatedListener {
        void onAccountCreated(String username, String password);
    }

    public CreateAccountFragment() {
        // Empty constructor
    }

    public void saveUser() {

        databaseReference = FirebaseDatabase.getInstance().getReference();

//        String name = fullName.getText().toString().trim();
//        String occ = occupation.getText().toString().trim();
//        String company = companyName.getText().toString().trim();
//        String phone = phoneNumber.getText().toString().trim();
//
//        UserInfo userInfo = new UserInfo(company, name, occ, phone);

        FirebaseUser user = mAuth.getCurrentUser();

        //databaseReference.child(user.getUid()).setValue(userInfo);

        //Toast.makeText(this, "Information Updated",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        try {
//            mCallback = (OnAccountCreatedListener) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString() + " must implement OnAccountCreatedListener");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_createaccount, container, false);

        // Initialize EditTexts and Buttons

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
                if(((EditText) this.getView().findViewById(R.id.fragment_createAccount_editText_password)).getText().toString().equals(
                        ((EditText) this.getView().findViewById(R.id.fragment_createAccount_editText_repeatPassword)).getText().toString())){
                    try{
                        User.getInstance().createAccount(((EditText) this.getView().findViewById(R.id.fragment_createAccount_editText_email)).getText().toString(), ((EditText) this.getView().findViewById(R.id.fragment_createAccount_editText_password)).getText().toString());
                        try{
                            if(((EditText) this.getView().findViewById(R.id.fragment_createAccount_editText_username)).getText().toString()!=null&&
                                    !((EditText) this.getView().findViewById(R.id.fragment_createAccount_editText_username)).getText().toString().isEmpty()) {
                                User.getInstance().changeDisplayName(((EditText) this.getView().findViewById(R.id.fragment_createAccount_editText_username)).getText().toString());
                            }
                        } catch(Exception e){
                            Toast.makeText(this.getContext(), R.string.s_fragment_createAccount_errorMessage_usernameNotSet, Toast.LENGTH_SHORT).show();
                        }
                        //TODO: verify this is correct way to do this
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    } catch(Exception e){
                        Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(v.getContext(), R.string.s_fragment_createAccount_errorMessage_PasswordsNotMatching, Toast.LENGTH_SHORT).show();
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