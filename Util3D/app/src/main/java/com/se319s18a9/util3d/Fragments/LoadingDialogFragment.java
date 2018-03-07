package com.se319s18a9.util3d.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.se319s18a9.util3d.R;

/**
 * Created by Mason on 3/5/2018.
 */

public class LoadingDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View dialogView = layoutInflater.inflate(R.layout.dialog_loading, null);
        ((TextView) dialogView.findViewById(R.id.fragment_loading_textView_loadingMessage)).setText(getArguments().getString("message"));
        builder.setView(dialogView);
        builder.setCancelable(false);
        return builder.create();
    }
}
