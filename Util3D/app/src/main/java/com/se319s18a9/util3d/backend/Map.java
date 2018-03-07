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
        ArrayList<Line> tempInitialLines = new ArrayList<>();
        JSONObject reader = new JSONObject(jsonString);
        for(int lineIndex = 0; true; lineIndex++){
            JSONObject tempLineJSON;
            try {
                tempLineJSON = reader.getJSONObject("line"+lineIndex);
            } catch(Exception e){
                break;
            }
            Line tempLine = new Line(null);
            //This line will throw an exception if the json is formatted incorrectly
            tempLine.readFromJSON(tempLineJSON);
            tempInitialLines.add(tempLine);
        }
        //I don't want to actually add any lines to this map until I am sure the entire json is formatted correctly
        for (Line l:tempInitialLines) {
            initialLines.add(l);
        }
    }
}
