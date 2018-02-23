package com.se319s18a9.util3d.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.se319s18a9.util3d.LoginActivity;
import com.se319s18a9.util3d.R;
import com.se319s18a9.util3d.backend.User;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    Button createProjectButton;
    Button settingsButton;
    Button logoutButton;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        createProjectButton = v.findViewById(R.id.fragment_dashboard_button_createProject);
        createProjectButton.setOnClickListener(this);

        settingsButton = v.findViewById(R.id.fragment_dashboard_button_settings);
        settingsButton.setOnClickListener(this);

        logoutButton = v.findViewById(R.id.fragment_dashboard_button_logout);
        logoutButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager;
        FragmentTransaction fragmentTransaction;

        switch(v.getId()) {
            case R.id.fragment_dashboard_button_createProject:
                Fragment createProjectFragment = new CreateProjectFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_frameLayout_root, createProjectFragment);
                fragmentTransaction.addToBackStack(null).commit();
                break;
            case R.id.fragment_dashboard_button_openProject:
                // TODO
                break;
            case R.id.fragment_dashboard_button_settings:
                Fragment settingsFragment = new SettingsFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_main_frameLayout_root, settingsFragment);
                fragmentTransaction.addToBackStack(null).commit();
                break;
            case R.id.fragment_dashboard_button_logout:
                User.getInstance().signOut();
                Toast.makeText(getContext(), R.string.s_fragment_dashboard_alertMessage_loggedOut, Toast.LENGTH_SHORT).show();
                //Todo fix to use fragment stack instead of ending activity
                getActivity().finish();
                break;
        }
    }
}
