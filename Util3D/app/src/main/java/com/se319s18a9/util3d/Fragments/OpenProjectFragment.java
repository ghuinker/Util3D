package com.se319s18a9.util3d.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.se319s18a9.util3d.R;

import java.util.ArrayList;

/**
 * Created by ghuin on 3/1/2018.
 */

public class OpenProjectFragment  extends Fragment implements View.OnClickListener {

    ListView listView;
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

        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        //ListViewAdapter adapter = new ListViewAdapter(v.getContext(), list);
        //listView.setAdapter(adapter);

        return v;
    }
    @Override
    public void onClick(View view) {

    }

    public class ListViewAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> list;
        public ListViewAdapter(Context context, ArrayList<String> list){
            this.context = context;
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if (view == null) {
                view = LayoutInflater.from(this.context).inflate(R.layout.fragment_openproject, viewGroup, false);
            }

            // get the TextView for item name and item description
            TextView textViewItemName = (TextView) view.findViewById(R.id.fragment_openproject_nameview);

            //sets the text for item name
            textViewItemName.setText(list.get(i));

            // returns the view for the current row
            return view;
        }
    }
}
