package com.se319s18a9.util3d.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.se319s18a9.util3d.R;
import com.se319s18a9.util3d.backend.User;

public class LoadingFragment extends Fragment implements View.OnClickListener {
    boolean operationCompleted;

    public LoadingFragment() {
        operationCompleted = false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loading, container, false);
        ((TextView) v.findViewById(R.id.fragment_loading_textView_loadingMessage)).setText(getArguments().getString("message"));
        return v;
    }

    @Override
    public void onClick(final View v) {

    }

    @Override
    public void onResume(){
        super.onResume();
        if(operationCompleted)
        {
            doneLoading();
        }
    }

    public void doneLoading(){
        operationCompleted = true;
        if(isResumed()) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}