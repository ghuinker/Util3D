package com.se319s18a9.util3d.database;

/**
 * Created by Matt Smith on 2/28/2018.
 */

public class UserInfo {
    public String companyName;
    public String fullName;
    public String occupation;
    public String phoneNumber;


    public UserInfo(){
        //empty constructor
    }

    public UserInfo(String companyName, String fullName, String occupation, String phoneNumber) //can add more info, just add more to parameters
    {
        this.companyName = companyName;
        this.fullName = fullName;
        this.occupation = occupation;
        this.phoneNumber = phoneNumber;
    }
}
