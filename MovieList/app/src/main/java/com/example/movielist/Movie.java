package com.example.movielist;

import java.util.Date;
import java.util.UUID;

public class Movie {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Movie(){
        this(UUID.randomUUID());
    }
    public Movie(UUID id) {
        mId = id;
        mDate = new Date();
        mTitle = "This title should give a default value！";
        mSolved = false;
    }


    public UUID getId(){
        return mId;
    }
    public String getTitle(){
        return mTitle;
    }
    public void setTitle(String title){
        mTitle = title;
    }
    public Date getDate(){
        return mDate;
    }
    public void setDate (Date date){
        mDate = date;
    }
    public boolean isSolved(){
        return mSolved;
    }
    public void setSolved (boolean solved){
        mSolved = solved;
    }
}
