package com.se319s18a9.util3d.backend;

public abstract class CustomAsyncTask extends Thread {
    public abstract boolean isComplete();
    public abstract boolean isSuccessful();
    public abstract Exception getException();
}
