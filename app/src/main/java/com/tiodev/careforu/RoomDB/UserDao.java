package com.tiodev.careforu.RoomDB;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM recipe")
    List<User> getAll();

}

