package com.se319s18a9.util3d.Fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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


import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.se319s18a9.util3d.R;
import com.se319s18a9.util3d.backend.User;
import com.se319s18a9.util3d.database.StoreJSON;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class CreateProjectFragment extends Fragment implements View.OnClickListener {

    private Button createProjectButton;
    private Button cancelButton;
    private Spinner utilitiesSpinner;
    private EditText projectNameEditText;
    private EditText orginizationEditText;
    private EditText locationEditText;

    private ArrayList<String> utilitiesUsed;
    private ArrayList<Utility> utilities;
    private String orginization;
    private String projectName;
    private String location;
    private Place googlePlace;

    private String JSONString = "test"; //Need JSON String from Mason

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    public CreateProjectFragment() {
        // Empty constructor
    }

    public void saveJSON() {

        databaseReference = FirebaseDatabase.getInstance().getReference();

        String projectname = projectNameEditText.getText().toString().trim();
        String orginizationname = orginizationEditText.getText().toString().trim();
        String locationname = locationEditText.getText().toString().trim();
        String json = JSONString.trim();

        StoreJSON storeJSON = new StoreJSON(projectname, orginizationname, locationname, json);

        databaseReference.child(User.getInstance().getUserID()).setValue(storeJSON);

        //Toast.makeText(this, "Information Updated",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_createproject, container, false);

        // Initialize Buttons
        createProjectButton = v.findViewById(R.id.fragment_createProject_button_create);
        createProjectButton.setOnClickListener(this);
        cancelButton = v.findViewById(R.id.fragment_createProject_button_cancel);
        cancelButton.setOnClickListener(this);

        //Initalize Spinner
        utilitiesSpinner = v.findViewById(R.id.fragment_createProject_spinner_utilites);
        //Initalize and populate utility spinner with string array from string resource package
        utilities = new ArrayList<>();
        String[] utilityNames = getResources().getStringArray(R.array.s_fragment_createProject_spinner_utilities);
        for (int i=0; i<utilityNames.length; i++){
            utilities.add(new Utility(utilityNames[i], false));
        }

        //Create an array from utilites arraylist to be passed into multispinner adapter
        Utility[] utilArr = new Utility[utilities.size()];
        utilArr = utilities.toArray(utilArr);

        //create and initalize multispinner adapter and set spinner to this adapter
        MultiSpinnerAdapter adapter = new MultiSpinnerAdapter(this.getContext(), R.layout.fragment_spinneritem, R.id.fragment_spinneritem_textview, utilArr);
        utilitiesSpinner.setAdapter(adapter);

        //Initalizes fields to the textview
        projectNameEditText = v.findViewById(R.id.fragment_createProject_editText_projectName);
        orginizationEditText = v.findViewById(R.id.fragment_createProject_editText_organization);
        locationEditText = v.findViewById(R.id.fragment_createProject_editText_location);
        utilitiesUsed = new ArrayList<>();

        //Initalize Google AutoComplete Fragment
        initGoogleLocation();

        return v;
    }

    /**
     * Initialize Google Location
     */
    private void initGoogleLocation(){
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getActivity().getFragmentManager().findFragmentById(R.id.fragment_place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName());
                googlePlace = place;
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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

                //Sets Values for global fragment to the edit text value
                this.orginization = getEditTextValue(orginizationEditText);
                this.location = getEditTextValue(locationEditText);
                this.projectName = getEditTextValue(projectNameEditText);

                Fragment mapFragment = new MapFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_frameLayout_root, mapFragment);
                fragmentTransaction.addToBackStack(null).commit();
                saveJSON();


            break;

            //TODO make this an actual cancel method that returns to previous screen
            case R.id.fragment_createProject_button_cancel:
                //Used solely for debugging
                //Sets Values for global fragment to the edit text value
                this.orginization = getEditTextValue(orginizationEditText);
                this.location = getEditTextValue(locationEditText);
                this.projectName = getEditTextValue(projectNameEditText);
                //Used solely for debugging
                StringBuilder proj = new StringBuilder();
                proj.append("Project Name: "+ this.projectName + "\n");
                proj.append("Orginization: "+ this.orginization + "\n");
                proj.append("Project Location: " + this.location + "\n");
                proj.append("Project Utilites: " + this.utilitiesUsed.toString() + "\n");
                proj.append("Location (Google Location): " + googlePlace.getName());
                Toast.makeText(this.getContext(),proj.toString(), Toast.LENGTH_LONG).show();
                break;


        }
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
        Utility utility;

        public CustomOnCheckedChangeListener(Utility utility, Context context){
            this.utility = utility;
            this.context = context;
        }

        /**
         * Called whenever a checkbox is clicked on. The new state is passed as boolean b
         * @param compoundButton
         * @param b
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
    public class Utility{
        boolean isChecked;
        String utilityType;

        public Utility(String utilityType,boolean isChecked){
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
    public ArrayList<String> getUtility(){
        return utilitiesUsed;
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
