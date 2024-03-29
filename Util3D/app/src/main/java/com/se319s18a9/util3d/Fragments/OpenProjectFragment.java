package com.se319s18a9.util3d.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.se319s18a9.util3d.R;
import com.se319s18a9.util3d.backend.Project;
import com.se319s18a9.util3d.backend.User;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ghuin on 3/1/2018.
 */

public class OpenProjectFragment  extends Fragment implements View.OnClickListener {

    ListView listView;
    ArrayList<Project> projects;
    ListViewAdapter adapter;
    LoadingDialogFragment loadingDialogFragment;
    Exception[] loadProjectListException;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        projects = new ArrayList<>();

        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setCancelable(false);
        Bundle messageArgument = new Bundle();
        messageArgument.putString("message", "Loading Projects");
        loadingDialogFragment.setArguments(messageArgument);
        loadProjectListException = User.getInstance().getMyPersonalProjects(projects, this::updateFileList);
        loadingDialogFragment.show(getActivity().getFragmentManager(), null);
    }

    public void updateFileList(){
        loadingDialogFragment.dismiss();
        adapter.notifyDataSetChanged();
        if(loadProjectListException[0]!=null) {
            Toast.makeText(getContext(), loadProjectListException[0].getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_openproject, container, false);

        getActivity().setTitle(R.string.global_fragmentName_openProject);

        listView = (ListView) v.findViewById(R.id.fragment_openproject_listview);

        adapter = new ListViewAdapter(v.getContext(), projects, 0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment mapFragment = new MapFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("LoadMap", true);
                bundle.putString("ProjectName", adapterView.getItemAtPosition(i).toString());
                mapFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frameLayout_root, mapFragment).addToBackStack(null).commit();
            }
        });

        return v;
    }
    @Override
    public void onClick(View view) {

    }

    public class ListViewAdapter extends BaseAdapter {

        Context context;
        ArrayList<Project> list;
        int simpleViewID= R.layout.fragment_openproject_nameview;
        int detailViewID = R.layout.fragment_openproject_detailview;
        int currentView;


        public ListViewAdapter(Context context, ArrayList<Project> list, int view){
            this.context = context;
            this.list = list;
            setView(view);
        }
        public void toggleDetail(){
            if(currentView == detailViewID){
                currentView = simpleViewID;
            } else {
                currentView = detailViewID;
            }
        }
        public void setView(int view){
            switch(view){
                case 0: currentView = simpleViewID; break;
                case 1: currentView = detailViewID; break;
                default: currentView = simpleViewID; break;
            }
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Project getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

                view = LayoutInflater.from(this.context).inflate(currentView, viewGroup, false);

            if(currentView == detailViewID){
                TextView itemName = (TextView) view.findViewById(R.id.fragment_openproject_detailname);
                TextView itemUtilities = (TextView) view.findViewById(R.id.fragment_openproject_detailutilities);
                TextView itemCreated = (TextView) view.findViewById(R.id.fragment_openproject_detailcreated);
                TextView itemModified = (TextView) view.findViewById(R.id.fragment_openproject_detailmodified);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");



                itemName.setText(list.get(i).name);
                itemCreated.setText(dateFormat.format(list.get(i).created));
                itemModified.setText(dateFormat.format(list.get(i).modified));

            }

            else if(currentView == simpleViewID) {
                // get the TextView for item name and item description
                TextView textViewItemName = (TextView) view.findViewById(R.id.fragment_openproject_nameview);
                textViewItemName.setText(list.get(i).name);
            }
            //TODO throw error if these cases aren't selected

            // returns the view for the current row
            return view;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_openproject_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.fragment_openproject_changeview:
                adapter.toggleDetail();
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Change View", Toast.LENGTH_SHORT).show();
                return true;
            default:
                Toast.makeText(getActivity(), "Something happened", Toast.LENGTH_SHORT).show();
                return false;

        }
    }
}
