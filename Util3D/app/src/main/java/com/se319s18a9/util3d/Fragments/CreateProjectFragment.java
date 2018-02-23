package com.se319s18a9.util3d.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



import com.se319s18a9.util3d.R;



public class CreateProjectFragment extends Fragment implements View.OnClickListener {

    private Button createProjectButton;
    private Spinner utilitiesSpinner;
    private EditText projectNameEditText;
    private EditText orginizationEditText;
    private EditText locationEditText;

    private String utility;

    private String orginization;
    private String projectName;
    private String location;

    public CreateProjectFragment() {
        // Empty constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_createproject, container, false);

        createProjectButton = v.findViewById(R.id.fragment_createProject_button_create);
        createProjectButton.setOnClickListener(this);


        utilitiesSpinner = v.findViewById(R.id.fragment_createProject_spinner_utilites);
        utilitiesSpinner.setOnItemSelectedListener(new CustomSpinnerListener());

        projectNameEditText = v.findViewById(R.id.fragment_createProject_editText_projectName);
        orginizationEditText = v.findViewById(R.id.fragment_createProject_editText_organization);
        locationEditText = v.findViewById(R.id.fragment_createProject_editText_location);
        utility = utilitiesSpinner.getItemAtPosition(0).toString();


        return v;
    }


    /**
     * Method called whenever the screen is clicked.
     * @param v
     */
    @Override
    public void onClick(View v) {
        // TODO: Add Switch statement for save buttons
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        switch(v.getId()) {
            case R.id.fragment_createProject_button_create:

                this.orginization = getEditTextValue(orginizationEditText);
                this.location = getEditTextValue(locationEditText);
                this.projectName = getEditTextValue(projectNameEditText);

                Fragment mapFragment = new MapFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_frameLayout_root, mapFragment);
                fragmentTransaction.addToBackStack(null).commit();
            break;

            case R.id.fragment_createProject_spinner_utilites:



        }
    }

    /**
     * This sets the utility string from the given string.
     * @param util
     */
    //TODO check if it is a correct utility
    public void setUtility(String util){
        this.utility = util;
    }


    /**
     * Public class used for the spinner
     * TODO Update this to multi selector
     */
    public class CustomSpinnerListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            setUtility(parent.getItemAtPosition(pos).toString());
        }
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }

    }

    /**
     * This method gets the string value from the edittext given
     *
     * @param editText
     * @return String from editext
     */
    private String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }

    /**
     * @return utility String
     */
    public String getUtility(){
        return utility;
    }

    /**
     * @return orginization String
     */
    public String getOrginization(){
        return orginization;
    }

    /**
     * @return projectname String
     */
    public String getProjectName(){
        return projectName;
    }

    /**
     * @return Orginization
     */
    public String getLocation(){
        return orginization;
    }


}
