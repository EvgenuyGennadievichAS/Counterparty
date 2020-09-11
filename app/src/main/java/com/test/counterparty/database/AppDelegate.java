package com.test.counterparty.database;

import android.app.Application;

import androidx.room.Room;

import com.test.counterparty.database.PersonDatabase;

public class AppDelegate extends Application {

    private PersonDatabase mPersonDatabase;
    @Override
    public void onCreate() {
        super.onCreate();

        mPersonDatabase = Room.databaseBuilder(this,PersonDatabase.class,"person_database")
                .allowMainThreadQueries()
                .build();
    }

    public PersonDatabase getPersonDatabase() {
        return mPersonDatabase;
    }
}
