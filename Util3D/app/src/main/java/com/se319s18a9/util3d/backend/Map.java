package com.se319s18a9.util3d.backend;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.se319s18a9.util3d.Fragments.LoadingFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Mason on 2/28/2018.
 */

public class Map {
    //Utility type
    //polylines
    //placepoint
    //setmaptype
    //deleteline
    //plotpointswhiletracking

    //file
    //name
    //owner
    //datecreated
    //dateupdated
    //linecount
    //pointcount
    //line type enum


    //line
    //name/id
    //util type
    //point count

    private ArrayList<Line> initialLines;
    private ConnectedPoint current;
    JSONObject stringFromFirebase[] = {null};

    public Map(){
        initialLines = new ArrayList<Line>();
        return;
    }

    public Line addNewLine(String type){
        Line newLine = new Line(type);
        initialLines.add(newLine);
        return newLine;
    }

    public ArrayList<Line> getLines(){
        return initialLines;
    }

    public void setSavedPoint(ConnectedPoint point){
        current = point;
    }

    public ConnectedPoint getSavedPoint(){
        return current;
    }

    public String writeToJSON() throws JSONException{
        JSONObject output = new JSONObject();

        if(initialLines!=null) {
            for (int c = 0; c < initialLines.size(); c++) {
                output.put("line" + c, initialLines.get(c).writeToJSON());
            }
        }

        return output.toString();
    }

    public void readFromJSON(String jsonString) throws JSONException
    {
        JSONObject reader = new JSONObject(jsonString);
        boolean failed = false;
        int lineIndex = 0;
        while(!failed){
            try {
                Line tempLine = new Line(null);
                JSONObject tempLineJSON = reader.getJSONObject("line"+lineIndex);
                tempLine.readFromJSON(tempLineJSON);
                initialLines.add(tempLine);
                lineIndex++;
            } catch(Exception e){
                failed = true;
            }
        }
    }

    public void getStringFromFirebase(String path, final LoadingFragment loadingFragment) {
        final DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference(path);
        tempRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stringFromFirebase[0] = dataSnapshot.getValue(JSONObject.class);
                loadingFragment.doneLoading();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return;
    }

    public void putStringToFirebase(String path, String json){
        DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference(path);
        tempRef.setValue(json);
    }
}
