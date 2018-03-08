package com.se319s18a9.util3d.database;

/**
 * Created by Kevan on 3/1/2018.
 */

public class StoreProject
{
    public String projectName;
    public String organizationName;
    public String locationName;

    public StoreProject(){
        //empty constructor
    }

    public StoreProject(String projectName, String organizationName, String locationName) //can add more info, just add more to parameters
    {
        this.projectName = projectName;
        this.organizationName = organizationName;
        this.locationName = locationName;
    }
}
