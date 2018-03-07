package com.se319s18a9.util3d.backend;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ConnectedPoint {
    private double savedLatitude;
    private double savedLongitude;
    private ConnectedPoint parentPoint;
    private ArrayList<ConnectedPoint> children;
    private Line parentLine;

    public ConnectedPoint(ConnectedPoint parent){
        parentPoint = parent;
        if(parent!=null) {
            parentLine = parent.parentLine;
        }
        children = null;
    }

    public ConnectedPoint(ConnectedPoint parent, double latitude, double longitude){
        this(parent);
        savedLatitude = latitude;
        savedLongitude = longitude;
    }

    public LatLng getLatLng()
    {
        return new LatLng(savedLatitude,savedLongitude);
    }

    public double getLatitude()
    {
        return savedLatitude;
    }

    public double getLongitude()
    {
        return savedLongitude;
    }

    public boolean isRoot()
    {
        return parentPoint == null;
    }

    public boolean isBranchPoint()
    {
        return children.size()>1;
    }

    public ConnectedPoint insertPointBetweenThisPointAndParent(double latitude, double longitude)
    {
        ConnectedPoint newPoint = new ConnectedPoint(this.parentPoint, latitude, longitude);
        parentPoint.children.add(newPoint);
        parentPoint.children.remove(this);
        newPoint.children.add(this);
        parentPoint=newPoint;
        return newPoint;
    }

    public ConnectedPoint addAChild(double latitude, double longitude)
    {
        if(children ==null) {
            children = new ArrayList<ConnectedPoint>();
        }
        ConnectedPoint newPoint = new ConnectedPoint(this, latitude, longitude);
        children.add(newPoint);
        return newPoint;
    }

    public void remove(){
        for (ConnectedPoint tempPoint: children){
            parentPoint.children.add(tempPoint);
            tempPoint.parentPoint = parentPoint;
        }
        parentPoint.children.remove(this);
    }

    public void move(double latitude, double longitude)
    {
        savedLatitude =latitude;
        savedLongitude =longitude;
    }

    public ArrayList<ConnectedPoint> getChildren()
    {
        return children;
    }

    public ConnectedPoint getParentPoint()
    {
        return parentPoint;
    }

    public Line getParentLine()
    {
        return parentLine;
    }

    public JSONObject writeToJSON() throws JSONException {
        JSONObject output = new JSONObject();

        output.put("latitude",savedLatitude);
        output.put("longitude",savedLongitude);
        if(children!=null) {
            for (int c = 0; c < children.size(); c++) {

                output.put("child" + c, children.get(c).writeToJSON());
            }
        }

        return output;
    }

    public void readFromJSON(JSONObject reader) throws JSONException
    {
        boolean failed = false;
        int pointIndex = 0;
        savedLatitude = reader.getDouble("latitude");
        savedLongitude = reader.getDouble("longitude");
        while(!failed){
            try {
                ConnectedPoint tempPoint = new ConnectedPoint(this);
                JSONObject tempChildJSON = reader.getJSONObject("child"+pointIndex);
                tempPoint.readFromJSON(tempChildJSON);
                if(children ==null) {
                    children = new ArrayList<ConnectedPoint>();
                }
                children.add(tempPoint);
                pointIndex++;
            } catch(Exception e){
                failed = true;
            }
        }
    }
}