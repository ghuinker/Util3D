package com.se319s18a9.util3d.backend;

import org.json.JSONException;
import org.json.JSONObject;

public class Line{
    private String savedType;
    private ConnectedPoint nullHeadPoint;

    public Line(String type){
        savedType = type;
        nullHeadPoint = new ConnectedPoint();
    }

    public String getType(){
        return savedType;
    }

    public ConnectedPoint getNullHeadPoint(){
        return nullHeadPoint;
    }

    public JSONObject writeToJSON() throws JSONException {
        JSONObject output = new JSONObject();
        output.put("type",savedType);
        output.put("initialPoint",nullHeadPoint.writeToJSON());
        return output;
    }

    public void readFromJSON(JSONObject reader) throws JSONException
    {
        savedType = reader.getString("type");
        JSONObject initialPointJSON = reader.getJSONObject("initialPoint");
        nullHeadPoint.readFromJSON(initialPointJSON, null);
    }
}