package com.se319s18a9.util3d.database;

/**
 * Created by Kevan on 3/1/2018.
 */

public class StoreJSON
{
    public String projectName;
    public String organizationName;
    public String locationName;
    public String JSONString;

    public StoreJSON(){
        //empty constructor
    }

    public StoreJSON(String projectName, String organizationName, String locationName, String JSONString) //can add more info, just add more to parameters
    {
        this.projectName = projectName;
        this.organizationName = organizationName;
        this.locationName = locationName;
        this.JSONString = JSONString;
    }
}
