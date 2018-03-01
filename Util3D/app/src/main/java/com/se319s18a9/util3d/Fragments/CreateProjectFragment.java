package com.se319s18a9.util3d.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.se319s18a9.util3d.R;

import java.util.ArrayList;

public class CreateProjectFragment extends Fragment implements View.OnClickListener {

    private EditText projectNameEditText;
    private EditText organizationEditText;
    private EditText locationEditText;
    private Button createProjectButton;
    private Button cancelButton;
    private Spinner utilitiesSpinner;

    private ArrayList<String> utilitiesUsed;
    private ArrayList<Utility> utilities;
    private String organization;
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
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_createproject, container, false);

        // Set toolbar title

        getActivity().setTitle(R.string.global_fragmentName_createProject);

        // Initialize local variables

        String[] utilityNames = getResources().getStringArray(R.array.s_fragment_createProject_spinner_utilities);

        // Initialize components and bind listeners

        projectNameEditText = v.findViewById(R.id.fragment_createProject_editText_projectName);
        locationEditText = v.findViewById(R.id.fragment_createProject_editText_location);

        utilitiesSpinner = v.findViewById(R.id.fragment_createProject_spinner_utilites);

        organizationEditText = v.findViewById(R.id.fragment_createProject_editText_organization);

        createProjectButton = v.findViewById(R.id.fragment_createProject_button_create);
        createProjectButton.setOnClickListener(this);

        cancelButton = v.findViewById(R.id.fragment_createProject_button_cancel);
        cancelButton.setOnClickListener(this);

        // Initialize and populate utility spinner with string array from string resource package

        utilities = new ArrayList<>();
        for (String utilityName : utilityNames) {
            utilities.add(new Utility(utilityName, true));
        }

        updateUtilitiesChecked();

        // Create an array from utilities ArrayList to be passed into MultiSpinner adapter

        Utility[] utilArr = new Utility[utilities.size()];
        utilArr = utilities.toArray(utilArr);

        // Create and initialize MultiSpinner adapter and set spinner to this adapter

        MultiSpinnerAdapter adapter = new MultiSpinnerAdapter(this.getContext(), R.layout.fragment_spinneritem, R.id.fragment_spinnerItem_textView, utilArr);
        utilitiesSpinner.setAdapter(adapter);
        utilitiesUsed = new ArrayList<>();

        return v;
    }

    /**
     * Method called whenever the screen is clicked.
     * @param v TBD
     */
    @Override
    public void onClick(View v) {
        // TODO: Add Switch statement for save buttons
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        switch(v.getId()) {
            case R.id.fragment_createProject_button_create:

                //Sets Values for global fragment to the edit text value
                this.organization = getEditTextValue(organizationEditText);
                this.location = getEditTextValue(locationEditText);
                this.projectName = getEditTextValue(projectNameEditText);

                Fragment mapFragment = new MapFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_frameLayout_root, mapFragment);
                fragmentTransaction.addToBackStack(null).commit();


            break;

            // TODO: Make this an actual cancel method that returns to previous screen
            case R.id.fragment_createProject_button_cancel:
                //Used solely for debugging
                //Sets Values for global fragment to the edit text value
                this.organization = getEditTextValue(organizationEditText);
                this.location = getEditTextValue(locationEditText);
                this.projectName = getEditTextValue(projectNameEditText);
                //Used solely for debugging
                StringBuilder proj = new StringBuilder();
                proj.append("Project Name: "+ this.projectName + "\n");
                proj.append("Organization: "+ this.organization + "\n");
                proj.append("Project Location: " + this.location + "\n");
                proj.append("Project Utilities: " + this.utilitiesUsed.toString() + "\n");
                Toast.makeText(this.getContext(),proj.toString(), Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * Creates Adapter For Multi Spinner
     */
    public class MultiSpinnerAdapter extends ArrayAdapter<Utility> {
        private Context context;
        private Utility[] utilities;
        private LayoutInflater flater;

        MultiSpinnerAdapter(@NonNull Context context, int layoutID, int textViewID, Utility[] data ) {
            super(context, layoutID, textViewID, data);
            this.context = context;
            this.utilities = data;
            flater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }

        //Sets Spinner View When the Drop Down is up
        @Override
        public View getView(int pos, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = flater.inflate(R.layout.fragment_spinneritem, parent, false);
            }

            View utilityView = flater.inflate(R.layout.fragment_spinneritem_closed, null, true);
            TextView textView = (TextView) utilityView.findViewById(R.id.fragment_spinnerItem_closed_textView);
            textView.setText("Select Utilities");

            return utilityView;
        }

        // Sets Each view of spinner when drop down is down
        @Override
        public View getDropDownView(int pos, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = flater.inflate(R.layout.fragment_spinneritem, parent, false);
            }

            Utility utility = this.utilities[pos];

            String utilityName = this.utilities[pos].toString();
            TextView title = (TextView) convertView.findViewById(R.id.fragment_spinnerItem_textView);

            CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.fragment_spinnerItem_checkbox);

            checkBox.setChecked(utilities[pos].isChecked);
            checkBox.setOnCheckedChangeListener(new CustomOnCheckedChangeListener(utility, context));

            title.setText(utilityName);
            return convertView;
        }
    }

    /**
     * Class of custom onChecked Listener
     */
    private class CustomOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        Context context;
        Utility utility;

        CustomOnCheckedChangeListener(Utility utility, Context context){
            this.utility = utility;
            this.context = context;
        }

        /**
         * Called whenever a checkbox is clicked on. The new state is passed as boolean b
         * @param compoundButton TBD
         * @param b TBD
         */
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            utility.setIsChecked(b);
            updateUtilitiesChecked();
        }
    }


    /**
     * Class of utility items for MultiSpinner Adapter
     */
    private class Utility{
        boolean isChecked;
        String utilityType;

        Utility(String utilityType, boolean isChecked){
            this.isChecked = isChecked;
            this.utilityType = utilityType;
        }

        void setIsChecked(boolean checked){
            isChecked = checked;
        }

        @Override
        public String toString(){
            return utilityType;
        }
    }

    /**
     * This method update the string list that will allow us to see the utilities picked
     */
    private void updateUtilitiesChecked(){
        utilitiesUsed = new ArrayList<>();
        for(int i=0; i<utilities.size(); i++){
            if(utilities.get(i).isChecked){
                utilitiesUsed.add(utilities.get(i).toString());
            }
        }
    }

    private String getEditTextValue(EditText editText) {
        return editText.getText().toString();
    }
}
