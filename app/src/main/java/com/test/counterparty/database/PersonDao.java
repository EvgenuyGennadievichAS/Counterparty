package com.test.counterparty.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertPersons(Person persons);

    @Query("select * from person")
    List<Person> getPersons();

    @Delete
    void deletePerson(Person person);
}
