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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.se319s18a9.util3d.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ghuin on 3/1/2018.
 */

public class OpenProjectFragment  extends Fragment implements View.OnClickListener {

    ListView listView;
    ArrayList<Project> projects;
    ListViewAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        View v = inflater.inflate(R.layout.fragment_openproject, container, false);

        listView = (ListView) v.findViewById(R.id.fragment_openproject_listview);

        Project[] values = new Project[] {
                new Project("First Name", new Date(2018,3,4), new Date(2018, 3, 6), null),
                new Project("Second Name", new Date(2018, 3, 5), new Date(2018, 3, 6), null)
        };

        final ArrayList<Project> list = new ArrayList<Project>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        adapter = new ListViewAdapter(v.getContext(), list, 0);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
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

                itemName.setText(list.get(i).name);
                itemCreated.setText(list.get(i).created.toString());
                itemModified.setText(list.get(i).modified.toString());

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

    private class Project{
        public String name;
        public Date created;
        public Date modified;
        public ArrayList<String> utilities;


        public Project(String name, Date created, Date modified, ArrayList<String> utilities){
            this.name = name;
            this.created = created;
            this.modified = modified;
            this.utilities = utilities;
        }

        @Override
        public String toString(){
            return name;
        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
