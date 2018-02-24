package com.se319s18a9.util3d.Fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Property;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import com.se319s18a9.util3d.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


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

        Utility[] utilities = new Utility[4];
        String[] utilityNames = getResources().getStringArray(R.array.s_fragment_createProject_spinner_utilities);
        for (int i=0; i<utilities.length; i++){
            utilities[i] = new Utility(utilityNames[i], false);
        }

        MultiSpinnerAdapter adapter = new MultiSpinnerAdapter(this.getContext(), R.layout.fragment_spinneritem, R.id.fragment_spinneritem_textview, utilities);
        utilitiesSpinner.setAdapter(adapter);


        projectNameEditText = v.findViewById(R.id.fragment_createProject_editText_projectName);
        orginizationEditText = v.findViewById(R.id.fragment_createProject_editText_organization);
        locationEditText = v.findViewById(R.id.fragment_createProject_editText_location);
        //utility = utilitiesSpinner.getItemAtPosition(0).toString();



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

            case R.id.fragment_createProject_button_cancel:

                Toast.makeText(this.getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                break;


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
     * Creates Adapter For Multi Spinner
     */
    public class MultiSpinnerAdapter extends ArrayAdapter<Utility>{

        private Context context;
        private Utility[] utilities;
        private LayoutInflater flater;

        public MultiSpinnerAdapter(@NonNull Context context, int layoutID, int textViewID, Utility[] data ) {
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
            TextView textView = (TextView) utilityView.findViewById(R.id.fragment_spinneritem_closed_textview);
            textView.setText("Select Utilites");

            return utilityView;
        }

        //Sets Each view of spinner when drop down is down
        @Override
        public View getDropDownView(int pos, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = flater.inflate(R.layout.fragment_spinneritem, parent, false);
            }
            Utility utility = this.utilities[pos];

            String utiliityName = this.utilities[pos].toString();
            TextView title = (TextView) convertView.findViewById(R.id.fragment_spinneritem_textview);

            CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.fragment_spinneritem_checkbox);

            checkBox.setOnCheckedChangeListener(new CustomOnCheckedChangeListener(utility, context));

            title.setText(utiliityName);
            return convertView;
        }

    }

    /**
     * Class of custom onChecked Listener
     */
    public class CustomOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        Context context;
        int arrPos;

        public CustomOnCheckedChangeListener(Utility utility, Context context){
            this.arrPos = arrPos;
            this.context = context;
        }
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Toast.makeText(context, arrPos + "", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Class of utility items for MultiSpinner Adapter
     */
    public class Utility{
        boolean isChecked;
        String utilityType;

        public Utility( String utilityType, Boolean isChecked){
            this.isChecked = isChecked;
            this.utilityType = utilityType;
        }
        public void setIsChecked(boolean checked){
            isChecked = checked;
        }
        public void changeChecked(){
            isChecked = !isChecked;
        }

        public boolean isChecked(){
            return isChecked;
        }
        public String getUtilityType(){
            return utilityType;
        }
        @Override
        public String toString(){
            return utilityType;
        }
    }



    /**
     * Public class used for the spinner USED FOR OG CODE
     * TODO Update this to multi selector
     */
    public class CustomSpinnerListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            setUtility(parent.getItemAtPosition(pos).toString());
            Toast.makeText(getContext(), "Parent + View =" + view + parent, Toast.LENGTH_SHORT);
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
