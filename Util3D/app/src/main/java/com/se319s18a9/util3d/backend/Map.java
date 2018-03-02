package com.se319s18a9.util3d.backend;

import org.json.JSONException;
import org.json.JSONObject;

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
}
