package com.se319s18a9.util3d.database;

/**
 * Created by Kevan on 3/1/2018.
 */

public class StoreJSON
{
    public String JSONString;

    public StoreJSON(){
        //empty constructor
    }

    public StoreJSON(String json) //can add more info, just add more to parameters
    {
        this.JSONString = json;
    }
}
