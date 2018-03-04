package com.se319s18a9.util3d.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.Projection;
import com.se319s18a9.util3d.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ghuin on 3/1/2018.
 */

public class OpenProjectFragment  extends Fragment implements View.OnClickListener {

    ListView listView;
    ArrayList<Project> projects;

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

        Project[] values = new Project[] {new Project(new Date(2018,3,4), "Name"), new Project(new Date(2018, 3, 5), "Second Name")};

        final ArrayList<Project> list = new ArrayList<Project>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        ListViewAdapter adapter = new ListViewAdapter(v.getContext(), list);
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

        public ListViewAdapter(Context context, ArrayList<Project> list){
            this.context = context;
            this.list = list;
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

            if (view == null) {
                view = LayoutInflater.from(this.context).inflate(R.layout.fragment_openproject_nameview, viewGroup, false);
            }

            // get the TextView for item name and item description
            TextView textViewItemName = (TextView) view.findViewById(R.id.fragment_openproject_nameview);


            //sets the text for item name
            textViewItemName.setText(list.get(i).name);

            // returns the view for the current row
            return view;
        }
    }

    private class Project{
        public Date date;
        public String name;

        public Project(Date date, String name){
            this.name = name;
            this.date = date;
        }

        @Override
        public String toString(){
            return name;
        }

    }
}
